package advent_of_code._2024

import Task
import readInput
import utils.Math.digits

object Task11 : Task {

    override fun partA() = parseInput()
        .let {
            solve(it, 25)
        }

    override fun partB() = parseInput()
        .let {
            solve(it, 75)
        }

    private val cache: HashMap<Pair<Long, Int>, Long> = HashMap()

    private fun solve(stones: List<Long>, times: Int) = stones.sumOf { stone -> blink(stone, times) }

    private fun blink(stone: Long, times: Int): Long {
        if (times == 0) {
            return 1
        }

        val cachedBlink = cache[stone to times]

        if (cachedBlink != null) {
            return cachedBlink
        }

        val result = if (stone == 0L) {
            blink(1, times - 1)
        } else if (stone.toString().length % 2 == 0) {
            val str = stone.toString()
            val left = str.substring(0, str.length / 2).toLong()
            val right = str.substring(str.length / 2).toLong()

            blink(left, times - 1) + blink(right, times - 1)
        } else {
            blink(stone * 2024, times - 1)
        }

        cache[stone to times] = result

        return result
    }

    private fun parseInput() = readInput("_2024/11")
        .split(' ')
        .map { it.toLong() }

}