package euler

import EulerTask
import utils.Math.digits
import utils.Math.findPrimesUntil

object Task35CircularPrimes : EulerTask("Circular primes") {

    private val primes = findPrimesUntil(1_000_000).toSet()

    override fun solution(): Int {
        var sum = 1

        main@for (n in 1..1_000_000L step 2) {
            val digits = n.digits()

            for (i in digits.indices) {
                var rotation = 0L

                for (j in digits.indices) {
                    rotation *= 10
                    rotation += digits[(i + j) % digits.size]
                }

                if (!primes.contains(rotation)) {
                    continue@main
                }
            }

            sum++
        }

        return sum
    }

}
