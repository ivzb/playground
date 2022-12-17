package utils

import utils.Bounds.Companion.bounds
import java.lang.StringBuilder

object Visualization {

    fun points(points: Collection<Point>, textValue: (Point) -> String): String =
        StringBuilder().apply {
            val caveBounds = points.bounds()

            (caveBounds.min.y..caveBounds.max.y).forEach { y ->
                (caveBounds.min.x..caveBounds.max.x).forEach { x ->
                    append(textValue(Point(x, y)))
                }

                append(" y = $y")
                appendLine()
            }
        }.toString()

    fun String.print() = println(this)

    fun distance(from: Point, to: Point, distance: Int): String {
        val points = ArrayList<Pair<Point, Point>>()

        fun Point.symbol(): Char = when {
            this == from -> '1'
            this == to -> '2'
            else -> '#'
        }

        var str = ""

        for (i in 0 until distance) {
            str += "\n"
            for (j in 0 until distance - i) {
                str += '.'
            }
            for (j in 0 until 2 * i + 1) {
                val up = Point(from.x - distance - (distance - i) + j + 1, from.y - (distance - i))
                str += up.symbol()
            }

            val x = from.x - i
            val y = from.y - (distance - i)
            val upFrom = Point(x, y)

            val j = 2 * i// + 1
            val upTo = Point(x + j, y)

            points.add(upFrom to upTo)
            str += " from ($upFrom) to ($upTo)"
        }

        str += "\n"
        for (i in 0 until 2 * distance + 1) {
//            str += '#'
            val mid = Point(from.x - distance + i, from.y)
            str += mid.symbol()
        }

        val midX = from.x - distance
        val midY = from.y

        val midFrom = Point(midX, midY)

        val xDelta = 2 * distance//+1
        val midTo = Point(midX + xDelta, midY)
        str += " from ($midFrom) to ($midTo)"

        points.add(midFrom to midTo)

        for (i in 0 until distance) {
            str += '\n'
            for (j in 0 until i + 1) {
                str += '.'
            }
            for (j in 0 until 2 * (distance - i) - 1) {
                val down = Point(from.x - distance + i + 1 + j, from.y + i + 1)
                str += down.symbol()
            }

            val downX = from.x - distance + i + 1
            val downY = from.y + i + 1

            val downFrom = Point(downX, downY)

            val xDelta = 2 * (distance - i) - 2
            val downTo = Point(downX + xDelta, downY)

            points.add(downFrom to downTo)
            str += " from ($downFrom) to ($downTo)"
        }

        return str
    }
}