package _2022

import Task
import readInput
import java.lang.Integer.max

typealias Grid = List<List<Int>>

object Task08 : Task {

    override fun partA(): Int = iterate(parseInput()) { grid, acc, i, j, treeHeight ->
        val visibleTrees = neighbours(grid, i, j)
            .map { neighbour -> neighbour.isEmpty() || treeHeight > neighbour.max() }
            .distinct()
            .count { isVisible -> isVisible }

        acc + visibleTrees
    }

    override fun partB() = iterate(parseInput()) { grid, acc, i, j, _ ->
        val scenicScore = neighbours(grid, i, j)
            .map { neighbour -> neighbour.takeWhileInclusive { grid[i][j] > it }.count() }
            .reduce { score, viewingDistance -> score * viewingDistance }

        max(acc, scenicScore)
    }

    private fun parseInput() = readInput("_2022/08")
        .split('\n')
        .map { it.map { it.digitToInt() } }

    private fun iterate(grid: Grid, operation: (Grid, Int, Int, Int, Int) -> Int): Int = grid
        .mapIndexed { i, row -> row.mapIndexed { j, current -> (i to j) to current } }
        .flatten()
        .fold(0) { acc, (position, treeHeight) ->
            val (i, j) = position
            operation(grid, acc, i, j, treeHeight)
        }

    private fun neighbours(grid: List<List<Int>>, i: Int, j: Int) = listOf(
        grid.subList(0, i).map { it[j] }.reversed(),
        grid[i].subList(j + 1, grid[i].size),
        grid.subList(i + 1, grid.size).map { it[j] },
        grid[i].subList(0, j).reversed(),
    )

    private fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean) = sequence {
        this@takeWhileInclusive.takeWhile { predicate(it) }.forEach { yield(it) }
        this@takeWhileInclusive.firstOrNull { !predicate(it) }?.let { yield(it) }
    }

}