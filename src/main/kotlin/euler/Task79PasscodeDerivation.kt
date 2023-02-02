package euler

import EulerTask
import readInput
import utils.Combinatorics.permute

object Task79PasscodeDerivation : EulerTask("Passcode derivation") {

    override fun solution(): Int {
        val rules = parseInput()
        val digits = rules.flatten().distinct()

        main@for (permutation in permute(digits)) {
            val indices = IntArray(10)

            permutation.forEachIndexed { index, n ->
                indices[n] = index
            }

            for (rule in rules) {
                val (a, b, c) = rule

                if (indices[a] > indices[b] || indices[b] > indices[c]) {
                    continue@main
                }
            }

            return permutation.joinToString("").toInt()
        }

        return -1
    }

    private fun parseInput(): List<List<Int>> =
        readInput("euler/79")
            .split('\n')
            .map { it.toCharArray().map { it.digitToInt() } }
            .sortedWith(compareBy({ it[0] }, { it[2] }, { it[1] }))
            .distinct()

}
