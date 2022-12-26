package advent_of_code._2022

import Task
import readInput
import utils.Bounds
import utils.Bounds.Companion.bounds
import utils.Direction.*
import utils.Point
import java.lang.StringBuilder

object Task24 : Task {

    private const val WALL = '#'
    private const val EMPTY = '.'
    private const val BLIZZARD_UP = '^'
    private const val BLIZZARD_RIGHT = '>'
    private const val BLIZZARD_DOWN = 'v'
    private const val BLIZZARD_LEFT = '<'

    private val SAME_DIRECTION = Point(0, 0)
    private val DIRECTION_DELTAS = (listOf(NORTH, SOUTH, WEST, EAST).map { it.delta } + SAME_DIRECTION)

    private data class Move(val position: Point, val step: Int)

    override fun partA() = parseInput()
        .let { input ->
            // build the map
            val map = buildMap(input)
            val walls = walls(input)
            val bounds = (map.keys + walls).bounds()

            // find the cycle with set of states
            val pattern = findPattern(map, bounds)

            // find the shortest path to the end
            val start = Point(1, 0)
            val end = Point(bounds.max.x - 1, bounds.max.y)

            val solution = search(start, end, 0, pattern, walls, bounds)

            solution
        }

    override fun partB() = parseInput()
        .let { input ->
            // build the map
            val map = buildMap(input)
            val walls = walls(input)
            val bounds = (map.keys + walls).bounds()

            // find the cycle with set of states
            val pattern = findPattern(map, bounds)

            // find the shortest path to the end
            val start = Point(1, 0)
            val end = Point(bounds.max.x - 1, bounds.max.y)

            val solution1 = search(start, end, 0, pattern, walls, bounds)
            val solution2 = search(end, start, solution1, pattern, walls, bounds)
            val solution3 = search(start, end, solution2, pattern, walls, bounds)

            solution3
        }

    private fun search(
        start: Point,
        end: Point,
        initialStep: Int,
        pattern: List<Set<Point>>,
        walls: Set<Point>,
        bounds: Bounds
    ): Int {
        val pendingMoves = ArrayDeque<Move>()
        val visitedMoves = HashSet<Move>()
        val possibleMoves = HashSet<Move>()
        val impossibleMoves = HashSet<Move>()

        val initialMove = Move(start, initialStep + 1)
        pendingMoves.add(initialMove)

        while (pendingMoves.isNotEmpty()) {
            val move = pendingMoves.removeFirst()
            val step = move.step + 1

            if (visitedMoves.contains(move)) {
                continue
            }

            if (move.position == end) {
                return step - 1
            }

            visitedMoves.add(move)

            val nextPositions = DIRECTION_DELTAS.map { delta -> move.position + delta }
            val blizzards = pattern[step % pattern.size]
            val nextPossibleMoves: Collection<Move> = nextPositions
                .map { nextPosition ->
                    val mode = Move(position = nextPosition, step = step)
                    cache(move, nextPosition, possibleMoves, impossibleMoves, bounds, walls, blizzards)
                    move
                }
                .subtract(impossibleMoves)

            pendingMoves.addAll(nextPossibleMoves)
        }

        error("no solution found")
    }

    private fun cache(
        move: Move,
        position: Point,
        possibleMoves: HashSet<Move>,
        impossibleMoves: HashSet<Move>,
        bounds: Bounds,
        walls: Set<Point>,
        blizzards: Set<Point>
    ) {
        if (!possibleMoves.contains(move) && !impossibleMoves.contains(move)) {
            val isPossibleMove = position.isWithin(bounds) && !walls.contains(position) && !blizzards.contains(position)

            if (isPossibleMove) {
                possibleMoves.add(move)
            } else {
                impossibleMoves.add(move)
            }
        }
    }

    private fun findPattern(
        map: Map<Point, List<Char>>,
        bounds: Bounds
    ): List<Set<Point>> {
        val pattern = HashSet<Map<Point, List<Char>>>()
        val states = ArrayList<Set<Point>>()

        var blizzards = map
        var position = 0

        while (true) {
            pattern.add(blizzards)
            states.add(HashSet(blizzards.keys))

            val nextState = nextState(blizzards, bounds)
            blizzards = nextState
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
        moves: Collection<Move>? = null
    ): String {
        val sb = StringBuilder()

        val positionCounts = HashMap<Point, Int>()

        if (moves != null) {
            (bounds.min.y..bounds.max.y).forEach { y ->
                (bounds.min.x..bounds.max.x).forEach { x ->
                    val position = Point(x, y)
                    positionCounts[position] = 0
                }
            }

            for (move in moves) {
                val position = move.position
                positionCounts[position] = positionCounts[position]!! + 1
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