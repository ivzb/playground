package euler

import EulerTask
import kotlin.math.ln

object Task63PowerfulDigitCounts : EulerTask("Powerful digit counts") {

    override fun solution(): Int {
        return solution_brute_force()
    }

    private fun solution_brute_force(): Int {
        var counter = 0

        for (i in 1 .. 9) {
            val n = i.toBigInteger()

            for (exp in 1..25) {
                val power = n.pow(exp)
                val size = power.toString().length

                if (size == exp) {
                    counter++
                }

                if (size > exp) {
                    break
                }
            }
        }

        return counter
    }

    private fun solution_math_formula(): Int {
        var counter = 0

        for (n in 1..9) {
            val r = (ln(10.0) / (ln(10.0) - ln(n.toDouble()))).toInt()
            counter += r
        }

        return counter
    }

}
