package euler

import EulerTask
import utils.Math.lcm

object Task05SmallestMultiple : EulerTask {

    override val name: String = "Smallest multiple"

    override fun solution(): Int {
        return (1..20).lcm()
    }

}