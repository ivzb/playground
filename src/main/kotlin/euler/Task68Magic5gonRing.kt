package euler

import EulerTask
import utils.Combinatorics

object Task68Magic5gonRing : EulerTask("Magic 5-gon ring") {

    override fun solution(): String {
        var bestSolution = ""

        for (permutation in Combinatorics.lexicographicPermutations((1..10).toMutableList())) {
            val line1 = arrayOf(permutation[0], permutation[1], permutation[2])
            val line2 = arrayOf(permutation[3], permutation[2], permutation[4])
            val line3 = arrayOf(permutation[5], permutation[4], permutation[6])
            val line4 = arrayOf(permutation[8], permutation[6], permutation[7])
            val line5 = arrayOf(permutation[9], permutation[7], permutation[1])

            val lines = listOf(line1, line2, line3, line4, line5)

            val line1Sum = line1.sum()
            val line1ExternalNode = line1[0]
            val isSolution = lines.all { line -> line1Sum == line.sum() && line1ExternalNode <= line[0] }

            if (isSolution) {
                val solution = lines.joinToString("") { it.joinToString("") }

                if (solution > bestSolution) {
                    bestSolution = solution
                }
            }
        }

        return bestSolution
    }

}
