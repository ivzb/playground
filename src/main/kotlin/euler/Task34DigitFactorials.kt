package euler

import EulerTask
import utils.Math.digits
import utils.Math.factorial

object Task34DigitFactorials : EulerTask("Digit factorials") {

    override fun solution(): Int {
        var sum = 0

        for (n in 3..50_000) {
            val digitsFactorialSum = n.digits().map { factorial(it) }.sumOf { it }

            if (digitsFactorialSum == n.toBigInteger()) {
                sum += n
            }
        }

        return sum
    }
}
