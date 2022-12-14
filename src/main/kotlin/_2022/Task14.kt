package _2022

import Task
import readInput
import utils.Bounds.Companion.toBounds
import utils.Direction
import utils.Point
import utils.Point.Companion.toPoint
import utils.Visualization
import utils.Visualization.print
import java.lang.StringBuilder
import kotlin.math.max
import kotlin.math.min

typealias Cave = HashMap<Point, Task14.Material>

object Task14 : Task {

    private val SAND_SOURCE_POSITION = Point(500, 0)
    private val FALLING_SAND_DIRECTIONS = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))

    override fun partA() = buildCave()
        .let { cave ->
            var fallingSand: Point? = null
            val caveBounds = cave.keys.toBounds()

            while (true) {
                if (fallingSand == null) {
                    fallingSand = SAND_SOURCE_POSITION
                }

                val direction = FALLING_SAND_DIRECTIONS.find { direction ->
                    val position = fallingSand!! + direction
                    !cave.containsKey(position)
                }

                if (direction == null) {
                    // sand landed
                    cave[fallingSand] = Material.Sand
                    fallingSand = null
                    continue
                }

                // sand falling
                fallingSand += direction

                if (!fallingSand.isWithin(caveBounds)) {
                    // "fell into the endless void"
                    break
                }
            }

            cave.count { (_, material) -> material == Material.Sand }
        }

    override fun partB() = buildCave()
        .let { cave ->
            var fallingSand: Point? = null
            val caveBounds = cave.keys.toBounds()
            val floorY = caveBounds.max.y + 2

            while (true) {
                if (fallingSand == null) {
                    fallingSand = SAND_SOURCE_POSITION
                }

                val direction = FALLING_SAND_DIRECTIONS.find { direction ->
                    val position = fallingSand!! + direction
                    !cave.containsKey(position) && position.y < floorY
                }

                if (direction == null) {
                    // sand landed or is filled to the top
                    cave[fallingSand] = Material.Sand

                    if (fallingSand == SAND_SOURCE_POSITION) {
                        break
                    }

                    fallingSand = null
                    continue
                }

                fallingSand += direction
            }

            cave.count { (_, material) -> material == Material.Sand }
        }

    private fun parseInput() = readInput("_2022/14")
        .split("\n")
        .map { it.split(" -> ").map { it.toPoint() } }

    private fun buildCave(): Cave {
        val cave = Cave()
        cave[SAND_SOURCE_POSITION] = Material.SandSource

        parseInput().map {
            it.zipWithNext { a, b ->
                for (x in min(a.x, b.x)..max(a.x, b.x)) {
                    for (y in min(a.y, b.y)..max(a.y, b.y)) {
                        val point = Point(x, y)
                        cave[point] = Material.Rock
                    }
                }
            }
        }

        return cave
    }

    enum class Material(val value: String) {
        Air("."),
        Rock("#"),
        Sand("o"),
        SandSource("+"),
    }
}