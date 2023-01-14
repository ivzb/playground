package euler

import EulerTask
import utils.Math.isPalindrome
import utils.Math.reversed

object Task55LychrelNumbers : EulerTask("Lychrel numbers") {

    override fun solution(): Int {
        var count = 0

        for (n in 0 until 10_000) {
            if (isLychrelNumber(n)) {
                count++
            }
        }

        return count
    }

    private fun isLychrelNumber(number: Int): Boolean {
        var n = number.toBigInteger()

        for (j in 0 until 50) {
            n += n.reversed()

            if (n.isPalindrome()) {
                return false
            }
        }

        return true
    }

}
