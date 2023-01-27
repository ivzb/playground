package euler

import EulerTask
import utils.Fraction
import utils.Math.gcd

object Task71OrderedFractions : EulerTask("Ordered fractions") {

    override fun solution(): Int {
        val target = 3.0 / 7

        var bestFraction = Fraction(2, 5)
        var best = bestFraction.decimal

        for (n in 2..1_000_000L) {
            for (d in bestFraction.denominator.toLong()..1_000_000L) {
                val current = n.toDouble() / d

                if (current < best) {
                    break
                }

                if (current > best && current < target && gcd(n, d) == 1L) {
                    bestFraction = Fraction(n, d)
                    best = current
                }
            }
        }

        return bestFraction.numerator.toInt()
    }

}
