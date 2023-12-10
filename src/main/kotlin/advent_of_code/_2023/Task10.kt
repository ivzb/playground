package advent_of_code._2023

import Task
import advent_of_code._2022.Task17
import readInput
import utils.Bounds.Companion.bounds
import utils.Bounds.Companion.rebound
import utils.Direction
import utils.Point
import java.lang.Math.max
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap

object Task10 : Task {

    private val PIPES = mapOf(
        '|' to (Direction.NORTH to Direction.SOUTH), // vertical pipe connecting north and south.
        '-' to (Direction.EAST to Direction.WEST),   // horizontal pipe connecting east and west.
        'L' to (Direction.NORTH to Direction.EAST),  // 90-degree bend connecting north and east.
        'J' to (Direction.NORTH to Direction.WEST),  // 90-degree bend connecting north and west.
        '7' to (Direction.SOUTH to Direction.WEST),  // 90-degree bend connecting south and west.
        'F' to (Direction.SOUTH to Direction.EAST)  // 90-degree bend connecting south and east.
    )

    private const val START = 'S'
    private const val GROUND = '.'

    data class Pipe(val point: Point, val char: Char, val from: Point, val to: Point) {
        var step = 0
    }

    override fun partA(): Int {
        val map = HashMap<Point, Pipe>()
        lateinit var startPoint: Point

        parseInput()
            .mapIndexed { rowIndex, row ->
                row.mapIndexed { colIndex, char ->
                    val point = Point(colIndex, rowIndex)

                    if (PIPES.containsKey(char)) {
                        val (fromDirection, toDirection) = PIPES.getValue(char)
                        val from = point + fromDirection.delta
                        val to = point + toDirection.delta
                        map[point] = Pipe(point, char, from, to)
                    }

                    if (char == START) {
                        startPoint = point
                    }
                }
            }

        val startPipeJoints = map
            .filter { (_, pipe) -> pipe.from == startPoint || pipe.to == startPoint}
            .map { (point, _) ->
                point
            }

        val (from, to) = startPipeJoints
        val start = Pipe(startPoint, START, from, to)

        val loop = HashMap<Point, Pipe>()
        val processed = HashSet<Pipe>()
        val unprocessed = LinkedList<Pipe>()
        unprocessed.add(start)

        var maxStep = 0

        while (unprocessed.isNotEmpty()) {
            val pipe = unprocessed.pop()

            if (processed.contains(pipe)) {
                continue
            }

            processed.add(pipe)
            loop[pipe.point] = pipe

            maxStep = max(maxStep, pipe.step)

            if (!loop.containsKey(pipe.from)) {
                unprocessed.add(map.getValue(pipe.from).also {
                    it.step = pipe.step + 1
                })
            }

            if (!loop.containsKey(pipe.to)) {
                unprocessed.add(map.getValue(pipe.to).also {
                    it.step = pipe.step + 1
                })
            }
        }

        return maxStep
    }

    private fun draw(map: Map<Point, Pipe>, start: Pipe): String {
        val bounds = map.keys.bounds()
        val sb = StringBuilder()

        (bounds.min.y..bounds.max.y).forEach { y ->
            (bounds.min.x..bounds.max.x).forEach { x ->
                val point = Point(x, y)

                if (map.containsKey(point)) {
                    sb.append('*')
                } else if (start.point == point) {
                    sb.append(START)
                } else {
                    sb.append(GROUND)
                }
            }

            sb.append('\n')
        }

        return sb.toString()
    }

    override fun partB(): Int = -1

    private fun parseInput() = readInput("_2023/10")
        .split("\n")
        .map { it.toCharArray() }

}