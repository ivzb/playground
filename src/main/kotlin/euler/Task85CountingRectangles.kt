package euler

import EulerTask
import utils.Math.triangularNumber
import java.lang.Math.abs

object Task85CountingRectangles : EulerTask("Counting rectangles") {

    private const val target = 2_000_000

    override fun solution(): Long {
        return solution_triangular_numbers()
    }

    private fun solution_brute_force(): Long {
        var best = Long.MAX_VALUE
        var bestArea = 0L

        for (x in 1..100L) {
            for (y in x..100L) {
                val r = abs(target - rectangles(x, y))

                if (r < best) {
                    best = r
                    bestArea = x * y
                }
            }
        }

        return bestArea
    }

    private fun rectangles(i: Long, j: Long): Long {
        var count = 0L

        for (x in 1..i) {
            for (y in 1..j) {
                count += x * y
            }
        }

        return count
    }

    private fun solution_triangular_numbers(): Long {
        var best = Long.MAX_VALUE
        var bestArea = 0L

        for (x in 1..100L) {
            for (y in 1..100L) {
                val rectangles = triangularNumber(x) * triangularNumber(y)
                val r = abs(target - rectangles)

                if (r < best) {
                    best = r
                    bestArea = x * y
                }
            }
        }

        return bestArea
    }

}
