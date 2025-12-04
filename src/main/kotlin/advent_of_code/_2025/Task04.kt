package advent_of_code._2025

import Task
import readInput
import utils.Bounds
import utils.Bounds.Companion.bounds
import utils.Point

object Task04 : Task {

    private const val ROLL_OF_PAPER = "@"
    private const val REMOVED = "x"

    override fun partA(): Int = solve { map, bounds ->
        move(map, bounds)
    }

    override fun partB(): Int = solve { map, bounds ->
        evolve(map) { move(it, bounds) }
    }

    private fun solve(move: (Map<Point, String>, Bounds) -> Map<Point, String>): Int = parseInput()
        .let { map ->
            move(map, map.keys.bounds())
                .count { (_, item) -> item == REMOVED }
        }

    private fun move(map: Map<Point, String>, bounds: Bounds): Map<Point, String> =
        map.mapValues { (position, item) ->
            if (item != ROLL_OF_PAPER) {
                return@mapValues item
            }

            val adjacent = position
                .adjacent()
                .filter { point -> point.isWithin(bounds) && map[point] == ROLL_OF_PAPER }

            if (adjacent.size >= 4) {
                return@mapValues item
            }

            REMOVED
        }

    private fun <T> evolve(value: T, move: (T) -> T): T {
        val next = move(value)
        return if (next == value) value else evolve(next, move)
    }

    private fun parseInput() = readInput("_2025/04")
        .lines()
        .mapIndexed { row, it ->
            it.split("").mapIndexed { col, it -> Point(row, col) to it }
        }
        .flatten()
        .toMap()

}
