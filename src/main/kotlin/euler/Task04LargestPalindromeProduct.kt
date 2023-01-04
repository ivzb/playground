package euler

import EulerTask
import utils.Math.isPalindrome

object Task04LargestPalindromeProduct : EulerTask("Largest palindrome product") {

    override fun solution(): Int {
        var bestPalindrome = 0

        val range = 100..999

        for (a in range) {
            for (b in range) {
                val product = a * b

                if (product.isPalindrome() && product > bestPalindrome) {
                    bestPalindrome = product
                }
            }
        }

        return bestPalindrome
    }

}