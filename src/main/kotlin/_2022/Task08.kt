package _2022

import Task
import readInput
import java.lang.Integer.max

typealias Grid = List<List<Int>>

object Task08 : Task {

    override fun partA(): Int = traverseFold(parseInput()) { totalVisibleTrees, neighbours, treeHeight ->
        neighbours
            .map { neighbour -> neighbour.isEmpty() || treeHeight > neighbour.max() }
            .distinct()
            .count { isVisible -> isVisible }
            .let { currentVisibleTrees -> totalVisibleTrees + currentVisibleTrees }
    }

    override fun partB() = traverseFold(parseInput()) { maxScore, neighbours, treeHeight ->
        neighbours
            .map { neighbour -> neighbour.takeWhileInclusive { treeHeight > it }.count() }
            .reduce { scenicScore, viewingDistance -> scenicScore * viewingDistance }
            .let { currencyScore -> max(maxScore, currencyScore) }
    }

    private fun parseInput() = readInput("_2022/08")
        .split('\n')
        .map { it.map { it.digitToInt() } }

    private fun traverseFold(grid: Grid, operation: (Int, Grid, Int) -> Int): Int = grid
        .mapIndexed { i, row -> row.mapIndexed { j, treeHeight -> (i to j) to treeHeight } }
        .flatten()
        .fold(0) { total, (position, treeHeight) ->
            val (i, j) = position
            operation(total, neighbours(grid, i, j), treeHeight)
        }

    private fun neighbours(grid: List<List<Int>>, i: Int, j: Int) = listOf(
        grid.slice(0 until i).map { it[j] }.reversed(), // top
        grid[i].slice(j + 1 until grid[j].size), // right
        grid.slice(i + 1 until grid.size).map { it[j] }, // bottom
        grid[i].slice(0 until j).reversed(), // left
    )

    private fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean) = sequence {
        this@takeWhileInclusive.takeWhile { predicate(it) }.forEach { yield(it) }
        this@takeWhileInclusive.firstOrNull { !predicate(it) }?.let { yield(it) }
    }

}