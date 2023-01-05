package euler

import EulerTask
import utils.Combinatorics.permute
import utils.Math.findPrimes
import utils.toInt
import utils.toLong

object Task43SubStringDivisibility : EulerTask("Sub-string divisibility") {

    override fun solution(): Long {
        val primes = findPrimes().take(7).toList()
        val permutations = permute(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
        var sum = 0L

        val primeDivisible = HashMap<Int, HashSet<List<Int>>>()

        primes.forEachIndexed { index, prime ->
            primeDivisible[index] = HashSet()

            for (x in 0..9) {
                for (y in 0..9) {
                    for (z in 0..9) {
                        val divisible = listOf(x,y,z)

                        if (divisible.toInt() % prime == 0L) {
                            primeDivisible[index]?.add(divisible)
                        }
                    }
                }
            }
        }

        main@for (permutation in permutations) {
            if (permutation[0] == 0) continue

            for (index in primes.indices) {
                val divisible = permutation.subList(index + 1, index + 4)

                if (primeDivisible[index]?.contains(divisible) == false) {
                    continue@main
                }
            }

            sum += permutation.toLong()
        }

        return sum
    }

}
