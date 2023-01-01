package euler

import EulerTask
import utils.Direction
import utils.Matrix.chebyshevDistance
import utils.Point

object Task28NumberSpiralDiagonals : EulerTask("Number spiral diagonals") {

    override fun solution(): Int = solution_brute_force()

    private fun solution_brute_force(): Int {
        val n = 1001
        val matrix = Array(n) { Array(n) { 0 } }

        var counter = 1

        val start = Point(n / 2, n / 2)
        var position = start

        val directions = listOf(Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH)
        var directionIndex = 0

        matrix[position.y][position.x] = counter

        var distance = 1

        while (counter < n * n) {
            val direction = directions[directionIndex]
            val nextPosition = position + direction.delta
            val isWithinDistance = chebyshevDistance(start, nextPosition) <= distance

            if (isWithinDistance) {
                position = nextPosition
                matrix[position.y][position.x] = ++counter
            } else {
                // change direction
                directionIndex++
                directionIndex %= directions.size

                if (directionIndex == 0) {
                    distance++
                }
            }
        }

        var diagonalSum = 0

        for (i in 0 until n) {
            diagonalSum += matrix[i][i]
            diagonalSum += matrix[i][n - i - 1]
        }

        diagonalSum -= matrix[n / 2][n / 2] // remove duplicated center

        return diagonalSum
    }

    /**
     * For n by n grid, and n being odd, the number in the top right corner is n^2.
     * The other corners are given by: n^2-n+1, n^2-2n+2, and n^2-3n+3.
     * Adding these together gives the quadratic, 4n^2-6n+6.
     * Then create a loop from 3 to 1001 in steps of 2 and find the running total (starting from 1) of the quadratic.
     */
    private fun solution_math(): Int {
        return (3..1001 step 2).fold(1) { sum, n ->
            sum + 4 * (n * n) - 6 * n + 6
        }
    }

}
