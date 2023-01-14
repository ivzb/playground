package euler

import EulerTask
import utils.Math.digits
import java.math.BigInteger

object Task56PowerfulDigitSum : EulerTask("Powerful digit sum") {

    override fun solution(): Int = solution_exact()

    private fun solution_brute_force(): Int {
        var best = BigInteger.ZERO

        for (number in 1 until 100) {
            val n = number.toBigInteger()

            for (exp in 80 until 100) {
                val r = n.pow(exp)
                val digits = r.digits()
                val sum = digits.sumOf { it }

                if (sum > best) {
                    best = sum
                }
            }
        }

        return best.toInt()
    }

    private fun solution_exact(): Int {
        val n = 99.toBigInteger()
        val exp = 95
        val digits = n.pow(exp).digits()
        val sum = digits.sumOf { it }

        return sum.toInt()
    }

}
