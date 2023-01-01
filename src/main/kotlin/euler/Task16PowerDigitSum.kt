package euler

import EulerTask

object Task16PowerDigitSum : EulerTask("Power digit sum") {

    override fun solution(): Int {
        var n = 1.toBigInteger().shiftLeft(1000)
        var sum = 0
        val base = 10.toBigInteger()

        while (n != 0.toBigInteger()) {
            val digit = n % base
            sum += digit.toInt()
            n /= base
        }

        return sum
    }
}