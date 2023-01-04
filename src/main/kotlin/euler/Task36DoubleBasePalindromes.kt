package euler

import EulerTask
import utils.Math.isPalindrome

object Task36DoubleBasePalindromes : EulerTask("Double-base palindromes") {

    override fun solution(): Int {
        var sum = 0

        for (n in 1..1_000_000 step 2) {
            if (n.isPalindrome(base = 10) && n.isPalindrome(base = 2)) {
                sum += n
            }
        }

        return sum
    }

}
