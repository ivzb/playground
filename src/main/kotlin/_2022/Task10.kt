package _2022

import Task
import readInput
import java.lang.Exception
import java.lang.StringBuilder

object Task10 : Task {

    var lastX = 1
    var lastSprite = ""
    var lastIndex = 0
    var cycles = HashMap<Int, Int>()
    var pixels = HashMap<Int, String>()

    override fun partA() = parseInput()
        .also {
            lastX = 1
            lastIndex = 0
            cycles = HashMap()
        }
        .map {
            when (it[0]) {
                "noop" -> {
                    lastIndex += 1
                    cycles[lastIndex] = lastX
                }

                "addx" -> {
                    val add = it[1].toInt()
                    lastIndex += 1
                    cycles[lastIndex] = lastX

                    lastIndex += 1
                    lastX += add
                    cycles[lastIndex] = lastX
                }
            }
        }
        .let {
            var sum = 0
            sum += 20 * cycles[19]!!
            sum += 60 * cycles[59]!!
            sum += 100 * cycles[99]!!
            sum += 140 * cycles[139]!!
            sum += 180 * cycles[179]!!
            sum += 220 * cycles[219]!!
            sum
        }

    override fun partB() = parseInput()
        .also {
            lastX = 1
            lastIndex = 0
            cycles = HashMap()
        }
        .map {
            when (it[0]) {
                "noop" -> {
                    if (lastIndex % 40 == 0) {
                        lastSprite = ""
                    }

                    lastIndex += 1
                    cycles[lastIndex] = lastX

                    lastSprite += draw(lastIndex, lastX)
                    pixels[lastIndex] = lastSprite
                }

                "addx" -> {
                    if (lastIndex % 40 == 0) {
                        lastSprite = ""
                    }

                    val add = it[1].toInt()
                    lastIndex += 1
                    cycles[lastIndex] = lastX

                    lastSprite += draw(lastIndex, lastX)
                    pixels[lastIndex] = lastSprite

                    if (lastIndex % 40 == 0) {
                        lastSprite = ""
                    }


                    lastIndex += 1
                    lastSprite += draw(lastIndex, lastX)
                    pixels[lastIndex] = lastSprite

                    lastX += add
                    cycles[lastIndex] = lastX
                }
            }
        }
        .let {
            val sb = StringBuilder()
            val ps = pixels.filter { (index) -> index % 40 == 0 }
            ps.forEach { (_, pixels) ->
                sb.appendLine(pixels)
            }
            sb.toString().trim()
        }

    fun draw(cycle: Int, sprite: Int): Char {
        val pixel = '#'
        val blank = '.'
        val s = listOf(sprite - 1, sprite, sprite + 1)

        val i = (cycle - 1) % 40
        if (s.contains(i)) {
            return pixel
        } else {
            return blank
        }
    }

    private fun parseInput() = readInput("_2022/10")
        .split("\n")
        .map { it.split(' ') }

}