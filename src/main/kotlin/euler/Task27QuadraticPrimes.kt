package euler

import EulerTask
import utils.Math.findPrimes

object Task27QuadraticPrimes : EulerTask {

    override val name: String = "Quadratic primes"

    private val primes = findPrimes().take(1_000).map { it.toInt() }.toSet()

    override fun solution(): Int {
        var best = 0
        var bestA = 0
        var bestB = 0

        for (a in 0..1000) {
            for (b in 0..1000) {
                var primesFound = 0

                for (n in 0..1000) {
                    val f = (n * n) - (a * n) + b
                    val isPrime = primes.contains(f)

                    if (!isPrime) {
                        break
                    }

                    primesFound++
                }

                if (primesFound > best) {
                    best = primesFound
                    bestA = a
                    bestB = b
                }
            }
        }

        return -bestA * bestB
    }

}
