package euler

import EulerTask
import utils.Math.findPrimesUntil

object Task47DistinctPrimesFactors : EulerTask("Distinct primes factors") {

    val primes = findPrimesUntil(1_000).toSet()

    override fun solution(): Long {
        var i = 999L
        val consecutive = 4

        main@ while (true) {
            for (j in consecutive - 1 downTo 0) {
                if (distinctPrimeFactors(i + j) != consecutive) {
                    i += j + 1
                    continue@main
                }
            }

            return i
        }
    }

    private fun distinctPrimeFactors(number: Long): Int {
        if (primes.contains(number)) return 1

        val factors = HashSet<Long>()
        var n = number

        for (prime in primes) {
            while (n % prime == 0L) {
                n /= prime
                factors.add(prime)
            }

            if (n == 1L) break
        }

        return factors.size
    }

}
