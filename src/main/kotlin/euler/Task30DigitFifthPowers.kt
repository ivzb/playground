package euler

import EulerTask
import utils.Math.digits

object Task30DigitFifthPowers : EulerTask("Digit fifth powers") {

    private const val EXP = 5

    private val powers = IntArray(10)

    init {
        for (i in 0..9) {
            powers[i] = i.toBigInteger().pow(EXP).toInt()
        }
    }

    override fun solution(): Int {
        var result = 0

        for (n in 1000..200_000) {
            val sum = n.digits().sumOf { powers[it] }

            if (sum == n) {
                result += sum
            }
        }

        return result
    }

}
