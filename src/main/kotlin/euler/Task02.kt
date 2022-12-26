package euler

import EulerTask
import utils.Math.fibonacci

object Task02 : EulerTask {

    override val name: String = "Even Fibonacci numbers"

    override fun solution(): Int =
        fibonacci()
            .takeWhile { it < 4_000_000 }
            .filter { it % 2 == 0 }
            .sum()
}