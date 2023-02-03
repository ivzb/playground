package euler

import EulerTask
import readInput
import utils.Bounds
import utils.Point

object Task81PathSumTwoWays : EulerTask("Path sum: two ways") {

    private val matrix = parseInput()
    private val start = Point(0, 0)
    private val target = Point(matrix.size - 1, matrix.size - 1)
    private val deltas = listOf(Point(1, 0), Point(0, 1))
    private val bounds = Bounds(start, target)

    override fun solution(): Int {
        val q = ArrayDeque<State>()
        q.add(State(listOf(start), matrix[start.y][start.x]))

        while (true) {
            val best = HashMap<Point, State>()

            while (q.isNotEmpty()) {
                val current = q.removeFirst()

                for (delta in deltas) {
                    val possible = (current + delta) ?: continue
                    val p = possible.points.last()
                    val currentBest = best[p]

                    if (currentBest == null || currentBest.sum > possible.sum) {
                        best[p] = possible
                    }
                }
            }

            q.addAll(best.values)

            if (q.size == 1) {
                return q.removeFirst().sum
            }
        }
    }

    private fun parseInput(): List<List<Int>> =
        readInput("euler/81")
            .split('\n')
            .map { it.split(',').map { it.toInt() } }


    private data class State(val points: List<Point>, val sum: Int) {

        operator fun plus(delta: Point): State? {
            val nextPoint = points.last() + delta

            if (!nextPoint.isWithin(bounds)) return null

            val nextItem = matrix[nextPoint.y][nextPoint.x]
            return State(points + nextPoint, sum + nextItem)
        }

    }

}
