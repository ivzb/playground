package _2021

import readInput

object Task01 {

    private data class Measurement(val value: Int, val level: Level)

    private enum class Level {
        NA,
        DECREASED,
        INCREASED,
        NO_CHANGE
    }

    fun partOne() = parseInput()
        .countIncreasedLevel()

    fun partTwo() = parseInput()
        .windowed(size = 3, step = 1) { it.sum() }
        .countIncreasedLevel()

    private fun List<Int>.countIncreasedLevel(): Int =
        fold(ArrayList<Measurement>()) { measurements, it ->
            val last = measurements.lastOrNull()
            val level = when {
                last == null -> Level.NA
                it > last.value -> Level.INCREASED
                it < last.value -> Level.DECREASED
                else -> Level.NO_CHANGE
            }

            measurements.add(Measurement(it, level))
            measurements
        }
        .count { it.level == Level.INCREASED }

    private fun parseInput() = readInput("_2021/01")
        .split("\n")
        .map { it.toInt() }

}