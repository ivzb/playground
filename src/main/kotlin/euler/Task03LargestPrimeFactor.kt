package euler

import EulerTask
import utils.Math.findPrimes

object Task03LargestPrimeFactor : EulerTask("Largest prime factor") {

    override fun solution(): Long {
        val primes = findPrimes().take(1_000).toList()
        var number = 600_851_475_143L

        for (prime in primes) {
            if (number % prime == 0L) {
                number /= prime

                if (number == 1L) {
                    return prime
                }
            }
        }

        error("no solution found")
    }

}