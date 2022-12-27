package euler

import EulerTask
import utils.Math.square

object Task09SpecialPythagoreanTriplet : EulerTask {

    override val name: String = "Special Pythagorean triplet"

    override fun solution(): Int {
        val sum = 1000

        for (a in 1..sum) {
            for (b in a + 1..sum) {
                val squareA = square(a)
                val squareB = square(b)

                val c = sum - a - b
                val squareC = square(c)

                if (squareC == squareA + squareB) {
                    return a * b * c
                }
            }
        }

        error("solution not found")
    }
}