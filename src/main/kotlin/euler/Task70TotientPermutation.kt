package euler

import EulerTask
import utils.Math.findPrimesUntil

object Task70TotientPermutation : EulerTask("Totient permutation") {

    val primes = findPrimesUntil(4_000).toList()

    override fun solution(): Long {
        var bestN = 0L
        var bestRatio = Double.MAX_VALUE

        for (i in 0 until primes.size) {
            for (j in i + 1 until primes.size) {
                val n = primes[i] * primes[j]

                if (n > 10_000_000) {
                    break
                }

                val phi = (primes[i] - 1) * (primes[j] - 1)

                val isPermutation = n.toString().toCharArray().sorted() == phi.toString().toCharArray().sorted()

                if (!isPermutation) {
                    continue
                }

                val ratio = n.toDouble() / phi

                if (ratio < bestRatio) {
                    bestN = n
                    bestRatio = ratio
                }
            }
        }

        return bestN
    }

}
