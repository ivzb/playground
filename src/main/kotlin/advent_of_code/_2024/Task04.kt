package advent_of_code._2024

import Task
import advent_of_code._2024.Task04.access
import readInput
import kotlin.Exception

object Task04 : Task {

    override fun partA() = solve('X', xmasCoords, "XMAS")

    override fun partB() = solve('A', masmasCoords, "MASMAS")

    private fun solve(charOfInterest: Char, coords: List<List<Pair<Int, Int>>>, solution: String): Int {
        val matrix = parseInput()
        var counter = 0

        matrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, col ->
                if (col == charOfInterest) {
                    coords.forEach { coord ->
                        val point = rowIndex to colIndex
                        val result = coord.map { offset -> matrix.access(point, offset) }.joinToString("")

                        if (result == solution) {
                            counter++
                        }
                    }
                }
            }
        }

        return counter
    }

    private fun parseInput() = readInput("_2024/04")
        .lines()

    private fun List<String>.access(point: Pair<Int, Int>, offset: Pair<Int, Int>): Char = try {
        this[point.first + offset.first][point.second + offset.second]
    } catch (e: Exception) {
        ' '
    }

    private val xmasCoords = listOf(
        // horizontal to the right
        listOf(
            0 to 0,
            0 to 1,
            0 to 2,
            0 to 3,
        ),

        // horizontal to the left
        listOf(
            0 to 0,
            0 to -1,
            0 to -2,
            0 to -3,
        ),

        // vertical to the bottom
        listOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 0,
        ),

        // vertical to the top
        listOf(
            0 to 0,
            -1 to 0,
            -2 to 0,
            -3 to 0,
        ),

        // diagonal to the bottom right corner
        listOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
        ),

        // diagonal to the top right corner
        listOf(
            0 to 0,
            -1 to 1,
            -2 to 2,
            -3 to 3,
        ),

        // diagonal to the bottom left corner
        listOf(
            0 to 0,
            1 to -1,
            2 to -2,
            3 to -3,
        ),

        // diagonal to the top left corner
        listOf(
            0 to 0,
            -1 to -1,
            -2 to -2,
            -3 to -3,
        )
    )

    private val masmasCoords = listOf(
        // left side
        listOf(
            -1 to -1,
            0 to 0,
            1 to 1,
            1 to -1,
            0 to 0,
            -1 to 1,
        ),

        // top side
        listOf(
            -1 to -1,
            0 to 0,
            1 to 1,
            -1 to 1,
            0 to 0,
            1 to -1,
        ),

        // right side
        listOf(
            -1 to 1,
            0 to 0,
            1 to -1,
            1 to 1,
            0 to 0,
            -1 to -1,
        ),

        // bottom side
        listOf(
            1 to 1,
            0 to 0,
            -1 to -1,
            1 to -1,
            0 to 0,
            -1 to 1,
        ),
    )

}