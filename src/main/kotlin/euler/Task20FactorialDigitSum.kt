package euler

import EulerTask
import utils.Math.factorial

object Task20FactorialDigitSum : EulerTask("Factorial digit sum") {

    override fun solution(): Int =
        factorial(100).toString().sumOf { it.digitToInt() }

}
