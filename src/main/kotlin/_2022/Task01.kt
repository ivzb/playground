package _2022

import Task
import readInput

object Task01: Task {

    override fun partA() = parseInput().maxOf { it }

    override fun partB() = parseInput().sortedDescending().take(3).sum()

    private fun parseInput() = readInput("_2022/01")
        .split("\n\n")
        .map { it.split("\n").sumOf { it.toInt() } }

}