package euler

import EulerTask
import readInput

object Task42CodedTriangleNumbers : EulerTask("Coded triangle numbers") {

    override fun solution(): Int {
        val triangleNumbers = (1..20).map { n -> triangleNumber(n) }.toSet()
        var triangleWords = 0

        parseInput().forEach { word ->
            val value = word.toCharArray().sumOf { it.code - 'A'.code + 1 }

            if (triangleNumbers.contains(value)) {
                triangleWords++
            }
        }

        return triangleWords
    }

    private fun triangleNumber(n: Int): Int = n * (n + 1) / 2

    private fun parseInput(): List<String> =
        readInput("euler/42").replace("\"", "").split(',')

}
