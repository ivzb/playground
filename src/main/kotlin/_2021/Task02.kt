package _2021

import Task
import readInput

object Task02 : Task {

    data class Position(var horizontal: Int, var depth: Int, var aim: Int)

    override fun partA() = parseInput()
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

    override fun partB() = parseInput()
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