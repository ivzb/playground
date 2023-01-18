package euler

import EulerTask
import utils.Math.hexagonalNumber
import utils.Math.pentagonalNumber
import utils.Math.triangularNumber

object Task45TriangularPentagonalAndHexagonal : EulerTask("Triangular, pentagonal, and hexagonal") {

    override fun solution(): Long = solution_brute_force()

    private fun solution_brute_force(): Long {
        val triangularNumbers = (40756..60_000L).map { n -> triangularNumber(n) }
        val pentagonNumbers = (0..40_000L).map { n -> pentagonalNumber(n) }.toSet()
        val hexagonalNumbers = (0..30_000L).map { n -> hexagonalNumber(n) }.toSet()

        for (n in triangularNumbers) {
            if (pentagonNumbers.contains(n) && hexagonalNumbers.contains(n)) {
                return n
            }
        }

        return -1
    }

    private fun solution_skip_triangular(): Long {
        var pentagonIndex = 166L
        var pentagon = pentagonalNumber(pentagonIndex)

        var hexagonIndex = 143L
        var hexagon = hexagonalNumber(hexagonIndex)

        while (true) {
            while (pentagon < hexagon) {
                pentagonIndex++
                pentagon = pentagonalNumber(pentagonIndex)
            }

            if (pentagon == hexagon) {
                return hexagon
            }

            hexagonIndex++
            hexagon = hexagonalNumber(hexagonIndex)
        }
    }

}
