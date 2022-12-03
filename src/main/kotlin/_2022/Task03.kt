package _2022

import readInput

object Task03 {

    private val priority = ('a'..'z') + ('A'..'Z')

    // 7997
    fun partOne() = parseInput()
        .map { it.chunked(it.length / 2) }
        .sumOf { sumPriority(it) }

    // 2545
    fun partTwo() = parseInput()
        .chunked(3)
        .sumOf { sumPriority(it) }

    private fun sumPriority(it: List<String>): Int = it
        .map { it.toSet() }
        .reduce { left, right -> left intersect right }
        .sumOf { priority.indexOf(it) + 1 }

    private fun parseInput() = readInput("_2022/03").split("\n")

}