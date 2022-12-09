package utils

import utils.Point.Companion.toPoint
import java.lang.Math.abs

object Matrix {

    enum class Direction(val delta: Point) {
        UP(Point(0, 1)),
        RIGHT(Point(1, 0)),
        DOWN(Point(0, -1)),
        LEFT(Point(-1, 0));
    }

    fun areAdjacent(
        p1: Point,
        p2: Point,
        withHorizontal: Boolean = true,
        withVertical: Boolean = true,
        withDiagonal: Boolean = true,
    ): Boolean =
        listOfNotNull(
            horizontalAdjacentDeltas.takeIf { withHorizontal },
            verticalAdjacentDeltas.takeIf { withVertical },
            diagonalAdjacentDeltas.takeIf { withDiagonal },
        )
        .flatten()
        .map { it.toPoint() }
        .any { delta ->
            abs(p1.x - p2.x) == delta.x && abs(p1.y - p2.y) == delta.y
        }

    // todo: allow taking n params (only near 1 neighbours)
    fun adjacentRowsAndCols(matrix: List<List<Int>>, i: Int, j: Int) = listOf(
        matrix.slice(0 until i).map { it[j] }.reversed(), // top
        matrix[i].slice(j + 1 until matrix[j].size), // right
        matrix.slice(i + 1 until matrix.size).map { it[j] }, // bottom
        matrix[i].slice(0 until j).reversed(), // left
    )

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
}