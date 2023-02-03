package euler

import EulerTask
import readInput
import utils.Bounds
import utils.Point

object Task82PathSumThreeWays : EulerTask("Path sum: three ways") {

    private val matrix = parseInput()
    private val targetX = matrix.size - 1
    private val deltas = listOf(Point(1, 0), Point(0, 1), Point(0, -1))
    private val bounds = Bounds(Point(0, 0), Point(matrix.size - 1, matrix.size - 1))

    override fun solution(): Int {
        val q = ArrayDeque<State>()

        for (y in matrix.indices) {
            val p = Point(0, y)
            q.add(State(listOf(p), matrix[p.y][p.x], p))
        }

        var solution = Int.MAX_VALUE

        while (q.isNotEmpty()) {
            val best = HashMap<Point, State>()

            while (q.isNotEmpty()) {
                val current = q.removeFirst()

                for (delta in deltas) {
                    val possible = (current + delta) ?: continue
                    val p = possible.last
                    val currentBest = best[p]

                    if (possible.sum > solution) continue

                    if (currentBest == null || currentBest.sum > possible.sum) {
                        best[p] = possible
                    }

                    if (p.x == targetX && possible.sum < solution) {
                        solution = possible.sum
                    }
                }
            }

            q.addAll(best.values)
        }

        return solution
    }

    private fun parseInput(): List<List<Int>> =
        readInput("euler/82")
            .split('\n')
            .map { it.split(',').map { it.toInt() } }

    private data class State(private val points: List<Point>, val sum: Int, val last: Point) {

        operator fun plus(delta: Point): State? {
            val nextPoint = points.last() + delta

            if (!nextPoint.isWithin(bounds) || points.contains(nextPoint)) return null

            val nextItem = matrix[nextPoint.y][nextPoint.x]
            return State(points + nextPoint, sum + nextItem, nextPoint)
        }

    }

}
