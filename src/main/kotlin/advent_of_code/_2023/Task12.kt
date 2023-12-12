package advent_of_code._2023

import Task
import readInput
import java.lang.StringBuilder
import java.util.LinkedList

object Task12 : Task {

    override fun partA(): Int {
        return parseInput()
            .sumOf { (springs, sizes) ->
                var count = 0
                val q = LinkedList<String>()
                q.add(springs)

                while (q.isNotEmpty()) {
                    val spring = q.pop()
                    val index = spring.indexOf('?')

                    if (index == -1) {
                        if (isSolution(spring, sizes)) {
                            count++
                        }

                        continue
                    }

                    q.add(spring.replaceAt(index, '.'))
                    q.add(spring.replaceAt(index, '#'))
                }

                count
            }
    }

    override fun partB(): Int = -1

    private fun String.replaceAt(index: Int, char: Char): String {
        val sb = StringBuilder(this)
        sb.setCharAt(index, char)
        return sb.toString()
    }

    private fun isSolution(spring: String, sizes: List<Int>): Boolean {
        val splits = spring.split('.').filter { it.isNotEmpty() }.map { it.count() }
        return splits == sizes
    }

    private fun parseInput() = readInput("_2023/12")
        .split("\n")
        .map {
            val (springs, sizes) = it.split(' ')
            springs to sizes.split(',').map { it.toInt() }
        }

}