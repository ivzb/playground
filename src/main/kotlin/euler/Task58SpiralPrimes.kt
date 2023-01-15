package euler

import EulerTask
import utils.Direction
import utils.Math.findPrimesUntil
import utils.Math.isPrime
import utils.Matrix
import utils.Point
import java.lang.Math.abs

object Task58SpiralPrimes : EulerTask("Spiral primes") {

    private val primes = findPrimesUntil(30_000)

    override fun solution(): Long = solution_diagonals_formula()

    /**
     * For n by n grid, rotating anticlockwise, and n being odd, the number in the bottom right corner is n^2.
     * The other corners are given by: n^2-n+1, n^2-2n+2, and n^2-3n+3.
     * Adding these together gives the quadratic, 4n^2-6n+6.
     * Then create a loop from 3 to 1001 in steps of 2 and find the running total (starting from 1) of the quadratic.
     */
    private fun solution_diagonals_formula(): Long {
        var n = 1L

        var totalCount = 1L
        var primeCount = 0L
        var ratio = 100.0

        while (ratio >= 0.1) {
            n += 2
            totalCount += 4

            val nSquare = n * n

            listOf(
                nSquare - (3 * n) + 3, // top right
                nSquare - (2 * n) + 2, // top left
                nSquare - n + 1, // bottom left
                // nSquare // bottom right
            ).forEach { diagonal ->
                if (isPrime(diagonal, primes)) {
                    primeCount++
                }
            }

            ratio = primeCount.toDouble() / totalCount
        }

        return n
    }

    private fun solution_brute_force(): Long {
        var n = 7L
        var counter = 1L

        var totalCount = 1L
        var primeDiagonalsCount = 0L

        val start = Point(0, 0)
        var position = start
        var directionIndex = 0

        val directions = listOf(Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH)
        var distance = 1

        while (true) {
            val direction = directions[directionIndex]
            val nextPosition = position + direction.delta
            val isWithinDistance = Matrix.chebyshevDistance(start, nextPosition) <= distance

            if (isWithinDistance) {
                position = nextPosition

                counter++

                val isDiagonal = abs(position.x - start.x) == abs(position.y - start.y)

                if (isDiagonal) {
                    totalCount++

                    if (isPrime(counter, primes)) {
                        primeDiagonalsCount++
                    }
                }
            } else {
                // change direction
                directionIndex++
                directionIndex %= directions.size

                if (directionIndex == 0) {
                    distance++
                }
            }

            if (counter == n * n) {
                val ratio = (primeDiagonalsCount.toDouble() / totalCount * 100).toInt()

                if (ratio < 10) {
                    println(counter)
                    return n
                }

                n += 2
            }
        }
    }

}
