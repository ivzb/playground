package euler

import EulerTask
import utils.Math.lcm

object Task05SmallestMultiple : EulerTask("Smallest multiple") {

    override fun solution(): Int {
        return (1..20).lcm()
    }

}