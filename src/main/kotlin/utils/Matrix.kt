package utils

import utils.Point.Companion.toPoint
import java.lang.Math.abs
import java.lang.Math.max

object Matrix {

    fun areAdjacent(
        p1: Point,
        p2: Point,
        checkDiagonal: Boolean = true,
    ): Boolean = when {
        checkDiagonal && chebyshevDistance(p1, p2) == 1 -> true
        !checkDiagonal && manhattanDistance(p1, p2) == 1 -> true
        else -> false
    }

    // todo: allow taking n params (only near 1 neighbours)
    fun adjacentRowsAndCols(matrix: List<List<Int>>, i: Int, j: Int) = listOf(
        matrix.slice(0 until i).map { it[j] }.reversed(), // top
        matrix[i].slice(j + 1 until matrix[j].size), // right
        matrix.slice(i + 1 until matrix.size).map { it[j] }, // bottom
        matrix[i].slice(0 until j).reversed(), // left
    )

    fun <T> adjacent(matrix: List<List<T>>, i: Int, j: Int) = listOf(
        matrix.slice(0 until i).map { it[j] }.reversed().firstOrNull(), // top
        matrix[i].slice(j + 1 until matrix[j].size).firstOrNull(), // right
        matrix.slice(i + 1 until matrix.size).map { it[j] }.firstOrNull(), // bottom
        matrix[i].slice(0 until j).reversed().firstOrNull(), // left
    )

    // todo: take N elements
    fun <T> adjacentIndexed(matrix: List<List<T>>, row: Int, col: Int): List<Pair<Pair<Int, Int>, T>> =
        adjacentIndexes( row, col)
            .filter { (i, j) -> (i in matrix.indices) && (j in matrix[i].indices) }
            .map { (i, j) -> (i to j) to matrix[i][j] }

    fun <T> adjacentIndexed(matrix: List<List<T>>, position: Point): List<Pair<Pair<Int, Int>, T>> =
        adjacentIndexed(matrix, position.x, position.y)

    fun adjacentIndexes(row: Int, col: Int): List<Pair<Int, Int>> =
        listOfNotNull(
            row - 1 to col, // top
            row to col + 1, // right
            row + 1 to col, // bottom
            row to col - 1, // left
        )

    // todo: create data class to hold row and col?
    fun adjacentIndexes(position: Point): List<Point> =
        adjacentIndexes(position.x, position.y).map { it.toPoint() }

    /**
     * Distance measured only horizontally and vertically, but not diagonally
     * Example:
     * 2 1 2
     * 1 . 1
     * 2 1 2
     */
    fun manhattanDistance(p1: Point, p2: Point): Int {
        val deltaX = abs(p1.x - p2.x)
        val deltaY = abs(p1.y - p2.y)
        return deltaX + deltaY
    }

    /**
     * Distance measured horizontally, vertically and diagonally
     * Example:
     * 1 1 1
     * 1 . 1
     * 1 1 1
     */
    fun chebyshevDistance(p1: Point, p2: Point): Int {
        val deltaX = abs(p1.x - p2.x)
        val deltaY = abs(p1.y - p2.y)
        return max(deltaX, deltaY)
    }

    private val horizontalAdjacentDeltas = listOf(
        -1 to 0, // Left
        1 to 0,  // Right
    )

    private val verticalAdjacentDeltas = listOf(
        0 to -1, // Up
        0 to 1,  // Down
    )

    private val diagonalAdjacentDeltas = listOf(
        -1 to -1, // Up-Left
        -1 to 1,  // Up-Right
        1 to -1,  // Down-Left
        1 to 1,   // Down-Right
    )

    fun isWithinManhattanDistance(p1: Point, p2: Point, p3: Point): Boolean {
        return abs(p1.x - p3.x) + abs(p1.y - p3.y) <= abs(p1.x - p2.x) + abs(p1.y - p2.y)
    }
}