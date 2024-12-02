package advent_of_code._2024

import Task
import readInput
import kotlin.math.abs

object Task01 : Task {

    override fun partA(): Int = parseInput()
        .let { (left, right) ->
            left.foldIndexed(ArrayList<Int>()) { index, acc, _ ->
                val distance = abs(left[index] - right[index])
                acc.add(distance)
                acc
            }
        }
        .sum()

    override fun partB(): Int = parseInput()
        .let { (left, right) ->
            val grouped = right.groupingBy { it }.eachCount()

            left.fold(ArrayList<Int>()) { acc, number ->
                grouped[number]?.let { occurrences ->
                    val similarityScore = number * occurrences
                    acc.add(similarityScore)
                }

                acc
            }
        }
        .sum()

    private fun parseInput() = readInput("_2024/01")
        .lines()
        .map {
            it.split("   ").let { split ->
                split[0].toInt() to split[1].toInt()
            }
        }
        .let {
            it.map { it.first }.sorted() to it.map { it.second }.sorted()
        }
}