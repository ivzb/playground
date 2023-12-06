package advent_of_code._2023

import Task
import readInput

object Task06 : Task {

    override fun partA(): Int {
        val (times, distances) = parseInput().map { it.filter { it.isNotEmpty() }.map { it.toLong() } }

        return (times.indices).map { i ->
            val totalMilliseconds = times[i]
            val recordDistance = distances[i]

            calculatePossibleWinningWays(totalMilliseconds, recordDistance)
        }
        .reduce(Int::times)
    }

    override fun partB(): Int {
        val (totalMilliseconds, recordDistance) = parseInput().map {
            it.filter { it.isNotEmpty() }.joinToString("").toLong()
        }

        return calculatePossibleWinningWays(totalMilliseconds, recordDistance)
    }

    private fun calculatePossibleWinningWays(totalMilliseconds: Long, recordDistance: Long): Int =
        (1..totalMilliseconds)
            .map { holdMilliseconds ->
                val remainingMilliseconds = totalMilliseconds - holdMilliseconds
                val distance = holdMilliseconds * remainingMilliseconds
                distance
            }
            .count {
                it > recordDistance
            }

    private fun parseInput() = readInput("_2023/06")
        .split("\n")
        .map { it.substringAfter(':').split(' ') }

}