package advent_of_code._2022

import Task
import readInput

object Task25 : Task {

    private const val BASE = 5

    private val FROM_SNAFU_MAPPING = mapOf(
        '=' to -2L,
        '-' to -1L,
        '0' to 0L,
        '1' to 1L,
        '2' to 2L,
    )

    private val TO_SNAFU_MAPPING = mapOf(
        -2L to '=',
        -1L to '-',
        0L to '0',
        1L to '1',
        2L to '2',
    )

    override fun partA() = parseInput().let { lines ->
        val total = lines.fold(0L) { total, line ->
            val amount = convertFromSNAFU(line)
            total + amount
        }

        convertToSNAFU(total)
    }

    override fun partB() = Unit

    fun convertFromSNAFU(snafu: String): Long {
        var number = 0L

        snafu.forEach { char ->
            val place = FROM_SNAFU_MAPPING[char] ?: error("undefined char $char")
            number *= BASE
            number += place
        }

        return number
    }

    fun convertToSNAFU(input: Long): String {
        var number = input
        var snafu = ""

        while (number != 0L) {
            val place = (number + 2) % BASE
            val char = TO_SNAFU_MAPPING[place - 2] ?: error("undefined place $place")
            snafu = char + snafu
            number = (number + 2) / BASE
        }

        return snafu
    }

    private fun parseInput() = readInput("_2022/25")
        .split("\n")

}