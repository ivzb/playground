package euler

import EulerTask

object Task31CoinSums : EulerTask("Coin sums") {

    override fun solution(): Int = solution_iterative()

    private fun solution_iterative(): Int {
        val coins = arrayOf(1, 2, 5, 10, 20, 50, 100, 200)
        val sum = 200
        val ways = Array(sum + 1) { 0 }
        ways[0] = 1

        coins.forEach { coin ->
            for (i in coin .. sum) {
                ways[i] += ways[i - coin]
            }
        }

        return ways[sum]
    }

}
