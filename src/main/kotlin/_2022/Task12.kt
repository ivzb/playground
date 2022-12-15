package _2022

import Task
import readInput
import utils.Matrix.adjacentIndexes
import utils.Point
import utils.Point.Companion.toPoint

typealias Graph = Map<Point, Task12.Node>
typealias Adjacent = List<Task12.Node>

object Task12 : Task {

    override fun partA() = shortestPathTo(destination = 'S')

    override fun partB() = shortestPathTo(destination = 'a')

    private fun shortestPathTo(destination: Char): Int {
        val elevationMatrix = parseInput()
        val graph = elevationMatrix.toGraph()
        return findShortestPath(graph, destination)
    }

    private fun parseInput() = readInput("_2022/12")
        .split("\n")
        .map { it.toCharArray().toList() }

    private fun List<List<Char>>.toGraph(): Graph {
        return this
            .mapIndexed { row, it ->
                it.mapIndexed { col, elevation ->
                    val position = (row to col).toPoint()
                    Node(elevation, position)
                }
            }
            .flatten()
            .associateBy { it.position }
    }

    private fun findShortestPath(graph: Graph, destination: Char): Int {
        val startingPoint = graph.entries.first { it.value.elevation == 'E' }.value
        val distances = graph.values.associateWith { Int.MAX_VALUE }.toMutableMap()
        distances[startingPoint] = 0

        val toVisit = mutableListOf(startingPoint)

        while (toVisit.isNotEmpty()) {
            val current = toVisit.removeFirst()
            val currentDistance = (distances[current] ?: error("undefined distance")) + 1

            current.adjacent(graph).forEach { adjacent ->
                if (graph[adjacent.position]?.elevation == destination) {
                    return currentDistance
                }

                val adjacentDistance = distances[adjacent] ?: error("undefined distance")

                if (adjacentDistance > currentDistance) {
                    distances[adjacent] = currentDistance
                    toVisit.add(adjacent)
                }
            }
        }

        error("no path found to destination")
    }

    data class Node(
        val elevation: Char,
        val position: Point,
    ) {

        fun adjacent(graph: Graph): Adjacent =
            adjacentIndexes(position)
                .mapNotNull { position -> graph[position] }
                .filter { adjacent -> adjacent.height() - this.height() >= -1 }

        private fun height(): Int =
            when (elevation) {
                'S' -> 'a'.code
                'E' -> 'z'.code
                else -> elevation.code
            }
    }
}