package _2022

import Task
import readInput
import utils.Bounds
import utils.Bounds.Companion.bounds
import utils.Direction.*
import utils.Matrix
import utils.Point
import java.lang.StringBuilder

object Task24 : Task {

    private const val WALL = '#'
    private const val EMPTY = '.'
    private const val BLIZZARD_UP = '^'
    private const val BLIZZARD_RIGHT = '>'
    private const val BLIZZARD_DOWN = 'v'
    private const val BLIZZARD_LEFT = '<'

    private const val NO_SOLUTION = -1

    private val SAME_DIRECTION = Point(0, 0)
    private val DIRECTION_DELTAS = (listOf(NORTH, SOUTH, WEST, EAST).map { it.delta } + SAME_DIRECTION)

    override fun partA() = parseInput()
        .let { input ->
            // build the map
            val initialState = buildMap(input)
            val walls = walls(input)
            val bounds = (initialState.keys + walls).bounds()

            // find the cycle with set of states
            val states = findCycleStates(initialState, bounds)

            // find the shortest path to the end
            val start = Point(1, 0)
            val end = Point(bounds.max.x - 1, bounds.max.y)

            val solution = lookup(start, end, 0, states, walls, bounds)

            solution
        }

    override fun partB() = parseInput()
        .let { input ->
            // build the map
            val initialState = buildMap(input)
            val walls = walls(input)
            val bounds = (initialState.keys + walls).bounds()

            // find the cycle with set of states
            val states = findCycleStates(initialState, bounds)

            // find the shortest path to the end
            val start = Point(1, 0)
            val end = Point(bounds.max.x - 1, bounds.max.y)

            val solution1 = lookup(start, end, 0, states, walls, bounds)
            val solution2 = lookup(end, start, solution1, states, walls, bounds)
            val solution3 = lookup(start, end, solution2, states, walls, bounds)

            println("solution1 = $solution1, steps = $solution1")
            println("solution2 = $solution2 - steps = ${solution2 - solution1}")
            println("solution3 = $solution3 - steps = ${solution3 - solution2}")

            solution3
        }

    data class Move(val from: Point, val to: Point, val step: Int)

    private fun lookup(
        start: Point,
        end: Point,
        initialStep: Int,
        states: List<Set<Point>>,
        walls: Set<Point>,
        bounds: Bounds
    ): Int {

        val pathsQ = ArrayDeque<List<Point>>()
        pathsQ.add(listOf(start))

        val solutions = HashSet<List<Point>>()
        var step = initialStep

        val visited = HashSet<Move>()

        val possibleMoves = HashSet<Move>()
        val impossibleMoves = HashSet<Move>()

        game@while (true) {
            val nextPossiblePaths = HashSet<List<Point>>()
            val stateIndex = (step + 1) % states.size
            val blizzards = states[stateIndex]

//            Debug.println(printMap(blizzards, walls, bounds, pathsQ))

            while (pathsQ.isNotEmpty()) {
                val path = pathsQ.removeFirst()
                val lastPosition = path.last()

                val nextMove = Move(lastPosition, end, step)

                if (visited.contains(nextMove)) {
                    continue
                }

                val result = search(lastPosition, end, step + 1, states, walls, bounds)

                if (result != NO_SOLUTION) {
                    // solution found
                    return result
                }

                visited.add(nextMove)

                if (lastPosition == end) {
                    solutions.add(path)
                    return result
                }

                val nextPossiblePossitions = DIRECTION_DELTAS.map { delta -> lastPosition + delta }

                fun Point.isPossibleMove(): Boolean {
                    return isWithin(bounds) && !walls.contains(this) && !blizzards.contains(this)
                }

                val nextPaths: List<List<Point>> = nextPossiblePossitions
                    .map { nextPosition ->
                        val move = Move(from = lastPosition, to = nextPosition, step = step)

                        if (!possibleMoves.contains(move) && !impossibleMoves.contains(move)) {
                            if (nextPosition.isPossibleMove()) {
                                possibleMoves.add(move)
                            } else {
                                impossibleMoves.add(move)
                            }
                        }

                        move
                    }
                    .subtract(impossibleMoves)
                    .map { move -> path + move.to }

                nextPossiblePaths.addAll(nextPaths)
            }

            if (nextPossiblePaths.isEmpty()) {
                error("no solution from (${start.x}, ${start.y}) after $step steps")
            }

            pathsQ.addAll(nextPossiblePaths)
            step++
        }
    }

    private fun search(
        start: Point,
        end: Point,
        initialStep: Int,
        states: List<Set<Point>>,
        walls: Set<Point>,
        bounds: Bounds
    ): Int {
        val pathsQ = ArrayDeque<List<Point>>()
        pathsQ.add(listOf(start))

        var step = initialStep

        game@while (true) {
            if (pathsQ.isEmpty()) {
                return NO_SOLUTION
            }

            val nextPossiblePaths = HashSet<Pair<List<Point>, Int>>()
            val stateIndex = (step + 1) % states.size
            val blizzards = states[stateIndex]

            while (pathsQ.isNotEmpty()) {
                val path = pathsQ.removeFirst()
                val position = path.last()

                if (position == end) {
                    // solution found
                    return step
                }

                val nextPossiblePositions = listOf(NORTH, SOUTH, WEST, EAST)
                    .map { position + it.delta }
                    .filter {
                        it.isWithin(bounds) && !walls.contains(it) && !blizzards.contains(it)
                    }

                val currentPaths = nextPossiblePositions.map {
                    val distance = Matrix.chebyshevDistance(end, it)
                    path + it to distance
                }

                nextPossiblePaths.addAll(currentPaths)
            }

            if (nextPossiblePaths.isEmpty()) {
                return NO_SOLUTION
            }

            val bestDistance = nextPossiblePaths.minOf { (_, distance) -> distance }

            val bestPositions = nextPossiblePaths
                .filter { (_, distance) -> distance == bestDistance }
                .map { (path) -> path }

            pathsQ.addAll(bestPositions)
            step++
        }
    }

    private fun findCycleStates(
        initialState: Map<Point, List<Char>>,
        bounds: Bounds
    ): List<Set<Point>> {
        val pattern = HashSet<Map<Point, List<Char>>>()
        val states = ArrayList<Set<Point>>()

        var lastState = initialState
        var position = 0

        while (true) {
            pattern.add(lastState)
            states.add(HashSet(lastState.keys))

            val nextState = nextState(lastState, bounds)
            lastState = nextState
            position++

            if (pattern.contains(nextState)) {
                break
            }
        }

        return states
    }

    private fun nextState(current: Map<Point, List<Char>>, bounds: Bounds): Map<Point, List<Char>> {
        val next = HashMap<Point, ArrayList<Char>>()

        current.forEach { (point, items) ->
            items.forEach { item ->
                val nextPoint = when (item) {
                    BLIZZARD_UP -> {
                        val prevY = point.y - 1
                        val y = if (prevY > bounds.min.y) prevY else bounds.max.y - 1
                        Point(point.x, y)
                    }

                    BLIZZARD_RIGHT -> {
                        val nextX = point.x + 1
                        val x = if (nextX < bounds.max.x) nextX else bounds.min.x + 1
                        Point(x, point.y)
                    }

                    BLIZZARD_DOWN -> {
                        val nextY = point.y + 1
                        val y = if (nextY < bounds.max.y) nextY else bounds.min.y + 1
                        Point(point.x, y)
                    }

                    BLIZZARD_LEFT -> {
                        val prevX = point.x - 1
                        val x = if (prevX > bounds.min.x) prevX else bounds.max.x - 1
                        Point(x, point.y)
                    }

                    else -> error("undefined item $item")
                }

                next.getOrPut(nextPoint) { ArrayList() }.add(item)
            }
        }

        return next
    }

    private fun printMap(
        blizzards: Set<Point>,
        walls: Set<Point>,
        bounds: Bounds,
        paths: Collection<List<Point>>? = null
    ): String {
        val sb = StringBuilder()

        val positionCounts = HashMap<Point, Int>()

        if (paths != null) {
            (bounds.min.y..bounds.max.y).forEach { y ->
                (bounds.min.x..bounds.max.x).forEach { x ->
                    val position = Point(x, y)
                    positionCounts[position] = 0
                }
            }

            for (path in paths) {
                for (position in path) {
                    positionCounts[position] = positionCounts[position]!! + 1
                }
            }
        }

        (bounds.min.y..bounds.max.y).forEach { y ->
            (bounds.min.x..bounds.max.x).forEach { x ->
                val position = Point(x, y)
                val positionCount = positionCounts[position] ?: 0
                val hasWall = walls.contains(position)
                val hasBlizzard = blizzards.contains(position)

                val item = when {
                    positionCount != 0 -> (positionCount % 9).digitToChar()
                    hasWall -> WALL
                    hasBlizzard -> WALL
                    else -> EMPTY
                }

                sb.append(item)
            }

            sb.appendLine()
        }

        return sb.toString()
    }

    private fun walls(input: List<CharArray>): Set<Point> {
        val walls = HashSet<Point>()

        input.forEachIndexed { y, row ->
            row.forEachIndexed row@{ x, item ->
                if (item != WALL) return@row
                val position = Point(x, y)
                walls.add(position)
            }
        }

        return walls
    }

    private fun buildMap(input: List<CharArray>): Map<Point, List<Char>> {
        val map = HashMap<Point, List<Char>>()

        input.forEachIndexed { y, row ->
            row.forEachIndexed row@{ x, item ->
                if (x == 0 || x == row.size - 1 || y == 0 || y == input.size - 1) return@row
                if (item == EMPTY) return@row
                val position = Point(x, y)
                map[position] = listOf(item)
            }
        }

        return map
    }

    private fun parseInput() = readInput("_2022/24")
        .split("\n")
        .map { it.toCharArray() }

}