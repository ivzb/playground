package _2022

import readInput

object Task06 {

    private const val startOfPacketSize = 4
    private const val startOfMessageSize = 14

    fun partOne() = findMarker(startOfPacketSize)

    fun partTwo() = findMarker(startOfMessageSize)

    private fun findMarker(size: Int): Int {
        return parseInput()
            .windowed(size)
            .indexOfFirst { it.toSet().size == size } + size
    }

    private fun parseInput() = readInput("_2022/06")

}