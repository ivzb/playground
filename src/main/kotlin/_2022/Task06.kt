package _2022

import readInput

object Task06 {

    private const val startOfPacketSize = 4
    private const val startOfMessageSize = 14

    fun partOne() = findMarker(startOfPacketSize)

    fun partTwo() = findMarker(startOfMessageSize)

    private fun findMarker(size: Int): Int {
        return parseInput()
            .windowed(size = size, step = 1, partialWindows = false)
            .mapIndexed { index, it ->
                val isSignal = it.toSet().count() == size
                val marker = it.length + index
                isSignal to marker
            }
            .filter { (isSignal) -> isSignal }
            .map { (_, marker) -> marker }
            .first()
    }

    private fun parseInput() = readInput("_2022/06")

}