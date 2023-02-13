package euler

import EulerTask
import utils.Math.isPerfectSquare

object Task86CuboidRoute : EulerTask("Cuboid route") {

    override fun solution(): Int {
        var solutions = 0
        var a = 1

        while (solutions < 1_000_000) {
            for (s in 2..a * 2) {
                if (isPerfectSquare(a * a + s * s)) {
                    solutions += if (s > a + 1) {
                        a + 1 - (s + 1) / 2
                    } else {
                        s / 2
                    }
                }
            }

            a++
        }

        return a - 1
    }

}
