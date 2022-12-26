package euler

import EulerTask

object Task01 : EulerTask {

    override val name: String = "Multiples of 3 or 5"

    override fun solution(): Any {
        var sum = 0

        for (n in 1 until 1000) {
            if (n % 3 == 0 || n % 5 == 0) {
                sum += n
            }
        }

        return sum
    }

}