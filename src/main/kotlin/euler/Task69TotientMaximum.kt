package euler

import EulerTask
import utils.Math
import utils.Math.findPrimes

object Task69TotientMaximum : EulerTask("Totient maximum") {

    val primes = findPrimes().take(7).toList()

    override fun solution(): Long = solution_primes()

    private fun solution_primes(): Long {
        var product = 1L

        for (prime in primes) {
            product *= prime
        }

        return product
    }

    private fun solution_brute_force(): Long {
        var bestRatio = 0.0
        var bestN = 0L

        for (n in 2..1_000_000L) {
            if (primes.contains(n)) continue

            var relativelyPrimes = 0

            for (i in 1 until n) {
                val gcd = Math.gcd(n, i)

                if (gcd == 1L) {
                    relativelyPrimes++
                }
            }

            val ratio = n.toDouble() / relativelyPrimes

            if (ratio > bestRatio) {
                bestRatio = ratio
                bestN = n
            }
        }

        return bestN
    }

}
