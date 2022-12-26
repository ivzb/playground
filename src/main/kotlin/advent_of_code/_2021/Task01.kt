package advent_of_code._2021

import Task
import readInput

object Task01 : Task {

    override fun partA() = advent_of_code._2021.Task01.parseInput()
        .zipWithNext()
        .count { it.first < it.second }

    override fun partB() = advent_of_code._2021.Task01.parseInput()
        .windowed(size = 3, step = 1) { it.sum() }
        .zipWithNext()
        .count { it.first < it.second }

    private fun parseInput() = readInput("_2021/01")
        .split("\n")
        .map { it.toInt() }

}