package euler

import EulerTask
import utils.Math.findPrimesUntil
import utils.pow

object Task46GoldbachsOtherConjecture : EulerTask("Goldbach's other conjecture") {

    private val primes = findPrimesUntil(10_000).toSet()

    override fun solution(): Long {
        val squares = (1..40L).map { i -> 2 * i.pow(2) }.toSet()
        var n = 1L

        while (true) {
            n += 2

            if (primes.contains(n)) {
                continue
            }

            var found = false

            for (prime in primes) {
                if (squares.contains(n - prime)) {
                    found = true
                    break
                }
            }

            if (!found) {
                return n
            }
        }
    }

}
