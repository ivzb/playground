package advent_of_code._2024

import Task
import readInput
import utils.Point
import java.lang.Exception
import java.util.*

object Task10 : Task {

    override fun partA() = parseInput()
        .let { map ->
            solve(map, withDistinctTrails = false)
        }

    override fun partB() = parseInput()
        .let { map ->
            solve(map, withDistinctTrails = true)
        }

    private fun solve(map: Map<Point, Int>, withDistinctTrails: Boolean): Int {
        val tails = map.filter { (_, number) -> number == 9 }.map { (point) -> point }
        var solutions = 0

        for (tail in tails) {
            val q = LinkedList<Point>()
            q.add(tail)

            val visited = mutableSetOf<Point>()

            while (q.isNotEmpty()) {
                val current = q.removeLast()
                visited.add(current)

                val currentValue = map[current] ?: throw Exception("item not found")
                val solutionFound = currentValue == 0

                if (solutionFound) {
                    solutions++
                    continue
                }

                listOf(
                    Point(0, -1), // up
                    Point(1, 0), // right
                    Point(0, 1), // down
                    Point(-1, 0), // left
                )
                .map { diff ->
                    current + diff
                }
                .filter {
                    map[it] == currentValue - 1 && (withDistinctTrails || !visited.contains(it))
                }
                .forEach(q::add)
            }
        }

        return solutions
    }

    private fun parseInput() = readInput("_2024/10")
        .lines()
        .mapIndexed { row, line -> line.mapIndexed { col, symbol -> Point(col, row) to symbol.digitToInt() } }
        .flatten()
        .toMap()

}