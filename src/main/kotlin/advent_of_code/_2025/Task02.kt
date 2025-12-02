package advent_of_code._2025

import Task
import readInput

object Task02 : Task {

    override fun partA(): Long = sumInvalidIDs { id ->
        val firstHalf = id.take(id.length / 2)
        val lastHalf = id.takeLast(id.length / 2)
        id.length % 2 == 0 && firstHalf == lastHalf
    }

    override fun partB(): Long = sumInvalidIDs { id ->
        (1 until id.length)
            .map(id::chunked)
            .any { chunked -> chunked.all { it == chunked[0] } }
    }

    private fun sumInvalidIDs(isInvalidID: (String) -> Boolean): Long = parseInput()
        .fold(0L) { invalidIDsSum, (firstID, lastID) ->
            invalidIDsSum + (firstID .. lastID)
                .filter { id -> isInvalidID(id.toString()) }
                .sum()
        }

    private fun parseInput() = readInput("_2025/02")
        .split(",")
        .map {
            it.split("-").map { it.toLong() }
        }
}