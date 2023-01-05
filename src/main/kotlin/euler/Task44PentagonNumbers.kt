package euler

import EulerTask

object Task44PentagonNumbers : EulerTask("Pentagon numbers") {

    override fun solution(): Int {
        val pentagonNumbers = (1..3_000).map { n -> pentagonNumber(n) }.toSet()

        for (j in pentagonNumbers) {
            for (k in pentagonNumbers) {
                if (pentagonNumbers.contains(j + k) && pentagonNumbers.contains(k - j)) {
                    return k - j
                }
            }
        }

        return -1
    }

    private fun pentagonNumber(n: Int): Int {
        return n * (3 * n - 1) / 2
    }

}
