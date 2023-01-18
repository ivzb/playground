package euler

import EulerTask
import utils.Math.pentagonalNumber

object Task44PentagonNumbers : EulerTask("Pentagon numbers") {

    override fun solution(): Long {
        val pentagonNumbers = (1..3_000L).map { n -> pentagonalNumber(n) }.toSet()

        for (j in pentagonNumbers) {
            for (k in pentagonNumbers) {
                if (pentagonNumbers.contains(j + k) && pentagonNumbers.contains(k - j)) {
                    return k - j
                }
            }
        }

        return -1
    }

}
