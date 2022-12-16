package _2022

import Task
import readInput

object Task16 : Task {

    data class Valve(
        val name: String,
        val flowRate: Int,
        val leadsTo: List<String>
    ) {

        fun neighbors(valves: Map<String, Valve>): List<Valve> {
            return leadsTo.map { valveName ->
                valves[valveName]!!
            }
        }

        override fun toString(): String {
            return "$name(rate=$flowRate)"
        }
    }


    data class PathB(
        val valvesMe: List<Valve>,
        val valvesElephant: List<Valve>,
        val opened: Map<Valve, Int>,
        val time: Int,
    ) {

        fun lastMe(): Valve = valvesMe.last()
        fun lastElephant(): Valve = valvesElephant.last()

        fun hasOpened(valve: Valve): Boolean = opened.containsKey(valve)

        fun total(): Int {
            // todo: check if calculation is correct
            // todo: partA is 30
            return opened.map { (valve, time) -> (26 - time) * valve.flowRate }.sum()
//            return opened.map { (_, total) -> total }.sum()
        }

        override fun toString(): String {
            val namesMe = valvesMe/*.drop(1)*/.mapIndexed { index, valve ->
                var result = valve.name
                if (index > 0 && valvesMe[index - 1].name == valvesMe[index].name) {
                    result = "($result)"
                }
                result
            }
            val namesElephant = valvesElephant/*.drop(1)*/.mapIndexed { index, valve ->
                var result = valve.name
                if (index > 0 && valvesElephant[index - 1].name == valvesElephant[index].name) {
                    result = "($result)"
                }
                result
            }
            val opened = opened.size
            return namesMe.joinToString(" -> ") + "\n " +
                    namesElephant.joinToString(" -> ") + "\n " +
                    " (opened=$opened, time = $time, total ${total()})"
        }
    }

    override fun partA() = parseInput().let {
        val allValves = it.associate {
            val name = it[0]
            val flowRate = it[1].toInt()
            val leadsTo = it.subList(2, it.size)
            name to Valve(name, flowRate, leadsTo)
        }

        val MAX_OPENABLE_VALVES = allValves.values.count { it.flowRate > 0 }

        var start = allValves["AA"]!!

        val startPath = PathA(
            valves = arrayListOf(start),
            opened = HashMap(),
            time = 0,
        )

        var allPaths = listOf(startPath)
        var bestPath = startPath

        var remainingTime = 30
        var time = 0

        while (time < 30) {
//            println("time = $time")
//            println(bestPath)

            val newPaths = arrayListOf<PathA>()
            for (currentPath in allPaths) {
                if (currentPath.opened.size == MAX_OPENABLE_VALVES) {
                    continue
                }

                val currentLast = currentPath.last()
                val currentValves = currentPath.valves
                val currentTime = currentPath.time + 1

                // open
                if (currentLast.flowRate > 0 && !currentPath.opened.containsKey(currentLast)) {
                    val opened = currentPath.opened.toMutableMap()
                    opened[currentLast] = currentTime
                    val possibleValves = currentValves + currentLast
                    val possibleOpenedPath = PathA(possibleValves, opened, currentTime)
                    newPaths.add(possibleOpenedPath)
                }

                // move
                val possiblePaths: List<PathA> = currentLast.leadsTo.map { lead ->
                    // add possible path and move on
                    val possibleValve = allValves[lead]!!
                    val possibleValves = currentValves + possibleValve
                    val possiblePath = PathA(possibleValves, currentPath.opened, currentTime)
                    possiblePath
                }

                newPaths.addAll(possiblePaths)
            }

            // todo: stop looking for paths when all possible valves have been opened
            allPaths = newPaths.sortedByDescending { it.total() }.take(10000)

            if (allPaths.first().total() > bestPath.total()) {
                bestPath = allPaths.first()
            }

            time++
            remainingTime--
        }

//        (1..30).forEach { minute ->
//            bestPath.apply {
//                println("== Minute ${minute}")
//                val openedValves = opened.filter { (_, time) -> time < minute }
//                if (openedValves.size == 0) {
//                    println("No valves are open.")
//                } else {
//                    val pressure = openedValves.map { (valve) -> valve.flowRate }.sum()
//                    val names = openedValves.map { (valve) -> valve.name }.joinToString(", ")
//                    println("Valves $names are open, releasing $pressure pressure.")
//                }
//
//                val valves = valves.drop(1)
//                if (minute < valves.size) {
//                    val valve = valves[minute]
//
//                    if (opened.containsKey(valve) && opened[valve]!! == minute) {
//                        println("You open valve ${valve.name}.")
//                    } else {
//                        println("You move to valve ${valve.name}.")
//                    }
//
//                    println()
//                }
//            }
//
//        }

        bestPath.total()
    }

    override fun partB() = parseInput().let {
        val allValves = it.associate {
            val name = it[0]
            val flowRate = it[1].toInt()
            val leadsTo = it.subList(2, it.size)
            name to Valve(name, flowRate, leadsTo)
        }

        val MAX_OPENABLE_VALVES = allValves.values.count { it.flowRate > 0 }

        var start = allValves["AA"]!!

        val startPath = PathB(
            valvesMe = arrayListOf(start),
            valvesElephant = arrayListOf(start),
            opened = HashMap(),
            time = 0,
        )

        var allPaths = listOf(startPath)
        var bestPath = startPath

        var remainingTime = 26
        var time = 0

        while (time < 26) {
//            println("time = $time")
//            println(bestPath)

            val newPaths = arrayListOf<PathB>()

            for (currentPath in allPaths) {
                if (currentPath.opened.size == MAX_OPENABLE_VALVES) {
                    continue
                }

                val currentLastMe = currentPath.lastMe()
                val currentLastElephant = currentPath.lastElephant()
                val currentValvesMe = currentPath.valvesMe
                val currentValvesElephant = currentPath.valvesElephant
                val currentTime = currentPath.time + 1

                val openMe = currentLastMe.flowRate > 0 && !currentPath.opened.containsKey(currentLastMe)
                val openElephant = currentLastElephant.flowRate > 0 && !currentPath.opened.containsKey(currentLastElephant)

                if (openMe && openElephant) {
                    val opened = currentPath.opened.toMutableMap()
                    opened[currentLastMe] = currentTime
                    val possibleValvesMe = currentValvesMe + currentLastMe

                    opened[currentLastElephant] = currentTime
                    val possibleValvesElephant = currentValvesElephant + currentLastElephant

                    val possibleOpenedPath = PathB(possibleValvesMe, possibleValvesElephant, opened, currentTime)
                    newPaths.add(possibleOpenedPath)
                } else if (openMe) {
                    val opened = currentPath.opened.toMutableMap()
                    opened[currentLastMe] = currentTime
                    val possibleValvesMe = currentValvesMe + currentLastMe

                    val possibleValvesElephants: List<List<Valve>> = currentLastElephant.leadsTo.map { lead ->
                        // add possible path and move on
                        val possibleValve = allValves[lead]!!
                        val possibleValves = currentValvesElephant + possibleValve
                        possibleValves
                    }

                    for (possibleValvesElephant in possibleValvesElephants) {
                        val possibleOpenedPath = PathB(possibleValvesMe, possibleValvesElephant, opened, currentTime)
                        newPaths.add(possibleOpenedPath)
                    }

                } else if (openElephant) {
                    val opened = currentPath.opened.toMutableMap()
                    opened[currentLastElephant] = currentTime
                    val possibleValvesElephant = currentValvesElephant + currentLastElephant

                    val possibleValvesMes: List<List<Valve>> = currentLastMe.leadsTo.map { lead ->
                        // add possible path and move on
                        val possibleValve = allValves[lead]!!
                        val possibleValves = currentValvesMe + possibleValve
                        possibleValves
                    }

                    for (possibleValvesMe in possibleValvesMes) {
                        val possibleOpenedPath = PathB(possibleValvesMe, possibleValvesElephant, opened, currentTime)
                        newPaths.add(possibleOpenedPath)
                    }
                }

                val combinedLeads = currentLastMe.leadsTo.map { leadMe ->
                    currentLastElephant.leadsTo.map { leadElephant ->
                        leadMe to leadElephant
                    }
                }
                    .flatten()
                    .filter { (a, b) -> a != b }

                // move combined
                val possiblePaths: List<PathB> = combinedLeads.map { (leadMe, leadElephant) ->
                    // add possible path and move on
                    val possibleValveMe = allValves[leadMe]!!
                    val possibleValvesMe = currentValvesMe + possibleValveMe
                    val possibleValveElephant = allValves[leadElephant]!!
                    val possibleValvesElephant = currentValvesElephant + possibleValveElephant
                    val possiblePath = PathB(possibleValvesMe, possibleValvesElephant, currentPath.opened, currentTime)
                    possiblePath
                }

                newPaths.addAll(possiblePaths)
            }

            // todo: stop looking for paths when all possible valves have been opened
            allPaths = newPaths.sortedByDescending { it.total() }.take(100000)

            if (allPaths.first().total() > bestPath.total()) {
                bestPath = allPaths.first()
            }

            time++
            remainingTime--
        }

//        (1 .. 26).forEach { minute ->
//            bestPath.apply {
//                println("== Minute ${minute}")
//                val openedValves = opened.filter { (_, time) -> time < minute }
//                if (openedValves.size == 0) {
//                    println("No valves are open.")
//                } else {
//                    val pressure = openedValves.map { (valve) -> valve.flowRate }.sum()
//                    val names = openedValves.map { (valve) -> valve.name }.joinToString(", ")
//                    println("Valves $names are open, releasing $pressure pressure.")
//                }
//
//                val valves = valves.drop(1)
//                if (minute < valves.size) {
//                    val valve = valves[minute]
//
//                    if (opened.containsKey(valve) && opened[valve]!! == minute) {
//                        println("You open valve ${valve.name}.")
//                    } else {
//                        println("You move to valve ${valve.name}.")
//                    }
//
//                    println()
//                }
//            }

//        }

        bestPath.total()
    }

    private fun parseInput() = readInput("_2022/16")
        .split("\n")
        .asSequence()
        .map { it.replace("Valve ", "") }
        .map { it.replace("has flow rate=", "") }
        .map { it.replace("; tunnels lead to valves", "") }
        .map { it.replace("; tunnel leads to valve", "") }
        .map { it.split(' ', ',') }
        .map { it.filter { it.isNotEmpty() } }
        .toList()

    data class PathA(
        val valves: List<Valve>,
        val opened: Map<Valve, Int>,
        val time: Int,
    ) {

        fun last(): Valve = valves.last()

        fun total(): Int {
            return opened.map { (valve, time) -> (30 - time) * valve.flowRate }.sum()
        }

        override fun toString(): String {
            val names = valves/*.drop(1)*/.mapIndexed { index, valve ->
                var result = valve.name
                if (index > 0 && valves[index - 1].name == valves[index].name) {
                    result = "($result)"
                }
                result
            }
            val opened = opened.size
            return names.joinToString(" -> ") + " (opened=$opened, time = $time, total ${total()})"
        }
    }
}
