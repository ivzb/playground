package euler

import EulerTask
import java.math.BigInteger

object Task29DistinctPowers : EulerTask("Distinct powers") {

    override fun solution(): Int {
        val terms = HashSet<BigInteger>()
        val n = 100

        for (a in 2..n) {
            for (b in 2..n) {
                terms.add(a.toBigInteger().pow(b))
            }
        }

        return terms.size
    }

}
