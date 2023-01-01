package euler

import EulerTask
import readInput

object Task22NamesScores : EulerTask("Names scores") {

    override fun solution(): Int =
        parseInput()
            .sorted()
            .mapIndexed { index, name -> name.sumOf { it - 'A' + 1 } * (index + 1) }
            .sum()

    private fun parseInput(): List<String> =
        readInput("euler/22").replace("\"", "").split(",")

}
