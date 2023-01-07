package euler

import EulerTask
import utils.Math.binom

object Task53CombinatoricSelections : EulerTask("Combinatoric selections") {

    override fun solution(): Int = solution_binom_brute_force()

    fun solution_binom_brute_force(): Int {
        var count = 0
        val target = 1_000_000.toBigInteger()

        for (n in 1..100L) {
            for (r in 1..n) {
                if (binom(n, r) > target) {
                    count++
                }
            }
        }

        return count
    }

    fun solution_pascal_triangle(): Int {
        val n = 101 // must be 1 more than the grid we're evaluating with this approach
        val arr = Array(n) { LongArray(n) }
        var count = 0

        for (n in 1 until n) {
            arr[n][0] = 1
            arr[n][n] = 1

            for (r in 1 until n) {
                arr[n][r] = arr[n-1][r-1] + arr[n-1][r]

                if (arr[n][r] > 1_000_000) {
                    arr[n][r] = 1_000_000
                    count++
                }
            }
        }

        return count
    }

}
