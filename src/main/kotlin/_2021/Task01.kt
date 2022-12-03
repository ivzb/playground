package _2021

import readInput

object Task01 {

    fun partOne() = parseInput()
        .zipWithNext()
        .count { it.first < it.second }

    fun partTwo() = parseInput()
        .windowed(size = 3, step = 1) { it.sum() }
        .zipWithNext()
        .count { it.first < it.second }

    private fun parseInput() = readInput("_2021/01")
        .split("\n")
        .map { it.toInt() }

}