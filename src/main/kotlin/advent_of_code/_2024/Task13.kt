package advent_of_code._2024

import Task
import readInput

object Task13 : Task {

    override fun partA() = parseInput()
        .let {
            var result = 0L

            it.forEach { machine ->
                for (ai in 0..100) {
                    for (bi in 0..100) {
                        val x = ai * machine.ax + bi * machine.bx
                        val y = ai * machine.ay + bi * machine.by

                        if (x == machine.px && y == machine.py) {
                            result += (ai * 3) + bi
                            break
                        }
                    }
                }
            }

            result
        }

    override fun partB() = parseInput()
        .let {
            var result = 0L

            it.forEach {
                it.px += 10_000_000_000_000
                it.py += 10_000_000_000_000

                val numeratorX: Long = it.px * it.by - it.py * it.bx
                val denomiatorX: Long = it.ax * it.by - it.ay * it.bx

                val numeratorY: Long = it.px * it.ay - it.py * it.ax
                val denomiatorY: Long = it.ay * it.bx - it.ax * it.by

                if (numeratorX % denomiatorX == 0L && numeratorY % denomiatorY == 0L) {
                    val a = numeratorX / denomiatorX
                    val b = numeratorY / denomiatorY
                    result += (a * 3) + b
                }
            }

            result
        }

    data class Machine(
        val ax: Long,
        val ay: Long,

        val bx: Long,
        val by: Long,

        var px: Long,
        var py: Long,
    )

    private fun parseInput() = readInput("_2024/13")
        .split("\n\n")
        .map {
            val (buttonA, buttonB, prize) = it.lines()

            val buttonRegex = "Button [A|B]: X\\+(\\d+), Y\\+(\\d+)".toRegex()
            val priceRegex = "Prize: X=(\\d+), Y=(\\d+)".toRegex()

            val (_, ax, ay) = buttonRegex.find(buttonA)!!.groupValues
            val (_, bx, by) = buttonRegex.find(buttonB)!!.groupValues
            val (_, prizeX, prizeY) = priceRegex.find(prize)!!.groupValues

            Machine(ax.toLong(), ay.toLong(), bx.toLong(), by.toLong(), prizeX.toLong(), prizeY.toLong())
        }

}