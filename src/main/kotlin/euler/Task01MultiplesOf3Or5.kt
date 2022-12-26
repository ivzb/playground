package euler

import EulerTask

object Task01MultiplesOf3Or5 : EulerTask {

    override val name: String = "Multiples of 3 or 5"

    override fun solution(): Int =
        (1 until 1000)
            .filter { n -> n % 3 == 0 || n % 5 == 0 }
            .sum()
}