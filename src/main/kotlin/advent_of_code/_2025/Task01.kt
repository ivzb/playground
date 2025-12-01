package advent_of_code._2025

import Task
import readInput
import java.lang.Math.abs
import java.lang.Math.floorDiv

object Task01 : Task {

    private const val INITIAL_COUNTER = 50
    private const val INITIAL_PASSWORD = 0

    override fun partA(): Int = parseInput()
        .fold(INITIAL_COUNTER to INITIAL_PASSWORD) { (counter, password), rotation ->
            val rotationCounter = (counter + rotation) % 100
            val rotationPassword = password + (if (counter == 0) 1 else 0)
            rotationCounter to rotationPassword
        }
        .let { (_, password) ->
            password
        }

    override fun partB(): Int = parseInput()
        .fold(INITIAL_COUNTER to INITIAL_PASSWORD) { (counter, password), rotation ->
            val rotationCounter = (counter + rotation) % 100
            val rotationPassword = password + rotate(counter, counter + rotation)
            rotationCounter to rotationPassword
        }
        .let { (_, password) ->
            password
        }

    private fun rotate(counter: Int, rotation: Int): Int = when {
        counter < rotation -> abs(floorDiv(rotation, 100) - floorDiv(counter, 100))
        counter > rotation -> rotate(-counter, -rotation)
        else -> 0
    }

    private fun parseInput() = readInput("_2025/01")
        .lines()
        .map {
            val direction = it[0]
            val distance = it.drop(1).toInt()

            val sign = when (direction) {
                'L' -> -1
                'R' -> 1
                else -> error("unsupported direction")
            }

            sign * distance
        }
}