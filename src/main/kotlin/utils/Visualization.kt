package utils

import _2022.Task14
import utils.Bounds.Companion.toBounds
import java.lang.StringBuilder

object Visualization {

    fun points(points: Collection<Point>, textValue: (Point) -> String): String =
        StringBuilder().apply {
            val caveBounds = points.toBounds()

            (caveBounds.min.y..caveBounds.max.y).forEach { y ->
                (caveBounds.min.x..caveBounds.max.x).forEach { x ->
                    append(textValue(Point(x, y)))
                }

                append(" y = $y")
                appendLine()
            }
        }.toString()

    fun String.print() = println(this)
}