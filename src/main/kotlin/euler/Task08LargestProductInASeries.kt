package euler

import EulerTask
import readInput
import utils.Math.product
import kotlin.math.max

object Task08LargestProductInASeries : EulerTask {

    private const val ADJACENT_DIGITS = 13

    override val name: String = "Largest product in a series"

    override fun solution(): Long = readInput("euler/08")
        .replace("\n", "")
        .windowed(size = ADJACENT_DIGITS, step = 1)
        .fold(0L) { bestProduct, it ->
            val adjacentDigits = it.toCharArray().map { it.digitToInt().toLong() }
            val product = adjacentDigits.product()
            max(bestProduct, product)
        }
}