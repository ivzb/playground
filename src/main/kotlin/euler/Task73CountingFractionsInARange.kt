package euler

import EulerTask
import utils.Fraction
import utils.Math.gcd

object Task73CountingFractionsInARange : EulerTask("Counting fractions in a range") {

    override fun solution(): Int {
        var counter = 0

        for (n in 2..12_000L) {
            for (d in n / 3 + 1 .. (n - 1) / 2) {
                if (gcd(n, d) == 1L) {
                    counter++
                }
            }
        }

        return counter
    }

}
