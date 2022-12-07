package _2022

import Task
import readInput

object Task04 : Task {

    override fun partA() = parseInput()
        .map { (left, right) -> left.containsAll(right) || right.containsAll(left) }
        .count { it }

    override fun partB() = parseInput()
        .map { (left, right) -> left.intersect(right).isNotEmpty() }
        .count { it }

    private fun parseInput() = readInput("_2022/04")
        .split("\n")
        .map {
            it
                .split(",")
                .map { it.split('-').map { it.toInt() } }
                .map { (from, to) -> (from..to).toSet() }
        }

}