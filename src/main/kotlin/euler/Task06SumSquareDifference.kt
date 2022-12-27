package euler

import EulerTask

object Task06SumSquareDifference : EulerTask {

    override val name: String = "Sum square difference"

    override fun solution(): Int {
        val (sumOfSquares, sum) = (1..100)
            .fold(Pair(0, 0)) { (sumOfSquares, sum), n ->
                (sumOfSquares + square(n)) to (sum + n)
            }

        return square(sum) - sumOfSquares
    }

    private fun square(n: Int): Int = n * n

}