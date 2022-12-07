package _2022

import Task
import readInput

object Task06 : Task {

    private const val startOfPacketSize = 4
    private const val startOfMessageSize = 14

    override fun partA() = findMarker(startOfPacketSize)

    override fun partB() = findMarker(startOfMessageSize)

    private fun findMarker(size: Int): Int {
        return parseInput()
            .windowed(size)
            .indexOfFirst { it.toSet().size == size } + size
    }

    private fun parseInput() = readInput("_2022/06")

}