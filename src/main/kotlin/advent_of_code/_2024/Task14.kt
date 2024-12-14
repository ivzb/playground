package advent_of_code._2024

import Task
import readInput
import utils.Bounds
import utils.Point

object Task14 : Task {

    private const val MAX_X = 101
    private const val MAX_Y = 103
    private val MAX = Point(MAX_X, MAX_Y)

    override fun partA() = parseInput()
        .let { robots ->
            repeat(100) {
                robots.forEach { it.move() }
            }

            val midX = MAX.x / 2
            val midY = MAX.y / 2

            listOf(
                Point(0, 0) to Point(midX - 1, midY - 1),
                Point(0, midY + 1) to Point(midX - 1, MAX.y),
                Point(midX + 1, 0) to Point(MAX.x, midY - 1),
                Point(midX + 1, midY + 1) to Point(MAX.x, MAX.y)
            )
            .map { (x, y) -> Bounds(x, y) }
            .map { bounds -> robots.filter { it.currentPosition.isWithin(bounds) }.size }
            .fold(1) { acc, n -> acc * n }
        }

    override fun partB() = parseInput()
        .let { robots ->
            repeat(10_000) { second ->
                robots.forEach { it.move() }

                val positions = robots.map { it.currentPosition }
                val uniquePositions = positions.toSet()

                if (positions.size == uniquePositions.size) {
                    return@let second + 1
                }
            }

            -1
        }

    data class Robot(val initialPosition: Point, val velocity: Point) {

        var currentPosition = initialPosition

        fun move() {
            val x = (currentPosition.x + velocity.x + MAX.x) % (MAX.x)
            val y = (currentPosition.y + velocity.y + MAX.y) % (MAX.y)
            currentPosition = Point(x, y)
        }
    }

    private fun parseInput() = readInput("_2024/14")
        .lines()
        .map {
            val split = it.split("=", " ", ",")
            val px = split[1].toInt()
            val py = split[2].toInt()
            val vx = split[4].toInt()
            val vy = split[5].toInt()

            Robot(initialPosition = Point(px, py), velocity = Point(vx, vy))
        }

}