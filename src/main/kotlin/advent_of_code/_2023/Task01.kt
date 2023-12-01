package advent_of_code._2023

import Task
import readInput

object Task01 : Task {

    override fun partA(): Int {
        return parseInput()
            .map { it.toCharArray() }
            .map { it.filter { it.isDigit() } }
            .map { "${it.first()}${it.last()}".toInt() }
            .sum()
    }

    override fun partB(): Int {
        return parseInput()
            .map {
                var firstResult = Int.MAX_VALUE
                var firstIndexResult = Int.MAX_VALUE

                var lastResult = Int.MIN_VALUE
                var lastIndexResult = Int.MIN_VALUE

                map.forEach { (numberWord, number) ->
                    val currentNumberFirstIndex = it.indexOf(numberWord)
                    val currentNumberLastIndex = it.lastIndexOf(numberWord)

                    if (currentNumberFirstIndex != -1 && currentNumberFirstIndex < firstIndexResult) {
                        firstIndexResult = currentNumberFirstIndex
                        firstResult = number
                    }

                    if (currentNumberLastIndex != -1 && currentNumberLastIndex > lastIndexResult) {
                        lastIndexResult = currentNumberLastIndex
                        lastResult = number
                    }
                }

                "$firstResult$lastResult".toInt()
            }
            .sum()
    }

    private fun parseInput() = readInput("_2023/01")
        .split("\n")

    private val map = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

}