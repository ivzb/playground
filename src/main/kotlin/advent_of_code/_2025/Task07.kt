package advent_of_code._2025

import Task
import readInput
import utils.Point
import java.util.*

object Task07 : Task {

    private val cache = HashMap<Point, Long>()

    override fun partA() = parseInput()
        .let { (map, start) ->
            solve(map, start, isQuantum = false) - 1
        }

    override fun partB() = parseInput()
        .let { (map, start) ->
            solve(map, start, isQuantum = true)
        }


    private fun solve(map: MutableMap<Point, Char>, path: Point, isQuantum: Boolean): Long {
        val next = path + Point(1, 0)

        cache[next]?.let {
            return it
        }

        return when (map[next]) {
            '.' -> {
                val timelines = solve(map, next, isQuantum)

                if (isQuantum) {
                    cache[next] = timelines
                } else {
                    map[next] = '|'
                }

                timelines
            }

            '^' -> {
                val left = solve(map, next + Point(0, -1), isQuantum)
                val right = solve(map, next + Point(0, 1), isQuantum)
                left + right
            }

            else -> 1
        }
    }

    private fun parseInput() = readInput("_2025/07")
        .lines()
        .let {
            val map = it
                .mapIndexed { rowIndex, row ->
                    row.mapIndexed { colIndex, char ->
                        val point = Point(rowIndex, colIndex)
                        point to char
                    }
                }
                .flatten()
                .associate { (p, char) -> p to char }
                .toMutableMap()

            val start = map.entries.first { (_, char) -> char == 'S' }.key

            map to start
        }

}