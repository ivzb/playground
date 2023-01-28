package euler

import EulerTask

object Task74DigitFactorialChains : EulerTask("Digit factorial chains") {

    override fun solution(): Int {
        var count = 0

        for (n in 1..1_000_000) {
            if (chain(n) == 60) {
                count++
            }
        }

        return count
    }

    private val countChains = IntArray(3_000_000)
    private val factorialChains = IntArray(3_000_000)
    private val factorials = arrayOf(1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880)

    private fun chain(start: Int): Int {
        var input = start
        val seen = HashSet<Int>()
        val q = ArrayList<Int>()
        var count = 0

        while (!seen.contains(input)) {
            if (countChains[input] != 0) {
                count += countChains[input]
                break
            }

            seen.add(input)
            q.add(input)
            input = factorialChain(input)
            count++
        }

        q.forEachIndexed { index, n ->
            countChains[n] = count - index
        }

        return count
    }

    private fun factorialChain(input: Int): Int {
        if (factorialChains[input] == 0) {
            factorialChains[input] = input.toString().sumOf { factorials[it.digitToInt()] }
        }

        return factorialChains[input]
    }
}
