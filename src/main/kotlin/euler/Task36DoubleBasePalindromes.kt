package euler

import EulerTask
import utils.Math.isPalindrome
import utils.Math.toBinary

object Task36DoubleBasePalindromes : EulerTask("Double-base palindromes") {

    override fun solution(): Int {
        var sum = 0

        for (n in 1..1_000_000) {
            if (n.isPalindrome() && n.toBinary().isPalindrome()) {
                sum += n
            }
        }

        return sum
    }

}
