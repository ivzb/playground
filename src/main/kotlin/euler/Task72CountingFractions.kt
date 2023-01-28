package euler

import EulerTask
import utils.Math.findPrimesUntil

object Task72CountingFractions : EulerTask("Ordered fractions") {

    override fun solution(): Long = solution_sieve_of_eratosthenes()

    private fun solution_sieve_of_eratosthenes(): Long {
        val limit = 1_000_000
        val phi = IntArray(limit+1)

        for (i in 0..limit) {
            phi[i] = i
        }

        var sum = 0L

        for (i in 2..limit) {
            if (phi[i] == i) {
                for (j in i..limit step i) {
                    phi[j] = phi[j] / i * (i - 1)
                }
            }

            sum += phi[i]
        }

        return sum
    }

    fun solution_sum_phi(): Long {
        var count = 0L

        for (n in 2..1_000_000) {
            count += phi(n)
        }

        return count
    }

    private fun phi(n: Int): Int {
        var n = n
        var result = n

        // Consider all prime factors
        // of n and subtract their
        // multiples from result
        var p = 2

        while (p * p <= n) {
            // Check if p is
            // a prime factor.
            if (n % p == 0) {
                // If yes, then update
                // n and result
                while (n % p == 0) {
                    n /= p
                }

                result -= result / p
            }

            p++
        }

        // If n has a prime factor
        // greater than sqrt(n)
        // (There can be at-most
        // one such prime factor)
        if (n > 1) {
            result -= result / n
        }

        return result
    }

}
