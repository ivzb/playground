package advent_of_code._2023

import Task
import readInput

object Task02 : Task {

    override fun partA(): Any {
        val result = parseInput()
            .filter { (_, cubeSets) ->
                val isRedPossible = cubeSets.filter { it.color == Color.Red }.all { it.count <= 12 }
                val isGreenPossible = cubeSets.filter { it.color == Color.Green }.all { it.count <= 13 }
                val isBluePossible = cubeSets.filter { it.color == Color.Blue }.all { it.count <= 14 }

                isRedPossible && isGreenPossible && isBluePossible
            }
            .sumOf { (gameID) -> gameID }

        return result
    }

    override fun partB(): Any {
        val result = parseInput()
            .map { (_, cubeSets) ->
                val maxRed = cubeSets.filter { it.color == Color.Red }.maxOf { it.count }
                val maxGreen = cubeSets.filter { it.color == Color.Green }.maxOf { it.count }
                val maxBlue = cubeSets.filter { it.color == Color.Blue }.maxOf { it.count }

                maxRed * maxGreen * maxBlue
            }
            .sum()

        return result
    }

    private fun parseInput() = readInput("_2023/02")
        .split("\n")
        .map {
            val (game, sets) = it.split(": ")
            val (_, gameID) = game.split("Game ")

            val cubeSets = sets.split(";").map {
                it.split(", ").map {
                    val (count, color) = it.trim().split(" ")
                    CubeSet(count.toInt(), Color.of(color))
                }
            }

            gameID.toInt() to cubeSets.flatten()
        }

    data class CubeSet(val count: Int, val color: Color)

    enum class Color(val value: String) {
        Red("red"),
        Green("green"),
        Blue("blue");

        companion object {

            fun of(value: String): Color {
                return values().find { it.value == value } ?: error("color $value not found")
            }
        }
    }

}