package _2022

import Task
import readInput
import java.lang.Exception
import java.lang.StringBuilder
import java.util.LinkedList

object Task09 : Task {

    data class Coord(var x: Int, var y: Int) {

//        fun moveTo(newX: Int, newY: Int) {
//            this.x = newX
//            this.y = newY
//        }
    }

    override fun partA() = parseInput()
        .let {
            val visited = ArrayList<Coord>()
            val start = Coord(0, 0)
            var head = start
            var tail = start

            for ((direction, steps) in it) {
                (0 until steps).forEach {
                    if (direction == "R") {
                        head = Coord(head.x + 1, head.y)

                        val xDiff = head.x - tail.x
                        val yDiff = head.y - tail.y

                        if (xDiff == 2) {
                            tail = Coord(tail.x + 1, tail.y + yDiff)
                        }
                    }

                    if (direction == "U") {
                        head = Coord(head.x, head.y - 1)

                        val xDiff = head.x - tail.x
                        val yDiff = head.y - tail.y

                        if (yDiff == -2) {
                            tail = Coord(tail.x + xDiff, tail.y - 1)
                        }
                    }

                    if (direction == "L") {
                        head = Coord(head.x - 1, head.y)

                        val xDiff = head.x - tail.x
                        val yDiff = head.y - tail.y

                        if (xDiff == -2) {
                            tail = Coord(tail.x - 1, tail.y + yDiff)
                        }
                    }

                    if (direction == "D") {
                        head = Coord(head.x, head.y + 1)

                        val xDiff = head.x - tail.x
                        val yDiff = head.y - tail.y

                        if (yDiff == 2) {
                            tail = Coord(tail.x + xDiff, tail.y + 1)
                        }
                    }

                    visited.add(tail)
                }
            }

            visited.distinct().size
        }

    override fun partB() = parseInput()
        .let {
            val visited = LinkedHashSet<Coord>()
            val start = Coord(0, 0)
            var head = start
            var tails = LinkedList((0 until 9).map { start }.toList())

            for ((direction, steps) in it) {
                (0 until steps).forEach {
                    if (direction == "R") {
                        head = Coord(head.x + 1, head.y)
                    }

                    if (direction == "U") {
                        head = Coord(head.x, head.y - 1)
                    }

                    if (direction == "L") {
                        head = Coord(head.x - 1, head.y)
                    }

                    if (direction == "D") {
                        head = Coord(head.x, head.y + 1)
                    }

                    val newTail = LinkedList<Coord>()
                    var head2 = head

                    while (tails.isNotEmpty()) {
                        val tail = tails.removeFirst()
                        var nt = tail

                        val xDiff = head2.x - tail.x
                        val yDiff = head2.y - tail.y

                        if (xDiff == -2 && yDiff == -2) {
                            nt = Coord(tail.x - 1, tail.y - 1)
                        } else if (xDiff == -2 && yDiff == 2) {
                            nt = (Coord(tail.x - 1, tail.y + 1))
                        } else if (xDiff == 2 && yDiff == -2) {
                            nt = Coord(tail.x + 1, tail.y - 1)
                        } else if (xDiff == 2 && yDiff == 2) {
                            nt = (Coord(tail.x + 1, tail.y + 1))
                        } else if (xDiff == 2) {
                            nt = Coord(tail.x + 1, tail.y + yDiff)
                        } else if (yDiff == -2) {
                            nt = Coord(tail.x + xDiff, tail.y - 1)
                        } else if (xDiff == -2) {
                            nt = Coord(tail.x - 1, tail.y + yDiff)
                        } else if (yDiff == 2) {
                            nt = Coord(tail.x + xDiff, tail.y + 1)
                        }

                        newTail.addLast(nt)
                        head2 = nt

                    }

                    tails = newTail
                    visited.add(tails.last())
                }
            }

            visited.distinct().size
        }

    fun print(start: Coord, head: Coord, tails: List<Coord>): String {
        val builder = StringBuilder()
        val x = tails.map { it.x } + listOf(head.x) + listOf(start.x)
        val y = tails.map { it.y } + listOf(head.y) + listOf(start.y)
        val xfrom = x.min()
        val xto = x.max()
        val yfrom = y.min()
        val yto = y.max()

        (yfrom..yto).forEach { y ->
            (xfrom..xto).forEach { x ->
                var char = '.'

                if (start.x == x && start.y == y) {
                    char = 's'
                }

                if (head.x == x && head.y == y) {
                    char = 'H'
                }

                tails.find {
                    it.x == x && it.y == y
                }?.let {
                    val n = tails.indexOf(it) + 1
                    char = n.digitToChar()
                }

                builder.append(char)
            }
            builder.appendLine()
        }
        
        return builder.toString()
    }

    fun visited(start: Coord, visited: List<Coord>): String {
        val builder = StringBuilder()
        val xfrom = visited.minOf { it.x }
        val xto = visited.maxOf { it.x }
        val yfrom = visited.minOf { it.y }
        val yto = visited.maxOf { it.y }

        (yfrom..yto).forEach { y ->
            (xfrom..xto).forEach { x ->
                var char = '.'

                if (start.x == x && start.y == y) {
                    char = 's'
                }
//
                visited.find {
                    it.x == x && it.y == y
                }?.let {
                    char = '#'
                }

                builder.append(char)
            }
            builder.appendLine()
        }
        return builder.toString()
    }

    private fun parseInput() = readInput("_2022/09")
        .split("\n")
        .map { it.split(" ").let { (direction, steps) -> direction to steps.toInt() } }

}