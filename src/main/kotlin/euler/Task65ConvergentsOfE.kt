package euler

import EulerTask
import utils.Fraction
import utils.Math.digits

object Task65ConvergentsOfE : EulerTask("Convergents of e") {

    override fun solution(): Int {
        var fraction = Fraction(0.toBigInteger(), 1.toBigInteger())

        for (convergent in 100 downTo 2) {
            val term = nthTerm(convergent).toBigInteger()
            fraction = Fraction(fraction.denominator, fraction.numerator + (term * fraction.denominator))
        }

        val term = nthTerm(1).toBigInteger()
        fraction = Fraction(fraction.numerator + (term * fraction.denominator), fraction.denominator)

        return fraction.numerator.digits().sumOf { it }.toInt()
    }

    private fun nthTerm(convergent: Int): Int =
        when {
            convergent == 1 -> 2
            convergent % 3 == 0 -> ((convergent + 1) / 3) * 2
            else -> 1
        }

}
