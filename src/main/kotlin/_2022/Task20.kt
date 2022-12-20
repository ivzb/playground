package _2022

import Task
import readInput
import java.lang.Math.abs

object Task20 : Task {

    override fun partA() = decrypt(1, 1)

    override fun partB() = decrypt(10, 811589153)

    private fun decrypt(mixes: Int, decryptionKey: Int): Long {
        val amounts = parseInput()
            .mapIndexed { index, amount -> index to amount * decryptionKey }
            .toMutableList()

        repeat(mixes) {
            (0 until amounts.size).forEach { index ->
                move(index, amounts)
            }
        }

        val startIndex = amounts.indexOfFirst { (_, value) -> value == 0L }

        return listOf(1000L, 2000L, 3000L)
            .map { endIndex -> amounts[findIndex(startIndex, endIndex, amounts.size)] }
            .sumOf { (_, amount) -> amount }
    }

    private fun move(index: Int, values: MutableList<Pair<Int, Long>>) {
        val indexFrom = values.indexOfFirst { (initialIndex) -> initialIndex == index }
        val value = values[indexFrom]
        val (_, amount) = value

        if (amount == 0L) return

        values.removeAt(indexFrom)
        val indexTo = findIndex(indexFrom, amount, values.size)
        values.add(indexTo, value)
    }

    private fun findIndex(startIndex: Int, endIndex: Long, size: Int): Int =
        (startIndex + endIndex).mod(size)

    private fun parseInput() =
        readInput("_2022/20").split("\n").map { it.toLong() }

}