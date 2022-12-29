package euler

import EulerTask

object Task17NumberLetterCounts : EulerTask {

    override val name: String = "Number letter counts"

    override fun solution(): Int =
        (1..1000).fold(0) { length, n -> length + spell(n).length }

    private fun spell(n: Int): String {
        var number = n
        val result = StringBuilder()

        if (number >= 1000) {
            result.append(words[number / 1000])
            result.append(words[1000])
            number %= 1000
        }

        if (number >= 100) {
            result.append(words[number / 100])
            result.append(words[100])
            number %= 100
        }

        if (number >= 20) {
            if (result.isNotEmpty()) {
                result.append("and")
            }

            result.append(words[(number / 10) * 10])

            if (number % 10 != 0) {
                result.append(words[number % 10])
            }

            number = 0
        }

        if (number > 0) {
            if (result.isNotEmpty()) {
                result.append("and")
            }

            result.append(words[number])
        }

        return result.toString()
    }

    private val words = mapOf(
        1 to "one",
        2 to "two",
        3 to "three",
        4 to "four",
        5 to "five",
        6 to "six",
        7 to "seven",
        8 to "eight",
        9 to "nine",
        10 to "ten",
        11 to "eleven",
        12 to "twelve",
        13 to "thirteen",
        14 to "fourteen",
        15 to "fifteen",
        16 to "sixteen",
        17 to "seventeen",
        18 to "eighteen",
        19 to "nineteen",
        20 to "twenty",
        30 to "thirty",
        40 to "forty",
        50 to "fifty",
        60 to "sixty",
        70 to "seventy",
        80 to "eighty",
        90 to "ninety",
        100 to "hundred",
        1000 to "thousand",
    )
}
