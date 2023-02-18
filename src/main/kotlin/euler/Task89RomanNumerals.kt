package euler

import EulerTask
import readInput
import utils.RomanNumerals

object Task89RomanNumerals : EulerTask("Roman numerals") {

    override fun solution(): Int =
        parseInput().sumOf { inefficientRomanNumber ->
            val decimalNumber = RomanNumerals.toDecimal(inefficientRomanNumber)
            val efficientRomanNumber = RomanNumerals.toRoman(decimalNumber) ?: error("couldn't parse")
            inefficientRomanNumber.length - efficientRomanNumber.length
        }

    private fun parseInput(): List<String> =
        readInput("euler/89")
            .split('\n')

}
