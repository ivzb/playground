package euler

import EulerTask
import utils.Math.findPrimesUntil
import utils.Math.reversed

object Task37TruncatablePrimes : EulerTask("Truncatable primes") {

    val primes = findPrimesUntil(1_000_000).toSet()

    override fun solution(): Long {
        var sum = 0L

        for (prime in primes) {
            if (prime < 10) continue

            if (prime.isTruncatableFromLeftToRight() && prime.isTruncatableFromRightToLeft()) {
                sum += prime
            }
        }

        return sum
    }

    private fun Long.isTruncatableFromLeftToRight(): Boolean {
        var n = this

        while (n != 0L) {
            if (!primes.contains(n)) {
                return false
            }

            n /= 10
        }

        return true
    }

    private fun Long.isTruncatableFromRightToLeft(): Boolean {
        var n = this

        while (n != 0L) {
            if (!primes.contains(n)) {
                return false
            }

            n = (n.reversed() / 10).reversed()
        }

        return true
    }

}
