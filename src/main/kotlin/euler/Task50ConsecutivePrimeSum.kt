package euler

import EulerTask
import utils.Math.findPrimesUntil

object Task50ConsecutivePrimeSum : EulerTask("Consecutive prime sum") {

    private val primes = findPrimesUntil(1_000_000).toSet()

    override fun solution(): Long {
        var bestCount = 0L
        var bestSum = 0L

        for (n in 0..10) {
            var count = 0L
            var sum = 0L

            for (prime in primes.drop(n)) {
                count++
                sum += prime

                if (sum > 1_000_000) {
                    break
                }

                if (count > bestCount && primes.contains(sum)) {
                    bestCount = count
                    bestSum = sum
                }
            }
        }

        return bestSum
    }

}
