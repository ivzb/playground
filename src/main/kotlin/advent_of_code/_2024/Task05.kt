package advent_of_code._2024

import Task
import readInput

object Task05 : Task {

    override fun partA() = parseInput()
        .let { (rules, pages) ->
            pages
                .filter { isInOrder(it, rules) }
                .sumOf { it[it.size / 2] }
        }

    override fun partB() = parseInput()
        .let { (rules, pages) ->
            pages
                .filterNot { isInOrder(it, rules) }
                .map { sort(it, rules) }
                .sumOf { it[it.size / 2] }
        }

    private fun isInOrder(it: List<Int>, rules: Set<Pair<Int, Int>>): Boolean =
        rules.all { (left, right) ->
            val leftIndex = it.indexOf(left)
            val rightIndex = it.indexOf(right)

            when {
                leftIndex == -1 || rightIndex == -1 -> true
                else -> leftIndex < rightIndex
            }
        }

    private fun sort(list: List<Int>, rules: Set<Pair<Int, Int>>): List<Int> =
        list.sortedWith { left, right ->
            when {
                rules.contains(left to right) -> -1
                rules.contains(right to left) -> 1
                else -> 0
            }
        }

    private fun parseInput() = readInput("_2024/05")
        .split("\n\n")
        .let { (rules, pages) ->
            val rules = rules.lines().map { it.split("|") }.map { (first, second) -> first.toInt() to second.toInt() }.toSet()
            val pages = pages.lines().map { it.split(",").map { it.toInt() } }

            rules to pages
        }

}