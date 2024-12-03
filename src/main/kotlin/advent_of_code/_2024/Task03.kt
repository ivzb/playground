package advent_of_code._2024

import Task
import readInput
import kotlin.math.abs

object Task03 : Task {

    override fun partA(): Int = parseInput()
        .let {
            val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
            regex.findAll(it).sumOf {
                val (_, a, b) = it.groupValues.map { it.toIntOrNull() ?: 0 }
                a * b
            }
        }

    override fun partB() = parseInput()
        .let {
            val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)")
            var enabled = 1

            regex.findAll(it).sumOf { match ->
                when (match.value) {
                    "do()" -> {
                        enabled = 1
                        0
                    }

                    "don't()" -> {
                        enabled = 0
                        0
                    }

                    else -> {
                        val (_, a, b) = match.groupValues.map { it.toIntOrNull() ?: 0 }
                        a * b * enabled
                    }
                }
            }
        }

    private fun parseInput() = readInput("_2024/03")

}