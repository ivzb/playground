package euler

import EulerTask
import java.math.BigInteger

object Task48SelfPowers : EulerTask("Self powers") {

    override fun solution(): Long {
        var sum = BigInteger.ZERO

        for (n in 1..1000) {
            sum += n.toBigInteger().pow(n)
        }

        return sum.toString().takeLast(10).toLong()
    }

}
