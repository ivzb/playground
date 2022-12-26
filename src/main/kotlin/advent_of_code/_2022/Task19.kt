package advent_of_code._2022

import Debug
import Task
import readInput
import utils.Parse.ints

object Task19 : Task {

    private const val MINUTES_PART_A = 24
    private const val MINUTES_PART_B = 32

    private val INITIAL_RESOURCES = ResourceAmounts(
        mutableMapOf(
            Resource.Ore to 0,
            Resource.Clay to 0,
            Resource.Obsidian to 0,
            Resource.Geode to 0,
        )
    )
    private val INITIAL_ROBOTS = ResourceAmounts(
        mutableMapOf(
            Resource.Ore to 1,
            Resource.Clay to 0,
            Resource.Obsidian to 0,
            Resource.Geode to 0,
        )
    )

    private val ROBOTS_TO_BUILD = listOf(
        Resource.Geode,
        Resource.Obsidian,
        Resource.Clay,
        Resource.Ore,
        null,
    )

    private val dependencies = mapOf(
        Resource.Geode to Resource.Obsidian,
        Resource.Obsidian to Resource.Clay,
        Resource.Clay to Resource.Ore,
        Resource.Ore to Resource.Ore,
    )

    enum class Resource {
        Ore,
        Clay,
        Obsidian,
        Geode,
    }

    data class Cost(val resource: Resource, val amount: Int)

    data class Blueprint(val robotCosts: Map<Resource, List<Cost>>) {

        val maxNeeded by lazy {
            robotCosts
                .map { (_, costs) -> costs }
                .flatten()
                .groupBy { it.resource }
                .mapValues { it.value.maxOf { it.amount } }

        }
    }

    data class State(
        val resources: ResourceAmounts,
        val robots: ResourceAmounts
    ) {

        fun copy(): State {
            return State(
                resources.copy(),
                robots.copy(),
            )
        }

        override fun toString(): String {
            val rbs = robots.toString()
            val res = resources.toString()
            return "robots=($rbs), resources=($res)"
        }
    }

    object Factory {

        fun canBuild(robotResource: Resource, state: State, blueprint: Blueprint): Boolean {
            val costs = blueprint.robotCosts[robotResource] ?: error("robot cost not found")
            val spend = ArrayList<String>()
            return costs.all { cost ->
                val available = state.resources.resources[cost.resource] ?: 0
                val needed = cost.amount
                spend.add("$needed ${cost.resource}")
                available >= needed
            }
        }

        fun startBuildingRobot(robotResource: Resource, state: State, blueprint: Blueprint) {
            val costs = blueprint.robotCosts[robotResource] ?: error("robot cost not found")
            costs.forEach { cost ->
                val needed = cost.amount
                state.resources.subtract(cost.resource, needed)
            }
        }

        fun completeRobotBuild(robotResource: Resource, state: State) {
            state.robots.add(robotResource, 1)
        }

    }

    data class ResourceAmounts(
        val resources: MutableMap<Resource, Int>,
        // todo: remove?
        var total: Int = 0,
        var geode: Int = 0,
    ) {

        fun add(resource: Resource, amount: Int) {
            resources[resource] = resources[resource]!! + amount

            when (resource) {
                Resource.Geode -> geode += amount
                else -> null
            }

            total += amount
        }

        fun subtract(resource: Resource, amount: Int) {
            resources[resource] = resources[resource]!! - amount

            when (resource) {
                Resource.Geode -> geode -= amount
                else -> null
            }

            total -= amount
        }

        fun copy(): ResourceAmounts {
            return ResourceAmounts(
                resources.toMutableMap(),
                geode = geode,
            )
        }

        override fun toString(): String {
            return resources.map { (resource, amount) -> "($resource: $amount)" }.joinToString(", ")
        }
    }

    override fun partA() = parseInput()
        .let { blueprints ->
            blueprints.mapIndexed { index, blueprint ->
                val initialState = State(INITIAL_RESOURCES, INITIAL_ROBOTS)

                val allPossibleStates = ArrayDeque<State>()
                allPossibleStates.add(initialState)

                var minute = 1

                while (minute <= MINUTES_PART_A) {
//                    Debug.println("== Minute $minute == blueprint id #${index}/${blueprints.size}")

                    val newPossibleStates = HashSet<State>()
//
                    while (allPossibleStates.isNotEmpty()) {
                        val state = allPossibleStates.removeFirst()
                        val newStates = ROBOTS_TO_BUILD.mapNotNull { robotToBuild ->
                            attempt(state, blueprint, robotToBuild)
                        }
                        newPossibleStates.addAll(newStates)
                    }

                    val localMaxResGeode = newPossibleStates.maxOf { it.resources.geode }
                    val localMaxRobGeode = newPossibleStates.maxOf { it.robots.geode }

                    val sorted = newPossibleStates.filter {
                            it.robots.geode == localMaxRobGeode || it.resources.geode == localMaxResGeode
                    }

//                    println("size=${sorted.size}")

                    allPossibleStates.addAll(sorted)

                    minute++
                }

                val id = (index + 1)
                val maxGeode = allPossibleStates.maxOf { it.resources.resources[Resource.Geode]!! }

//                println("id #$id max= $maxGeode")

                id * maxGeode
            }.sum()
        }

    override fun partB() = parseInput()
        .take(3)
        .let { blueprints ->
            blueprints.mapIndexed { index, blueprint ->
                val initialState = State(INITIAL_RESOURCES, INITIAL_ROBOTS)

                val allPossibleStates = ArrayDeque<State>()
                allPossibleStates.add(initialState)

                var minute = 1

                while (minute <= MINUTES_PART_B) {
//                    Debug.println("== Minute $minute == blueprint id #${index}/${blueprints.size}")

                    val newPossibleStates = HashSet<State>()

                    while (allPossibleStates.isNotEmpty()) {
                        val state = allPossibleStates.removeFirst()
                        val newStates = ROBOTS_TO_BUILD.mapNotNull { robotToBuild ->
                            attempt(state, blueprint, robotToBuild)
                        }
                        newPossibleStates.addAll(newStates)
                    }

                    val localMaxResGeode = newPossibleStates.maxOf { it.resources.geode }
                    val localMaxRobGeode = newPossibleStates.maxOf { it.robots.geode }

                    val sorted = newPossibleStates.filter {
                        it.robots.geode == localMaxRobGeode || it.resources.geode == localMaxResGeode
                    }

//                    println("size=${sorted.size}")//, max1=$localMaxResGeode, max2=$localMaxRobGeode")

                    allPossibleStates.addAll(sorted)

                    minute++
                }

                val maxGeode = allPossibleStates.maxOf { it.resources.resources[Resource.Geode]!! }

//                println("id #$id max= $maxGeode")

                maxGeode
            }.reduce(Int::times)
        }

    private fun attempt(base: State, blueprint: Blueprint, robotResource: Resource?): State? {
        if (robotResource != null) {
            // don't attempt if there are no robots capable of creating desired robot
            val dependency = dependencies[robotResource] ?: error("dependency $robotResource not found")

            if (!base.robots.resources.containsKey(dependency)) {
                return null
            }

            // no need to produce more resources than needed
            val maxNeeded = blueprint.maxNeeded

            if (maxNeeded.containsKey(robotResource) && base.robots.resources[robotResource]!! == maxNeeded[robotResource]!!) {
                return null
            }
        }

        val state = base.copy()

        val robotToBuild: Resource? = if (robotResource != null && Factory.canBuild(robotResource, state, blueprint)) {
            Factory.startBuildingRobot(robotResource, state, blueprint)
            robotResource
        } else null

        for ((resource, robotsCount) in state.robots.resources) {
            state.resources.add(resource, robotsCount)
        }

        robotToBuild?.let {
            Factory.completeRobotBuild(it, state)
        }

        return state
    }

    private fun parseInput() = readInput("_2022/19")
        .split("\n")
        .map {
            it.split(" ").ints().let { costs ->
                val robotCosts = mapOf(
                    Resource.Ore to listOf(Cost(Resource.Ore, costs[0])),
                    Resource.Clay to listOf(Cost(Resource.Ore, costs[1])),
                    Resource.Obsidian to listOf(Cost(Resource.Ore, costs[2]), Cost(Resource.Clay, costs[3])),
                    Resource.Geode to listOf(Cost(Resource.Ore, costs[4]), Cost(Resource.Obsidian, costs[5])),
                )

                Blueprint(robotCosts)
            }
        }

}