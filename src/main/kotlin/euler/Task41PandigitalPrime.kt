package euler

import EulerTask
import utils.Combinatorics.permute
import utils.Math.findPrimesUntil
import utils.Math.isConsecutivePandigital
import utils.toLong
import java.lang.Math.max

object Task41PandigitalPrime : EulerTask("Pandigital prime") {

    private val primes = findPrimesUntil(7_654_321).toSet()

    override fun solution(): Long = solution_permutations()

    private fun solution_brute_force(): Long {
        var best = 0L

        for (prime in primes) {
            if (prime.isConsecutivePandigital()) {
                best = prime
            }
        }

        return best
    }

    private fun solution_permutations(): Long {
        var best = 0L

        permute(listOf(7,6,5,4,3,2,1)).forEach { permutation ->
            val n = permutation.toLong()

            if (primes.contains(n) && n.isConsecutivePandigital()) {
                best = max(best, n)
            }
        }

        return best
    }

}
