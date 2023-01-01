package euler

import EulerTask
import utils.Math.square

object Task06SumSquareDifference : EulerTask("Sum square difference") {

    override fun solution(): Int {
        val (sumOfSquares, sum) = (1..100)
            .fold(Pair(0, 0)) { (sumOfSquares, sum), n ->
                (sumOfSquares + square(n)) to (sum + n)
            }

        return square(sum) - sumOfSquares
    }

}