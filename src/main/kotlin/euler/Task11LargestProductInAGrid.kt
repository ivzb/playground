package euler

import EulerTask
import readInput
import utils.Bounds.Companion.bounds
import utils.Math.product
import utils.Matrix
import utils.Point
import kotlin.math.max

object Task11LargestProductInAGrid : EulerTask("Largest product in a grid") {

    private const val DISTANCE = 4

    override fun solution(): Long =
        parseInput().let { matrix ->
            matrix.keys.fold(0L) { bestProduct, position ->
                val currentBestProduct = Matrix.adjacent(position, DISTANCE, matrix.keys.bounds())
                    .map { adjacent ->
                        adjacent
                            .map { adjacentPosition ->
                                matrix[adjacentPosition] ?: error("undefined position $adjacentPosition")
                            }
                            .product()
                    }
                    .max()

                max(bestProduct, currentBestProduct)
            }
        }

    private fun parseInput(): HashMap<Point, Long> {
        val matrix = readInput("euler/11")
            .split("\n")
            .map {
                it.split(' ').map { it.toLong() }
            }

        val points = HashMap<Point, Long>()

        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                val position = Point(x, y)
                val item = matrix[y][x]
                points[position] = item
            }
        }

        return points
    }
}