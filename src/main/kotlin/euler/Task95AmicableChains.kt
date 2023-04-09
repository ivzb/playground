package euler

import EulerTask
import kotlin.math.sqrt

object Task95AmicableChains : EulerTask("Amicable chains") {

    override fun solution(): Int {
        val store = HashMap<Int, Int>()

        var bestChain = ArrayDeque<Int>()

        for (n in 1..50_000) {
            var last = n
            val seen = HashSet<Int>()
            seen.add(last)

            val chain = ArrayDeque<Int>()
            chain.add(last)

            var isChain = false

            while (true) {
                last = store.getOrPut(last) { sumOfDivisors(last) }

                if (last > 1_000_000) {
                    chain.clear()
                    break
                }

                if (n == last && chain.size > 1) {
                    isChain = true
                    break
                }

                if (seen.contains(last)) {
                    isChain = false
                    break
                }

                seen.add(last)
                chain.add(last)
            }

            if (isChain && chain.size > bestChain.size) {
                bestChain = chain
            }
        }

        return bestChain.min()
    }

    private fun sumOfDivisors(n: Int): Int {
        var sum = 0

        for (i in 1..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) {
                sum += i

                val rem = n / i

                if (rem != i && rem != n) {
                    sum += rem
                }
            }
        }

        return sum
    }

}
