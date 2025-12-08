package advent_of_code._2025

import Task
import readInput
import utils.DisjointSet
import utils.pow
import java.lang.Math.sqrt

object Task08 : Task {

    override fun partA() = parseInput()
        .let { (junctionBoxes, distances) ->
            val size = junctionBoxes.size
            val dsu = DisjointSet(size)
            val circuits = Array(size) { IntArray(size) }
            var connections = 0
            var distanceIndex = 0

            while (connections < 1_000) {
                val distance = distances[distanceIndex++]
                val i = distance.i
                val j = distance.j

                if (circuits[i][j] == 0) {
                    circuits[i][j] = 1
                    circuits[j][i] = 1
                    dsu.union(i, j)
                    connections++
                }
            }

            dsu.getAllComponentSizes()
                .sortedByDescending { it }
                .distinct()
                .take(3)
                .fold(1L) { acc, size -> acc * size }
        }

    override fun partB() = parseInput()
        .let { (junctionBoxes, distances) ->
            val size = junctionBoxes.size
            val dsu = DisjointSet(size)
            var distanceIndex = 0

            while (true) {
                val distance = distances[distanceIndex++]
                val i = distance.i
                val j = distance.j

                dsu.union(i, j)

                if (dsu.getAllComponentSizes().size == 1) {
                    return@let junctionBoxes[i][0] * junctionBoxes[j][0]
                }
            }
        }

    private fun parseInput() = readInput("_2025/08")
        .lines()
        .map { it.split(",").map { it.toLong() } }
        .let { junctionBoxes ->
            val distances = junctionBoxes
                .flatMapIndexed { i, a ->
                    junctionBoxes.mapIndexedNotNull { j, b ->
                        if (a != b) Distance(i, j) else null
                    }
                }
                .sortedBy { it.calculate(junctionBoxes) }

            junctionBoxes to distances
        }


    private data class Distance(val i: Int, val j: Int) {

        fun calculate(junctionBoxes: List<List<Long>>): Double {
            val a = junctionBoxes[i]
            val b = junctionBoxes[j]
            return sqrt((a[0] - b[0]).pow(2) + (a[1] - b[1]).pow(2) + (a[2] - b[2]).pow(2).toDouble())
        }
    }

}