package euler

import EulerTask
import utils.Math.isPerfectSquare
import utils.Math.pellsEquation
import java.math.BigInteger

object Task66DiophantineEquation : EulerTask("Diophantine equation") {

    override fun solution(): Int = solution_fractions()

    private fun solution_fractions(): Int {
        var bestX = BigInteger.ZERO
        var bestD = 0

        for (d in 2 .. 1_000) {
            if (isPerfectSquare(d)) {
                continue
            }

            val fundamentalSolution = pellsEquation(d)
            val x = fundamentalSolution.numerator

            if (x > bestX) {
                bestX = x
                bestD = d
            }
        }

        return bestD
    }

    private fun solution_binary_search_fractions(): Int {
        var bestX = BigInteger.ZERO
        var bestD = BigInteger.ZERO

        for (i in 2..1000) {
            val n = i.toBigInteger()

            var n1 = 0.toBigInteger()
            var d1 = 1.toBigInteger()
            var n2 = 1.toBigInteger()
            var d2 = 0.toBigInteger()

            while (true) {
                // these are the two bounding fractions
                val a = n1 + n2
                val b = d1 + d2

                // a/b is the new candidate somewhere in the middle
                val t = a * a - n * b * b;  // see how close a^2/b^2 is to n

                if (t == 1.toBigInteger()) { // you have your pell solution (a,b)
                    if (a > bestX) {
                        bestX = a
                        bestD = n
                    }

                    break
                } else if (t == 0.toBigInteger()) { // problem, n was a square = (a/b)^2
                    break
                } else { // not there yet - adjust low or hi bound
                    if (t > 0.toBigInteger()) {
                        n2 = a
                        d2 = b
                    } else {
                        n1 = a
                        d1 = b
                    }
                }
            }
        }

        return bestD.toInt()
    }

}
