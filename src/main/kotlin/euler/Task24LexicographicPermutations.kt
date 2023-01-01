package euler

import EulerTask
import utils.Combinatorics.lexicographicPermutations
import utils.Combinatorics.lexicographicPermutationAt

object Task24LexicographicPermutations : EulerTask("Lexicographic permutations") {

    override fun solution(): String = solution_factorial()

    private fun solution_brute_force(): String =
        lexicographicPermutations((0..9).toMutableList())
            .take(1_000_000)
            .last()
            .joinToString("")

    private fun solution_factorial(): String =
        lexicographicPermutationAt((0..9).toMutableList(), 1_000_000).joinToString("")

}
