package euler

import EulerTask
import readInput
import utils.Math.triangularNumber

object Task42CodedTriangleNumbers : EulerTask("Coded triangle numbers") {

    override fun solution(): Int {
        val triangleNumbers = (1..20L).map { n -> triangularNumber(n) }.toSet()
        var triangleWords = 0

        parseInput().forEach { word ->
            val value = word.toCharArray().sumOf { it.code - 'A'.code + 1 }.toLong()

            if (triangleNumbers.contains(value)) {
                triangleWords++
            }
        }

        return triangleWords
    }

    private fun parseInput(): List<String> =
        readInput("euler/42").replace("\"", "").split(',')

}
