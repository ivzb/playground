package euler

import EulerTask
import utils.Math.findPrimes
import kotlin.math.sqrt

object Task12HighlyDivisibleTriangularNumber : EulerTask {

    override val name: String = "Highly divisible triangular number"

    val primes = findPrimes().take(1_000).toList()

    override fun solution(): Long {
        var n = 1
        var triangleSum = 0L

        while (true) {
            triangleSum += n
            val divisors = divisors(triangleSum.toInt())

            if (divisors >= 500) {
                return triangleSum
            }

            n++
        }
    }

    private fun divisors(n: Int): Int {
        var divisors = 0
        val sq = sqrt(n.toDouble()).toInt()
        
        for (i in 1..sq) {
            if (n % i == 0) {
                divisors += 2
            }
        }

        if (n == sq * sq) {
            // correction for a perfect square
            divisors--
        }

        return divisors
    }

    fun solution_optimized(): Long {
        var n = 3L // start with a prime
        var Dn = 2 // number of divisors for any prime
        var cnt = 0 // divisors, to ensure the while loop is entered

        while (cnt <= 500) {
            n++
            var n1 = n

            if (n1 % 2 == 0L) {
                n1 /= 2
            }

            var Dn1 = 1
            val P = primes.size

            for (i in 0..P) {
                val prime = primes[i]

                if (prime * prime > n1) {
                    // when the prime divisor would be greater than the residual n1,
                    // that residual n1 is the last prime factor with an exponent = 1
                    // no need to identify it
                    Dn1 *= 2
                    break
                }

                var exponent = 1

                while (n1 % prime == 0L) {
                    exponent++
                    n1 /= prime
                }

                if (exponent > 1) {
                    Dn1 *= exponent
                }

                if (n1  == 1L) {
                    break
                }
            }

            cnt = Dn*Dn1
            Dn = Dn1
        }

        return (n*(n-1))/2
    }

}