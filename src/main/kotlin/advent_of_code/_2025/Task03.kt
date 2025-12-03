package advent_of_code._2025

import Task
import readInput
import utils.pow

object Task03 : Task {

    override fun partA(): Long = largestPossibleJoltage(2)

    override fun partB(): Long = largestPossibleJoltage(12)

    private fun largestPossibleJoltage(n: Int) = parseInput()
        .sumOf {
            (0 until n)
                .fold(Joltage(index = -1, result = 0L)) { joltage, i ->
                    val remaining = n - i
                    val canTake = it.size - remaining - joltage.index
                    val range = it.drop(joltage.index + 1).take(canTake)
                    val localMax = range.maxOf { it }
                    val localMaxIndex = range.indexOf(localMax)

                    Joltage(
                        index = joltage.index + localMaxIndex + 1,
                        result = joltage.result + localMax * 10L.pow(remaining - 1)
                    )
                }
                .result
        }

    private fun parseInput() = readInput("_2025/03")
        .lines()
        .map {
            it.toCharArray().map { it.digitToInt().toLong() }
        }

    private data class Joltage(
        val index: Int,
        val result: Long,
    )

}