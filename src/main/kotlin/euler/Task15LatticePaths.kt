package euler

import EulerTask
import utils.Bounds
import utils.Direction
import utils.Math.binom
import utils.Math.factorial
import utils.Point
import java.lang.StringBuilder
import java.text.DecimalFormat

object Task15LatticePaths : EulerTask("Lattice paths") {

    override fun solution(): Long {
        val n = 20
        val numberOfPaths = factorial(2 * n) / (factorial(n) * factorial(n))

        return numberOfPaths.toLong()
    }

    fun binom_solution(): Long {
        return binom(40, 20)
    }

    fun pascal_solution(): Long {
        val n = 20 // must be 1 more than the grid we're evaluating with this approach
        val arr = Array(n) { LongArray(n) }

        // filling the array with pascal's triagle values
        for (i in 0 until n) {
            // outer values set to 1(must be ommited for the solution)
            arr[0][i] = 1
            arr[i][0] = 1
        }

        for (c in 1 until n) {
            for (i in 1 until n) {
                arr[i][c] = arr[i][c - 1] + arr[i - 1][c];
            }
        }

        return arr[n-1][n-1]
    }

    fun naive_solution(): Long {
        // takes 2.25 hours to count all paths
        val directions = listOf(Direction.EAST, Direction.SOUTH)

        val start = Point(0, 0)
        val end = Point(20, 20)
        val bounds = Bounds(start, end)

        val graph = HashMap<Point, List<Point>>()

        (bounds.min.x..bounds.max.x).forEach { x ->
            (bounds.min.y..bounds.max.y).forEach { y ->
                val p = Point(x, y)

                val adjacent = directions
                    .map { it.delta + p }
                    .filter { possiblePosition -> possiblePosition.isWithin(bounds) }

                graph[p] = adjacent
            }
        }

        var solutions = 0L
        var counter = 0

        val q = ArrayDeque<Point>()
        q.add(start)

        while (q.isNotEmpty()) {
            counter++

            if (counter % 100_000_000 == 0) {
                val fmt = DecimalFormat("###,###,###,###,###").format(solutions)
                println("q size = ${q.size}, counter=$counter, solutions=$fmt")
//                println(print(bounds, map))
            }

            val current = q.removeLast()

            if (current == end) {
                // todo: inspect the solution path
                solutions++
                continue
            }

            // todo: find all paths to end, cache and reuse
            val newPaths = graph[current]!!

            q.addAll(newPaths)
        }

        return solutions
    }

    fun print(bounds: Bounds, visits: List<Point>): String {
        val sb = StringBuilder()

        (bounds.min.x..bounds.max.x).forEach { x ->
            (bounds.min.y..bounds.max.y).forEach { y ->
                val p = Point(x, y)
                if (visits.contains(p)) {
                    sb.append('#')
                } else {
                    sb.append(".")
                }
            }
            sb.appendLine()
        }
        sb.appendLine()

        return sb.toString()
    }

}