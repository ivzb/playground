package euler

import EulerTask
import readInput

object Task13LargeSum : EulerTask("Large sum") {

    override fun solution(): Long =
        parseInput().sumOf { it.take(11).toLong() } / 1000

    fun solution_naive(): Long =
        parseInput()
            .map { it.toBigInteger() }
            .sumOf { it }
            .toString()
            .take(10)
            .toLong()

    fun solution_carryOver(): Long {
        val matrix = parseInput().map { it.reversed().toCharArray().map { it.digitToInt() } }

        var carryOver = 0
        val number = ArrayDeque<Int>()

        for (y in matrix[0].indices) {
            var sum = 0

            for (x in matrix.indices) {
                sum += matrix[x][y]
            }

            sum += carryOver
            carryOver = sum / 10
            number += sum % 10
        }

        while (carryOver > 0) {
            number += carryOver % 10
            carryOver /= 10
        }

        return number.reversed().take(10).joinToString("").toLong()
    }

    private fun parseInput(): List<String> =
        readInput("euler/13").split('\n')

}