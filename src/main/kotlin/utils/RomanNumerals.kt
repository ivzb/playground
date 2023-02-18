package utils

import java.util.*

object RomanNumerals {

    private val romanToDecimal = mapOf(
        'I' to 1,
        'V' to 5,
        'X' to 10,
        'L' to 50,
        'C' to 100,
        'D' to 500,
        'M' to 1000,
    )

    private val decimalToRoman = TreeMap(mapOf(
        1 to "I",
        4 to "IV",
        5 to "V",
        9 to "IX",
        10 to "X",
        40 to "XL",
        50 to "L",
        90 to "XC",
        100 to "C",
        400 to "CD",
        500 to "D",
        900 to "CM",
        1000 to "M",
    ))

    fun toRoman(number: Int): String? {
        val closest = decimalToRoman.floorKey(number)

        return if (number == closest) {
            decimalToRoman[number]
        } else {
            decimalToRoman[closest] + toRoman(number - closest)
        }
    }

    fun toDecimal(number: String): Int {
        var prev = 0
        var result = 0

        for (c in number.reversed()) {
            val current = romanToDecimal[c] ?: error("undefined roman number $c")
            result += current

            if (current < prev) {
                result -= 2 * current
            }

            prev = current
        }

        return result
    }

}