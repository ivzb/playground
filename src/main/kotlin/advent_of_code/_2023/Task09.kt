package advent_of_code._2023

import Task
import readInput

object Task09 : Task {

    override fun partA(): Long = parseInput().sumOf { it.last() + extrapolate(it, isEnd = true) }

    override fun partB(): Long = parseInput().sumOf { it.first() - extrapolate(it, isEnd = false) }

    private fun extrapolate(it: List<Long>, isEnd: Boolean): Long {
        val windowed = it.windowed(size = 2, step = 1).map { (a, b) -> b - a }

        if (windowed.all { it == 0L }) {
            return 0
        }

        return if (isEnd) {
            extrapolate(windowed, isEnd) + windowed.last()
        } else {
            windowed.first() - extrapolate(windowed, isEnd)
        }
    }

    private fun parseInput() = readInput("_2023/09")
        .split("\n")
        .map { it.split(' ').map { it.toLong() } }

}