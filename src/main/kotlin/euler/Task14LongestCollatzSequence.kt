package euler

import EulerTask

object Task14LongestCollatzSequence : EulerTask("Longest Collatz sequence") {

    override fun solution(): Long {
        var max = 0L
        var n = 0L

        for (i in 1..1_000_000L) {
            val current = collatzSequence(i)

            if (current > max) {
                max = current
                n = i
            }
        }

        return n
    }

    private fun collatzSequence(start: Long): Long {
        var n = start
        var length = 1L

        while (n != 1L) {
            if (n % 2 == 0L) {
                n /= 2
            } else {
                n = (3 * n) + 1
            }

            length++
        }

        return length
    }

}