package advent_of_code._2025

import Task
import readInput
import utils.transpose
import utils.transposeStrings
import java.lang.Math.abs
import java.lang.Math.floorDiv
import java.math.BigInteger

object Task06 : Task {

    override fun partA(): Long = parseInput()
        .map { it.tokens() }
        .let { lines ->
            lines[0].indices.sumOf { colIndex ->
                val column = lines.map { row -> row[colIndex] }

                column
                    .dropLast(1)
                    .solve(operator = column.last())
            }
        }

    override fun partB(): Long = parseInput()
        .let { lines ->
            val startIndex = 0
            val endIndex = lines[0].length
            val indexes = lines.last()
                .mapIndexed { index, char -> operatorIndex(index, char) }
                .filterNot { it == -1 }

            val operators = lines.last().tokens()

            lines
                .dropLast(1)
                .map {
                    (listOf(startIndex) + indexes + listOf(endIndex))
                        .zipWithNext()
                        .map { (start, end) -> it.substring(start, end) }
                }
                .transpose()
                .mapIndexed { index, column ->
                    column
                        .transposeStrings()
                        .map { it.trim() }
                        .filterNot { it.isEmpty() }
                        .solve(operators[index])
                }
                .sum()
        }

    private fun operatorIndex(index: Int, char: Char) =
        if (char == '+' || char == '*') index - 1 else -1

    private fun String.tokens(): List<String> =
        trim().replace("\\s+".toRegex(), " ").split(" ")

    private fun List<String>.solve(operator: String): Long =
        fold(if (operator == "+") 0L else 1L) { acc, s ->
            when (operator) {
                "+" -> acc + s.toLong()
                "*" -> acc * s.toLong()
                else -> throw IllegalArgumentException("unsupported operator $operator")
            }
        }

    private fun parseInput() = readInput("_2025/06")
        .lines()

}