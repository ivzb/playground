package euler

import EulerTask
import utils.Math.sumOfProperDivisors

object Task21AmicableNumbers : EulerTask {

    override val name: String = "Amicable numbers"

    override fun solution(): Int = solution_naive()

    private val d = Array(10_000) { 0 }

    init {
        val n = 10_000

        for (i in 1 until 10_000) {
            for (j in 1 until i) {
                if (i % j == 0) {
                    d[i] += j
                }
            }
        }
    }

    private fun solution_naive(): Int {
        var sum = 0

        for (a in 2 until 10_000) {
            val b = d[a]

            if (b > 10_000 || a == b) {
                continue
            }

            if (a == d[b]) {
                sum += a
            }
        }

        return sum
    }

    private fun solution_prime_factorisation(): Int {
        var sum = 0

        for (a in 2 until 10_000) {
            val b = sumOfProperDivisors(a)

            if (b > 10_000 || a == b) {
                continue
            }

            if (a == sumOfProperDivisors(b)) {
                sum += a
            }
        }

        return sum
    }

}
