package euler

import EulerTask
import utils.Fraction

object Task57SquareRootConvergents : EulerTask("Square root convergents") {

    override fun solution(): Int {
        var previousFraction = Fraction(1, 2)
        var count = 0

        repeat(1000) {
            val a = Fraction(1, 1)
            val b = Fraction(1, Fraction(2, 1) + previousFraction)
            val r = a + b

            if (r.numerator.toString().length > b.denominator.toString().length) {
                count++
            }

            previousFraction = b
        }

        return count
    }

}
