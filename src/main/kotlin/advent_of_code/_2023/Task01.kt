package advent_of_code._2023

import Task
import readInput

object Task01 : Task {

    override fun partA(): Int {
        return parseInput()
            .map { it.filter { it.isDigit() } }
            .sumOf { "${it.first()}${it.last()}".toInt() }
    }

    override fun partB(): Int {
        return parseInput()
            .sumOf { row ->
                val (_, firstDigit) = map
                    .map { (key, value) -> row.indexOf(key) to value }
                    .filter { (key) -> key != -1 }
                    .minBy { (key) -> key }

                val (_, lastDigit) = map
                    .map { (key, value) -> row.lastIndexOf(key) to value }
                    .filter { (key) -> key != -1 }
                    .maxBy { (key) -> key }

                "$firstDigit$lastDigit".toInt()
            }
    }

    private fun parseInput() = readInput("_2023/01")
        .split("\n")

    private val map = (1..9).associateBy { it.toString() } + mapOf(
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