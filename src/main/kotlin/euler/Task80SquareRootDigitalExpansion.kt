package euler

import EulerTask
import utils.Math.isPerfectSquare
import java.math.MathContext

object Task80SquareRootDigitalExpansion : EulerTask("Square root digital expansion") {

    override fun solution(): Int {
        var sum = 0
        val mc = MathContext(102)

        for (n in 1..100) {
            if (!isPerfectSquare(n)) {
                val sqrt = n.toBigDecimal().sqrt(mc)
                val digits = sqrt.toString().replace(".", "").take(100).map { it.digitToInt() }
                sum += digits.sum()
            }
        }

        return sum
    }

}
