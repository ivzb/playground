package advent_of_code._2024

import Task
import readInput
import utils.Combinatorics.toPairs
import utils.Point

object Task08 : Task {

    override fun partA() = parseInput()
        .let { map ->
            solve(map) { a, b ->
                sequence {
                    val diff = Point(a.x - b.x, a.y - b.y)
                    yield(a + diff)
                    yield(b - diff)
                }
            }
        }

    override fun partB() = parseInput()
        .let { map ->
            solve(map) { a, b ->
                sequence {
                    val diff = Point(a.x - b.x, a.y - b.y)
                    var nextA = a
                    var nextB = b

                    while (map.containsKey(nextA) || map.containsKey(nextB)) {
                        yield(nextA)
                        yield(nextB)

                        nextA += diff
                        nextB -= diff
                    }
                }
            }
        }

    private fun solve(map: Map<Point, Char>, calculation: (Point, Point) -> Sequence<Point>): Int {
        return map
            .filter { (_, symbol) -> symbol.isLetterOrDigit() }
            .toList()
            .groupBy { (_, symbol) -> symbol }
            .map { (symbol, antenas) ->
                antenas
                    .map { (position) -> position }
                    .toPairs()
                    .map { (a, b) -> calculation(a, b) }
                    .flatten()
                    .map { node -> node to symbol }
                    .toList()
            }
            .flatten()
            .filter { (node) -> map.containsKey(node) }
            .distinctBy { (node) -> node }
            .size
    }

    private fun parseInput() = readInput("_2024/08")
        .lines()
        .mapIndexed { row, line -> line.mapIndexed { col, symbol -> Point(col, row) to symbol } }
        .flatten()
        .toMap()

}