package euler

import EulerTask
import utils.Math.findPrimesUntil
import utils.Math.isPrime

object Task60PrimePairSets : EulerTask("Prime pair sets") {

    private val primes = findPrimesUntil(10_000).toList()

    override fun solution(): Long {
        val q = ArrayDeque<List<Int>>()
        q.addAll(List(primes.size) { index -> listOf(index) })

        while (q.isNotEmpty()) {
            val possible = ArrayList<List<Int>>()

            while (q.isNotEmpty()) {
                val list = q.removeFirst()
                val start = list.last()
                val end = when (list.size) {
                    1,2,3 -> 1_000
                    else -> 2_000
                }

                val listPrimes = list.map { index -> primes[index] }

                for (index in start until end.coerceAtMost(primes.size)) {
                    val prime = primes[index]

                    if (listPrimes.all { isPrimeSet(it, prime) }) {
                        val next = list + index

                        if (next.size == 5) {
                            return next.sumOf { primes[it] }
                        }

                        possible.add(next)
                    }
                }
            }

            q.addAll(possible)
        }

        return -1
    }

    private val cache = HashMap<Pair<Long, Long>, Boolean>()

    private fun isPrimeSet(prime1: Long, prime2: Long): Boolean =
        cache.getOrPut(prime1 to prime2) {
            isPrime("$prime1$prime2".toLong(), primes) &&
            isPrime("$prime2$prime1".toLong(), primes)
        }

}
