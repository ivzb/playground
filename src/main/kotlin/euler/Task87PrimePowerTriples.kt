package euler

import EulerTask
import utils.Math.findPrimesUntil
import utils.pow

object Task87PrimePowerTriples : EulerTask("Prime power triples") {

    private const val limit = 50_000_000
    // √(50_000_000 - (2^3 + 2^4)) ≈ 7071
    private val primes = findPrimesUntil(7071)

    override fun solution(): Int {
        val squares = ArrayList<Long>()
        val cubes = ArrayList<Long>()
        val fourths = ArrayList<Long>()

        for (prime in primes) {
            prime.pow(2).let { if (it < limit) squares.add(it) }
            prime.pow(3).let { if (it < limit) cubes.add(it) }
            prime.pow(4).let { if (it < limit) fourths.add(it) }
        }

        val solutions = HashSet<Long>()

        for (a in squares) {
            for (b in cubes) {
                val ab = a + b

                if (ab > limit) {
                    break
                }

                for (c in fourths) {
                    val abc = ab + c

                    if (abc > limit) {
                        break
                    }

                    solutions.add(abc)
                }
            }
        }

        return solutions.size
    }

}
