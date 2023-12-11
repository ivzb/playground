package advent_of_code._2023

import Task
import readInput
import utils.Combinatorics.permutationPairs
import utils.Matrix.manhattanDistanceLong
import utils.Point
import utils.Point.Companion.toPoint

object Task11 : Task {

    private const val EMPTY_SPACE = '.'
    private const val GALAXY = '#'

    override fun partA(): Long = cosmicExpansion(2)

    override fun partB(): Long = cosmicExpansion(1_000_000)

    private fun cosmicExpansion(times: Int): Long {
        val input = parseInput()

        // generate map for easier data access in format of (x, y) = char
        val sky =
            List(input.size) { row ->
                List(input[row].size) { col ->
                    (row to col).toPoint() to input[row][col]
                }
            }
            .flatten()
            .associate { (row, char) -> row to (row to char) }
            .toMutableMap()

        // expand space in X direction
        (0 until input.size)
            .map { rowIndex ->
                val row = input[rowIndex]
                rowIndex to row.all { it == EMPTY_SPACE }
            }
            .filter { (_, isEmpty) -> isEmpty }
            .map { (index) -> index }
            .toSet()
            .forEach { row ->
                sky.filter { (point) -> point.x > row }.forEach { (key, r) ->
                    val (point, char) = r
                    sky[key] = (point + Point(times - 1, 0) to char)
                }
            }

        // expand space in Y direction
        (0 until input[0].size)
            .map { colIndex ->
                val col = (0 until input.size).map { rowIndex -> input[rowIndex][colIndex] }
                colIndex to col.all { it == '.' }
            }
            .filter { (_, isEmpty) -> isEmpty }
            .map { (index) -> index }
            .toSet()
            .forEach { col ->
                sky.filter { (point) -> point.y > col }.forEach { (key, r) ->
                    val (point, char) = r
                    sky[key] = (point + Point(0, times - 1) to char)
                }
            }

        return sky.values
            .filter { (_, char) -> char == GALAXY }
            .map { (point) -> point }
            .permutePairs()
            .sumOf { (from, to) ->
                manhattanDistanceLong(from, to)
            }
    }

    private fun parseInput() = readInput("_2023/11")
        .split("\n")
        .map { it.toCharArray().toMutableList() }
        .toMutableList()

    private fun List<Point>.permutePairs() = permutationPairs(this)

}