package advent_of_code._2024

import Task
import readInput

object Task06 : Task {

    override fun partA() = parseInput()
        .let {
            val (visited, _) = solve(it, null)
            visited.size
        }

    override fun partB() = parseInput()
        .let { map ->
            val (visitedPositions) = solve(map, null)

            visitedPositions.count { obstructionPosition ->
                val (_, isLoop) = solve(map, obstructionPosition)
                isLoop
            }
        }

    private fun solve(map: Map<Pair<Int, Int>, Char>, obstructionPosition: Pair<Int, Int>?): Pair<Set<Pair<Int, Int>>, Boolean> {
        var (guardPosition, guardDirection) = map.entries.first { it.value == '^' }
        var hasLoop = false
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Char>>()

        while (true) {
            if (visited.contains(guardPosition to guardDirection)) {
                hasLoop = true
                break
            }

            visited.add(guardPosition to guardDirection)
            val targetPosition = nextPosition(guardDirection, guardPosition)

            when {
                map[targetPosition] == '#' || targetPosition == obstructionPosition -> {
                    // rotate direction 90 degrees
                    guardDirection = CHANGE_DIRECTION_MAP[guardDirection] ?: throw Exception("unknown guard direction $guardDirection")
                }

                map[targetPosition] == null -> {
                    // went out of map
                    break
                }

                else -> {
                    // free to go there
                    guardPosition = targetPosition
                }
            }
        }

        val visitedPositions = visited.map { (positions) -> positions }.toSet()
        return visitedPositions to hasLoop
    }

    private val CHANGE_DIRECTION_MAP = mapOf(
        '^' to '>',
        '>' to 'v',
        'v' to '<',
        '<' to '^',
    )

    private fun nextPosition(direction: Char, position: Pair<Int, Int>): Pair<Int, Int> {
        val (row, col) = position

        return when(direction) {
            '^' -> (row - 1) to col
            '>' -> row to col + 1
            'v' -> (row + 1) to col
            '<' -> row to col - 1
            else -> throw Exception("unknown guard direction $direction")
        }
    }

    private fun parseInput() = readInput("_2024/06")
        .lines()
        .mapIndexed { row, line -> line.mapIndexed { col, symbol -> (row to col) to symbol } }
        .flatten()
        .toMap()

}