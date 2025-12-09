package advent_of_code._2025

import Task
import readInput
import java.lang.Long.max

object Task09 : Task {

    override fun partA() = parseInput()
        .let {
            var maxArea = Long.MIN_VALUE

            for (i in it.indices) {
                val (ax, ay) = it[i]

                for (j in it.indices) {
                    val (bx, by) = it[j]
                    val area = (bx - ax - 1) * (by - ay - 1)
                    maxArea = max(maxArea, area)
                }
            }

            maxArea
        }

    override fun partB() = parseInput()
        .let {
            0L
        }

    private fun parseInput() = readInput("_2025/09")
        .lines()
        .map {
            it.split(",").map { it.toLong() }
        }
}