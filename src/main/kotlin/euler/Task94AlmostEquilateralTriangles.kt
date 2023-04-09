package euler

import EulerTask
import utils.Math.gcd
import kotlin.math.abs

object Task94AlmostEquilateralTriangles : EulerTask("Almost equilateral triangles") {

    override fun solution(): Long {
        val N = 1_000_000_000L
        var result = 0L

        for ((a, b, c) in generateTriples(N / 3 + 2)) {
            if (abs(2 * a - c) == 1L) { // (c,c,2*a) is almost equilateral triangle
                result += 2 * a + 2 * c
            }

            if (abs(2 * b - c) == 1L) { // (c, c, 2*b) is almost equilateral triangle
                result += 2 * b + 2 * c
            }
        }

        return result
    }

    private fun generateTriples(k: Long): Sequence<Triple<Long, Long, Long>> = sequence {
        var n = 1L
        var m = 2L

        while (m * m + 1 < k) {
            if (n >= m) {
                n = m % 2
                m = m + 1
            }

            val c = (m * m) + (n * n)

            if (c >= k) {
                n = m
                continue
            }

            if (gcd(n, m) == 1L) {
                yield(Triple(m * m - n * n, 2 * m * n, c))
            }

            n += 2
        }
    }

}
