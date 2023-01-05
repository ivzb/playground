package euler

import EulerTask
import utils.Math.gcd
import kotlin.math.sqrt

object Task39IntegerRightTriangles : EulerTask("Integer right triangles") {

    override fun solution() = solution_brute_force()

    fun solution_brute_force(): Int {
        var bestSolutions = 0
        var bestP = 0

        for (p in 1..1000) {
            var solutions = 0

            for (a in 1..p / 4) {
                for (b in a..(p - a) / 2) {
                    if (a + b > p) break

                    val c = sqrt((a * a) + (b * b) + 0.0)

                    if (a + b + c == p.toDouble()) {
                        solutions++
                    }
                }
            }

            if (solutions > bestSolutions) {
                bestSolutions = solutions
                bestP = p
            }
        }

        return bestP
    }

    private fun solution_pythagorean_triples(): Int {
        var bestP = 0
        var bestCount = 0
        val count = Array(1001) { 0 }

        for (m in 2..500) {
            val mIsOdd = m % 2 == 1

            for (n in 1..m) {
                val nIsOdd = n % 2 == 1

                if (mIsOdd && nIsOdd) continue
                if (m * (m + n) > 500) break
                if (gcd(m.toLong(), n.toLong()) > 1) continue

                for (k in 1..100) {
                    val perimeter = 2 * k * m * (m + n)

                    if (perimeter > 1000) break

                    count[perimeter]++
                }
            }
        }

        for (p in 1..1000) {
            if (count[p] > bestCount) {
                bestCount = count[p]
                bestP = p
            }
        }

        return bestP
    }

}
