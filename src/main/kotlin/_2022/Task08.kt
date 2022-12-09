package _2022

import Task
import readInput
import utils.Matrix.adjacentRowsAndCols
import java.lang.Integer.max

typealias Grid = List<List<Int>>

object Task08 : Task {

    override fun partA(): Int = traverseFold(parseInput()) { totalVisibleTrees, neighbours, treeHeight ->
        neighbours
            .map { neighbour -> neighbour.isEmpty() || treeHeight > neighbour.max() }
            .count { isVisible -> isVisible }
            .coerceAtMost(1)
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
            val neighbours = adjacentRowsAndCols(grid, i, j)
            operation(total, neighbours, treeHeight)
        }

    private fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean) = sequence {
        this@takeWhileInclusive.takeWhile { predicate(it) }.forEach { yield(it) }
        this@takeWhileInclusive.firstOrNull { !predicate(it) }?.let { yield(it) }
    }

}