package _2022

import Task
import readInput
import utils.Direction
import utils.Point
import utils.Point.Companion.toPoint
import java.lang.StringBuilder
import kotlin.math.max

object Task14 : Task {

    private const val ROCK = '#'
    private const val SAND = 'o'

    val SAND_SOURCE = Point(500, 0)

    var cave1 = HashMap<Point, Char>().also {
        it[SAND_SOURCE] = '+'
    }
    var cave2 = HashMap<Point, Char>().also {
        it[SAND_SOURCE] = '+'
    }

    override fun partA() = parseInput()
        .map {
            it.zipWithNext { a, b ->
                val minX = Math.min(a.x, b.x)
                val maxX = Math.max(a.x, b.x)
                val minY = Math.min(a.y, b.y)
                val maxY = Math.max(a.y, b.y)

                for (x in minX..maxX) {

                    for (y in minY..maxY) {
                        val point = Point(x, y)
                        cave1[point] = ROCK
                    }
                }
            }
        }
        .let {
            var isResting = true
            lateinit var fallingSandPosition: Point
            val fallingDirections = listOf(Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT)

            var unitsOfSand = 0

            // todo
            while (true) {
//            while (unitsOfSand < 25) {
                if (isResting) {
                    isResting = false
                    fallingSandPosition = SAND_SOURCE + Direction.UP.delta
                    cave1[fallingSandPosition] = SAND
                } else {
                    val direction = fallingDirections.firstOrNull { direction ->
                        val position = fallingSandPosition + direction.delta
                        !cave1.containsKey(position)
                    }

                    if (direction != null) {
//                        println("prev=$fallingSandPosition, new=${fallingDirections + direction.delta}")
                        cave1.remove(fallingSandPosition)
                        fallingSandPosition += direction.delta
                        val (min, max) = bounds(cave1)

                        if (fallingSandPosition.x >= min.x && fallingSandPosition.x <= max.x &&
                            fallingSandPosition.y >= min.y && fallingSandPosition.y <= max.y
                        ) {
                            cave1[fallingSandPosition] = SAND
                        } else {
//                            println("fell in the void")
                            break
                        }
                    } else {
                        isResting = true
                        unitsOfSand++
                    }
                }
            }

            val print = print(cave1)
            println(print)
            println(unitsOfSand)

            unitsOfSand
        }

    override fun partB() = parseInput()
        .map {
            it.zipWithNext { a, b ->
                val minX = Math.min(a.x, b.x)
                val maxX = Math.max(a.x, b.x)
                val minY = Math.min(a.y, b.y)
                val maxY = Math.max(a.y, b.y)

                for (x in minX..maxX) {

                    for (y in minY..maxY) {
                        val point = Point(x, y)
                        cave2[point] = ROCK
                    }
                }
            }
        }
        .let {
            var isResting = true
            lateinit var fallingSandPosition: Point
            val fallingDirections = listOf(Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT)

            var unitsOfSand = 0
            var i = 0

            val (caveMin, caveMax) = bounds(cave2)
            val floorY = caveMax.y + 2

            // todo
            while (true) {
//            while (unitsOfSand < 25) {
                if (isResting) {
                    isResting = false
                    fallingSandPosition = SAND_SOURCE //+ Direction.UP.delta
                    cave2[fallingSandPosition] = SAND
                } else {
                    val direction = fallingDirections.firstOrNull { direction ->
                        val position = fallingSandPosition + direction.delta
                        !cave2.containsKey(position) && position.y < floorY
                    }

                    if (direction != null) {
//                        println("prev=$fallingSandPosition, new=${fallingDirections + direction.delta}")
                        cave2.remove(fallingSandPosition)
                        fallingSandPosition += direction.delta

                            cave2[fallingSandPosition] = SAND
//                            val print = print(cave)
//                            println(print)

                    } else {
//                        println("else prev=$fallingSandPosition")
                        isResting = true
                        unitsOfSand++
                        if (fallingSandPosition == Point(500, 0)) {
                            break
                        }
                    }
                }
            }

            val print = print(cave2)
            println(print)
            println(unitsOfSand)

            unitsOfSand
        }

    private fun parseInput() = readInput("_2022/14")
        .split("\n")
        .map { it.split(" -> ").map { it.toPoint() } }

    private fun bounds(cave: Map<Point, Char>): Pair<Point, Point> {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE

        cave.forEach { (point) ->
            minX = Math.min(minX, point.x)
            maxX = Math.max(maxX, point.x)
            minY = Math.min(minY, point.y)
            maxY = Math.max(maxY, point.y)
        }

        return Point(minX, minY) to Point(maxX, maxY)
    }

//    private fun floor(cave: Map<Point, Char>) {
//        val (caveMin, caveMax) = bounds(cave)
//
//        (caveMin.x .. caveMax.x).forEach { x ->
//            val position = Point(x, caveMax.y + 2)
//            cave[position] = ROCK
//        }
//    }

    private fun floor() {

    }

    private fun print(cave: Map<Point, Char>): String {
        var (min1, max1) = bounds(cave)

        val tmp = HashMap(cave)

        (min1.x..max1.x).forEach { x ->
            val point = Point(x, max1.y + 2)
            tmp[point] = ROCK
        }
//        val floor = Point(fallingSandPosition.x, caveMax.y - 2)

        val (min, max) = bounds(tmp)

        val sb = StringBuilder()

        (min.y..max.y).forEach { y ->
            (min.x..max.x).forEach { x ->
                val point = Point(x, y)
                val item = tmp[point]

                if (item != null) {
                    sb.append(item)
                } else {
                    sb.append('.')
                }
            }
            sb.appendLine()
        }
        sb.appendLine()

        return sb.toString()
    }
}