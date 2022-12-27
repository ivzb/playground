package euler

import EulerTask
import utils.Math.findPrimes

object Task07_10001stPrime : EulerTask {

    override val name: String = "10001st prime"

    override fun solution(): Long {
        return findPrimes().take(10_001).last()
    }

}