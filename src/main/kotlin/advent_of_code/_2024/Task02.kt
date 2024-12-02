package advent_of_code._2024

import Task
import readInput
import kotlin.math.abs

object Task02 : Task {

    override fun partA(): Int = parseInput()
        .let {
            it.fold(0) { acc, report ->
                if (check(report)) {
                    acc + 1
                } else {
                    acc
                }
            }
        }

    override fun partB() = parseInput()
        .let {
            it.fold(0) { acc, report ->
                if (check(report) || tolerateCheck(report)) {
                    acc + 1
                } else {
                    acc
                }
            }
        }

    private fun check(report: List<Int>): Boolean {
        val zipped = report.zipWithNext()
        fun inRange(a: Int, b: Int) = (abs(a - b) in 1..3)
        val increasing = zipped.all { (a, b) -> a < b && inRange(a, b) }
        val decreasing = zipped.all { (a, b) -> a > b && inRange(a, b) }

        return increasing || decreasing
    }

    private fun tolerateCheck(report: List<Int>): Boolean {
        for (i in report.indices) {
            val toleratedReport = report.toMutableList()
            toleratedReport.removeAt(i)

            if (check(toleratedReport)) {
                return true
            }
        }

        return false
    }

    private fun parseInput() = readInput("_2024/02")
        .lines()
        .map {
            it.split(" ").map { it.toInt() }
        }
}