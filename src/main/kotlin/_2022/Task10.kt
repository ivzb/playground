package _2022

import Task
import readInput

object Task10 : Task {

    private const val REGISTER = 1
    private const val PIXELS = ""
    private const val PIXEL_LIT = '#'
    private const val PIXEL_DARK = '.'
    private const val PIXELS_START = 19
    private const val PIXELS_STEP = 40
    private val SIGNALS = listOf<Int>()

    override fun partA() = parseInput()
        .fold(REGISTER to SIGNALS) { (register, signals), instruction ->
            (register + instruction) to signal(instruction, signals, register)
        }
        .let { (_, signals) -> signals }
        .drop(PIXELS_START)
        .windowed(1, PIXELS_STEP)
        .flatten()
        .sum()

    override fun partB() = parseInput()
        .fold(REGISTER to PIXELS) { (register, pixels), instruction ->
            (register + instruction) to drawPixel(instruction, pixels, register)
        }
        .let { (_, pixels) -> pixels }
        .chunked(PIXELS_STEP)
        .joinToString("\n")

    private fun parseInput() = readInput("_2022/10")
        .split("\n")
        .map { it.split(' ') }

    private fun signal(instruction: List<String>, prevSignals: List<Int>, x: Int): List<Int> =
        instruction.fold(prevSignals) { signals, _ ->
            val cycle = signals.size + 1
            val signal = cycle * x
            signals + signal
        }

    private fun drawPixel(instruction: List<String>, prevPixels: String, register: Int): String =
        instruction.fold(prevPixels) { pixels, _ ->
            pixels + pixelFor(cycle = pixels.length + 1, register)
        }

    private fun pixelFor(cycle: Int, register: Int): Char {
        val sprite = listOf(register - 1, register, register + 1)
        return if (sprite.contains((cycle - 1) % 40)) PIXEL_LIT else PIXEL_DARK
    }

    private operator fun List<String>.component2(): Int? = getOrNull(1)?.toIntOrNull()

    private operator fun Int.plus(instruction: List<String>): Int =
        this + instruction.let { (name, amount) ->
            when (name) {
                "addx" -> (amount ?: error("invalid instruction value $amount"))
                else -> 0
            }
        }

}