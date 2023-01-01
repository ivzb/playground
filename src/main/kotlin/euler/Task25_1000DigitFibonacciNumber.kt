package euler

import EulerTask
import utils.Math.fibonacci
import utils.BigInteger.length
import utils.Math.fibonacciIndexOf

object Task25_1000DigitFibonacciNumber : EulerTask("1000-digit Fibonacci number") {

    override fun solution(): Int = solution_brute_force()

    fun solution_brute_force(): Int {
        var n = 1

        while (fibonacci(n).length() < 1_000) {
            n++
        }

        return n + 1
    }

    private fun solution_binet_formula(): Int =
        fibonacciIndexOf(1_000)
}
