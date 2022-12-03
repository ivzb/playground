package _2021

import readInput

object Task02 {

    data class Position(var horizontal: Int, var depth: Int, var aim: Int)

    fun partOne() = parseInput()
        .fold(Position(horizontal = 0, depth = 0, aim = 0)) { position, (command, value) ->
            position.apply {
                when (command) {
                    "forward" -> horizontal += value
                    "down" -> depth += value
                    "up" -> depth -= value
                }
            }
        }
        .let {
            it.horizontal * it.depth
        }

    fun partTwo() = parseInput()
        .fold(Position(horizontal = 0, depth = 0, aim = 0)) { position, (command, value) ->
            position.apply {
                when (command) {
                    "forward" -> {
                        horizontal += value
                        depth += aim * value
                    }
                    "down" -> aim += value
                    "up" -> aim -= value
                }
            }
        }
        .let {
            it.horizontal * it.depth
        }

    private fun parseInput() = readInput("_2021/02")
        .split("\n")
        .map {
            val (command, value) = it.split(" ")
            command to value.toInt()
        }

}