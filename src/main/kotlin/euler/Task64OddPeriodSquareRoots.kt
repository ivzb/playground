package euler

import EulerTask
import utils.Math.continuedFractionSqrt

object Task64OddPeriodSquareRoots : EulerTask("Odd period square roots") {

    override fun solution(): Int {
        var counter = 0

        for (n in 2..10_000) {
            val continuedFraction = continuedFractionSqrt(n)
            val period = continuedFraction.size

            if (period % 2 != 0) {
                counter++
            }
        }

        return counter
    }

}
