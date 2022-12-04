package _2022

import readInput

object Task04 {

    // 466
    fun partOne() = parseInput()
        .map { (left, right) -> left.containsAll(right) || right.containsAll(left) }
        .count { it }

    // 865
    fun partTwo() = parseInput()
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