package euler

import EulerTask
import utils.Combinatorics.isPermutation
import utils.Math.findPrimesUntil

object Task49PrimePermutations : EulerTask("Prime permutations") {

    private val primes = findPrimesUntil(10_000)

    override fun solution(): Long {
        for (i in primes.indices) {
            val prime1 = primes[i]

            if (prime1 < 1_500) {
                continue
            }

            for (j in i + 1 until primes.size) {
                val prime2 = primes[j]

                if (!isPermutation(prime1, prime2)) {
                    continue
                }

                val diff1 = prime2 - prime1

                for (k in j + 1 until primes.size) {
                    val prime3 = primes[k]
                    val diff2 = prime3 - prime2

                    if (diff1 != diff2 || !isPermutation(prime2, prime3)) {
                        continue
                    }

                    return "$prime1$prime2$prime3".toLong()
                }
            }
        }

        return -1
    }

}
