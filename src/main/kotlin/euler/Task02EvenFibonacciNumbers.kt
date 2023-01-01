package euler

import EulerTask
import utils.Math.fibonacci

object Task02EvenFibonacciNumbers : EulerTask("Even Fibonacci numbers") {

    override fun solution(): Int =
        fibonacci()
            .map { it.toInt() }
            .takeWhile { it < 4_000_000 }
            .filter { it % 2 == 0 }
            .sum()
}