package euler

import EulerTask
import utils.Fraction
import utils.Math.digits

object Task33DigitCancellingFractions : EulerTask("Digit cancelling fractions") {

    override fun solution(): Long {
        var product = Fraction(1, 1)

        for (numerator in 10..99L) {
            for (denominator in numerator..99L) {
                val fraction = Fraction(numerator, denominator)

                if (fraction.decimal >= 1) {
                    continue
                }

                if (anomalousCancellation(fraction)) {
                    product *= fraction
                }
            }
        }

        return product.simplified.denominator
    }

    private fun anomalousCancellation(fraction: Fraction): Boolean {
        val (a1, a2) = fraction.numerator.digits()
        val (b1, b2) = fraction.denominator.digits()

        if (a2 != b1) {
            return false
        }

        val accidentalFraction = Fraction(a1, b2)

        return fraction.decimal == accidentalFraction.decimal
    }
}
