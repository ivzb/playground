package _2022

import Task
import readInput

object Task02 : Task {

    private const val LOSE = 0
    private const val DRAW = 3
    private const val WIN = 6

    private const val ROCK = 1
    private const val PAPER = 2
    private const val SCISSORS = 3

    private val strategyOne = mapOf(
        "A X" to DRAW + ROCK,
        "A Y" to WIN + PAPER,
        "A Z" to LOSE + SCISSORS,
        "B X" to LOSE + ROCK,
        "B Y" to DRAW + PAPER,
        "B Z" to WIN + SCISSORS,
        "C X" to WIN + ROCK,
        "C Y" to LOSE + PAPER,
        "C Z" to DRAW + SCISSORS,
    )

    private val strategyTwo = mapOf(
        "A X" to LOSE + SCISSORS,
        "A Y" to DRAW + ROCK,
        "A Z" to WIN + PAPER,
        "B X" to LOSE + ROCK,
        "B Y" to DRAW + PAPER,
        "B Z" to WIN + SCISSORS,
        "C X" to LOSE + PAPER,
        "C Y" to DRAW + SCISSORS,
        "C Z" to WIN + ROCK,
    )

    override fun partA() = parseInput().sumOf { strategyOne[it] ?: 0 }

    override fun partB() = parseInput().sumOf { strategyTwo[it] ?: 0 }

    private fun parseInput() = readInput("_2022/02").split("\n")

}