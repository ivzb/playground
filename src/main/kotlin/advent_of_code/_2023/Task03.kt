package advent_of_code._2023

import Task
import readInput
import utils.Bounds.Companion.bounds
import utils.Matrix
import utils.Point

object Task03 : Task {

    override fun partA() = adjacentNumbersOf("[*@/+$=&\\-#%]")
        .flatten()
        .sum()

    override fun partB() = adjacentNumbersOf("\\*")
        .mapNotNull { it.takeIf { it.size == 2 }?.reduce(Int::times) }
        .sum()

    private fun adjacentNumbersOf(regexPattern: String): List<Set<Int>> {
        val matrix = matrix()
        val numbersMatrix = numbersMatrix()

        return parseInput()
            .mapIndexed { rowIndex, row ->
                var lastIndex = 0

                Regex(regexPattern)
                    .findAll(row)
                    .map(MatchResult::value)
                    .map { char ->
                        val colIndex = row.indexOf(char, lastIndex)
                        lastIndex = colIndex + 1
                        val position = Point(rowIndex, colIndex)
                        Matrix.adjacent(position, 2, matrix.keys.bounds())
                            .flatten()
                            .mapNotNull { point -> numbersMatrix[point] }
                            .toSet()
                    }
                    .toList()
            }
            .flatten()
    }

    private fun parseInput() = readInput("_2023/03")
        .split("\n")

    private fun matrix(): Map<Point, Char> {
        val input = parseInput()
        val matrix = HashMap<Point, Char>()

        for (y in input.indices) {
            for (x in input[y].indices) {
                val position = Point(x, y)
                val item = input[y][x]
                matrix[position] = item
            }
        }

        return matrix
    }

    private fun numbersMatrix(): HashMap<Point, Int> {
        val input = parseInput()
        val numbers = HashMap<Point, Int>()

        input
            .mapIndexed { rowIndex, row ->
                var lastIndex = 0

                Regex("[0-9]+").findAll(row)
                    .map(MatchResult::value)
                    .forEach { number ->
                        val colIndex = row.indexOf(number, lastIndex)

                        for (currentPosition in colIndex until colIndex + number.length) {
                            val position = Point(rowIndex, currentPosition)
                            numbers[position] = number.toInt()
                        }

                        lastIndex = colIndex + number.length
                    }
            }

        return numbers
    }
}