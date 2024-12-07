package advent_of_code._2024

import Task
import readInput
import java.util.*

object Task07 : Task {

    override fun partA() = parseInput()
        .let {
            solve(it, "+*")
        }

    override fun partB() = parseInput()
        .let {
            solve(it, "+*|")
        }

    private fun solve(it: List<Pair<Long, List<Long>>>, testOperators: String): Long {
        val solutions = mutableListOf<Long>()
        val cachedOperators = cachedOperators(it, testOperators)

        it.forEach { (testValue, remainingNumbers) ->
            for (operators in cachedOperators[remainingNumbers.size]!!) {
                val remainingNumbersQ = LinkedList(remainingNumbers)
                val operatorsQ = LinkedList(operators)

                while (operatorsQ.isNotEmpty()) {
                    val left = remainingNumbersQ.pop()
                    val right = remainingNumbersQ.pop()
                    val operator = operatorsQ.pop()

                    val result = when (operator) {
                        '+' -> left + right
                        '*' -> left * right
                        '|' -> "$left$right".toLong()
                        else -> throw Exception("unknown operator $operator")
                    }

                    remainingNumbersQ.push(result)

                    if (result > testValue) {
                        break
                    }
                }

                if (testValue == remainingNumbersQ.pop()) {
                    solutions.add(testValue)
                    break
                }
            }
        }

        return solutions.sum()
    }

    private fun cachedOperators(it: List<Pair<Long, List<Long>>>, testOperators: String): Map<Int, List<List<Char>>> {
        val testOps = testOperators.map { listOf(it) }
        val opsMap = mutableMapOf<Int, List<List<Char>>>()
        val keys = it.groupBy { (_, list) -> list.size }.keys

        keys.forEach { remainingNumbers ->
            val result = mutableListOf<List<Char>>()
            val q = LinkedList<List<Char>>()
            q.addAll(testOps)

            while (q.isNotEmpty()) {
                val operators = q.pop()

                if (operators.size == remainingNumbers - 1) {
                    result.add(operators)
                    continue
                }

                testOps.forEach {
                    q.add(operators + it)
                }
            }

            opsMap[remainingNumbers] = result
        }

        return opsMap
    }

    private fun parseInput() = readInput("_2024/07")
        .lines()
        .map {
            val split = it.split(": ")
            val testValue = split[0].toLong()
            val remainingNumbers = split[1].split(' ').map { it.toLong() }

            testValue to remainingNumbers
        }

}