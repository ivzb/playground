package advent_of_code._2024

import Task
import readInput

object Task07 : Task {

    override fun partA() = solve(withConcat = false)

    override fun partB() = solve(withConcat = true)

    private fun solve(withConcat: Boolean): Long = parseInput()
        .filter { (targetValue, numbers) ->
            solve(numbers, index = 1, result = numbers[0], targetValue, withConcat)
        }
        .sumOf { (testValue) -> testValue }

    private fun solve(numbers: List<Long>, index: Int, result: Long, targetValue: Long, withConcat: Boolean): Boolean {
        if (numbers.size == index) {
            return result == targetValue
        }

        if (result > targetValue) {
            return false
        }

        return solve(numbers, index + 1, result + numbers[index], targetValue, withConcat) ||
                solve(numbers, index + 1, result * numbers[index], targetValue, withConcat) ||
                (withConcat && solve(numbers, index + 1, "$result${numbers[index]}".toLong(), targetValue, withConcat))
    }

    private fun parseInput() = readInput("_2024/07")
        .lines()
        .map {
            val split = it.split(": ")
            val testValue = split[0].toLong()
            val remainingNumbers = split[1].split(' ').map { it.toLong() }

            testValue to remainingNumbers
        }

}