package euler

import EulerTask
import utils.Math.sumOfProperDivisors

object Task23NonAbundantSums : EulerTask {

    override val name: String = "Non-abundant sums"

    private const val LIMIT = 28123

    override fun solution(): Int {
        val abundant = ArrayList<Int>()
        val result = IntArray(LIMIT+1)

        for (n in 1..LIMIT) {
            val sum = sumOfProperDivisors(n)
            val isAbundant = sum > n

            if (isAbundant) {
                abundant.add(n)
            }

            result[n] = n
        }

        for (i in abundant.indices) {
            for (j in i until abundant.size) {
                val sum = abundant[i] + abundant[j]

                if (sum <= LIMIT) {
                    result[sum] = 0
                }
            }
        }

        return result.sum()
    }

}
