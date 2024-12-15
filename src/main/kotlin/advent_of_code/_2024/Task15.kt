package advent_of_code._2024

import Task
import advent_of_code._2021.Task02
import advent_of_code._2022.Task22
import readInput
import utils.Bounds
import utils.Bounds.Companion.bounds
import utils.Point
import java.lang.StringBuilder
import java.util.LinkedList

object Task15 : Task {

    private val DIRECTION_UP = Point(0, -1)
    private val DIRECTION_RIGHT = Point(1, 0)
    private val DIRECTION_DOWN = Point(0, 1)
    private val DIRECTION_LEFT = Point(-1, 0)

    private val DIRECTION_UP_LEFT = Point(-1, -1)
    private val DIRECTION_UP_RIGHT = Point(1, -1)

    private val DIRECTION_DOWN_LEFT = Point(-1, 1)
    private val DIRECTION_DOWN_RIGHT = Point(1, 1)

    private val DIRECTIONS = mapOf(
        '>' to DIRECTION_RIGHT,
        'v' to DIRECTION_DOWN,
        '<' to DIRECTION_LEFT,
        '^' to DIRECTION_UP,
    )

    private val NEIGHBOURS = mapOf(
        ('[' to DIRECTION_UP) to DIRECTION_UP_RIGHT,
        ('[' to DIRECTION_DOWN) to DIRECTION_DOWN_RIGHT,
        (']' to DIRECTION_UP) to DIRECTION_UP_LEFT,
        (']' to DIRECTION_DOWN) to DIRECTION_DOWN_LEFT,
    )

    override fun partA() = parseInput(isWide = false)
        .let { (map, moves) ->
            var robotPosition = map.filterValues { char -> char == '@' }.keys.first()

            for (move in moves) {
                val q = LinkedList<Point>()
                val direction = DIRECTIONS[move] ?: throw Exception("unknown move $move")
                var currentPosition = robotPosition

                q.add(currentPosition)

                while (true) {
                    val nextPosition = currentPosition + direction
                    val next = map[nextPosition]
                    q.add(nextPosition)

                    when (next) {
                        '#' -> {
                            // hit a wall - can't move anything
                            q.clear()
                            break
                        }

                        '.' -> {
                            // found a gap -> can move everything
                            break
                        }

                        'O' -> {
                            // found a box -> try to push it
                        }

                        else -> {
                            throw Exception("unknown next $next")
                        }
                    }

                    currentPosition = nextPosition
                }

                q.reversed().zipWithNext().forEach { (next, current) ->
                    val temp = map[next] ?: throw Exception("undefined map position $next")
                    map[next] = map[current] ?: throw Exception("undefined map position $current")
                    map[current] = temp
                }

                if (q.isNotEmpty()) {
                    robotPosition += direction
                }
            }

            map.toList().filter { (_, char) -> char == 'O' }.sumOf { (position) -> position.y * 100 + position.x }
        }

    override fun partB() = parseInput(isWide = true)
        .let { (map, moves) ->
            var robotPosition = map.filterValues { char -> char == '@' }.keys.first()

            for (move in moves) {
                val currentPosition = robotPosition
                val direction = DIRECTIONS[move] ?: throw Exception("unknown move $move")

                val affected = mutableSetOf<Pair<Point, Point>>()
                val canMove = findAllBlocking(map, currentPosition, direction, affected)

                if (canMove) {
                    // remove old robot
                    map[robotPosition] = '.'

                    // remove old boxes
                    affected.forEach { (p1, p2) ->
                        map[p1] = '.'
                        map[p2] = '.'
                    }

                    // move robot
                    robotPosition += direction
                    map[robotPosition] = '@'

                    // move boxes
                    affected.forEach { (p1, p2) ->
                        map[p1 + direction] = '['
                        map[p2 + direction] = ']'
                    }
                }
            }

            map.toList().filter { (_, char) -> char == '[' }.sumOf { (position) -> position.y * 100 + position.x }
        }


    private fun findPair(map: Map<Point, Char>, position: Point): Pair<Point, Point> {
        return when (val item = map[position]) {
            '[' -> Pair(position, position + Point(1, 0))
            ']' -> Pair(position + Point(-1, 0), position)
            else -> throw Exception("unsupported item $item")
        }
    }

    private fun findAllBlocking(
        map: Map<Point, Char>,
        currentPosition: Point,
        direction: Point,
        affected: MutableSet<Pair<Point, Point>>
    ): Boolean {
        val nextPosition = currentPosition + direction

        when (val next = map[nextPosition]) {
            '#' -> {
                // found a wall -> cannot move
                return false
            }

            '.' -> {
                // found a gap -> can move
                return true
            }

            else -> {
                // found a box -> add it to affected and along with its neighbours
                affected.add(findPair(map, nextPosition))

                val neighbour = NEIGHBOURS[next to direction]

                val others = if (neighbour != null) {
                    affected.add(findPair(map, currentPosition + neighbour))
                    findAllBlocking(map, currentPosition + neighbour, direction, affected)
                } else {
                    true
                }

                return findAllBlocking(map, nextPosition, direction, affected) && others
            }
        }
    }

    private fun parseInput(isWide: Boolean) = readInput("_2024/15")
        .split("\n\n")
        .let { (board, moves) ->
            val map = HashMap<Point, Char>()

            board
                .let {
                    if (isWide) {
                        it
                            .replace("#", "##")
                            .replace("O", "[]")
                            .replace(".", "..")
                            .replace("@", "@.")
                    } else {
                        it
                    }
                }
                .lines().forEachIndexed { row, line ->
                    line.forEachIndexed { col, symbol ->
                        map[Point(col, row)] = symbol
                    }
                }

            map to moves.filterNot { it == '\n' }
        }

}