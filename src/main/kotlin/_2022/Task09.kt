package _2022

import Task
import readInput
import utils.Matrix.areAdjacent
import utils.Matrix.Direction
import utils.Point
import java.lang.Exception

typealias Tail = ArrayDeque<Point>

object Task09 : Task {

    override fun partA() = simulateRopePhysics(knots = 1).visited.size

    override fun partB() = simulateRopePhysics(knots = 9).visited.size

    private fun simulateRopePhysics(knots: Int) = parseInput()
        .fold(Rope(knots)) { rope, (direction, steps) ->
            rope.apply {
                repeat(steps) {
                    head += direction.toDirection().delta
                    tail = moveTail(this)
                    visited += tail.last()
                }
            }
        }

    private fun parseInput() = readInput("_2022/09")
        .split("\n")
        .map { it.split(" ").let { (direction, steps) -> direction to steps.toInt() } }

    private fun moveTail(rope: Rope): Tail = rope.tail
        .fold(Tail(rope.tail.size)) { tail, knot ->
            val head = tail.lastOrNull() ?: rope.head
            tail += motion(head, knot)
            tail
        }

    private fun motion(head: Point, tail: Point): Point = when {
        areAdjacent(head, tail) -> tail
        else -> tail + (head - tail).coerceIn(-1, 1)
    }

    private data class Rope(var head: Point, var tail: Tail, val visited: HashSet<Point>) {
        constructor(knots: Int) : this(
            head = Point(),
            tail = Tail(List(knots) { Point() }),
            visited = HashSet()
        )
    }

    private fun String.toDirection(): Direction = when (this) {
        "U" -> Direction.UP
        "R" -> Direction.RIGHT
        "D" -> Direction.DOWN
        "L" -> Direction.LEFT
        else -> throw Exception("unknown direction $this")
    }

}