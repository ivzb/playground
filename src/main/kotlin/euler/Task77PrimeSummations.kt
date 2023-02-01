package euler

import EulerTask
import utils.Math.findPrimes
import java.lang.Integer.min

object Task77PrimeSummations : EulerTask("Prime summations") {

    override fun solution(): Int = solution_sum()

    private fun solution_sum(): Int {
        val primes = findPrimes().take(100).map { it.toInt() }.toList()
        val max = 100
        val ways = Array(max + 1) { 0 }
        ways[0] = 1

        primes.forEach { prime ->
            for (i in prime .. max) {
                ways[i] += ways[i - prime]
            }
        }

        ways.forEachIndexed { index, way ->
            if (way >= 5_000) {
                return index
            }
        }

        return -1
    }

    private fun solution_brute_force(): Int {
        val primes = findPrimes().take(100).map { it.toInt() }.toList()
        var total = 0

        while (true) {
            total++
            // todo: use caching to avoid recalculating similar paths
            var solutions = 0
            val q = ArrayDeque<List<Int>>()

            for (n in primes) {
                if (n > total) {
                    break
                }

                q.add(listOf(n))
            }

            while (q.isNotEmpty()) {
                val current = q.removeFirst()
                val sum = current.sum()
                val remaining = total - sum

                val to = min(current.last(), remaining)

                for (n in primes) {
                    if (n > to) {
                        break
                    }

                    val r = sum + n

                    if (r == total) {
                        solutions++
                        break
                    }

                    if (r > total) {
                        break
                    }

                    val possible = current + n
                    q.add(possible)
                }
            }

            println("total=$total, solution=$solutions")

            if (solutions >= 5_000) {
                return total
            }
        }
    }

}
