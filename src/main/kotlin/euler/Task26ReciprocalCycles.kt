package euler

import EulerTask
import utils.Math.patternInFraction
import java.lang.Math.max

object Task26ReciprocalCycles : EulerTask {

    override val name: String = "Reciprocal cycles"

    override fun solution(): Int = solution_brute_force()

    fun solution_brute_force(): Int {
        var longest = 0
        var n = 0

        for (i in 1..1000) {
            val cycle = patternInFraction(1, i)

            if (cycle.size > longest) {
                longest = cycle.size
                n = i
            }
        }

        return n
    }

    // For a given number d, the length of the repeating decimals of 1/d can be calculated
    // by finding the smallest number that consists of just nines (999999...) that is evenly divisible by d.
    // The value 10^n-1 is 9999... with n repeating nines.
    // You can find out if this number is divisible by d by calculating the value 10^n-1 modulus d.
    // If it is 0, then you know there is no remainder to the division.
    // You can simplify the value slightly by instead calculating 10^n mod d and if the result is 1,
    // then you know that d divides evenly.
    fun solution_repeating_nines(): Int {
        val ten = 10.toBigInteger()
        val one = 1.toBigInteger()

        var longest = 0

        for (d in 1..1000) {
            for (n in 1..1000) {
                if (ten.pow(n).mod(d.toBigInteger()) == one) {
                    longest = max(longest, n)
                    break
                }
            }
        }

        return longest + 1
    }

}
