package _2022

import Task
import readInput
import utils.Numbers.lowestCommonMultiple
import utils.Parse.firstInt

typealias ReliefWorryLevel = (Long) -> Long
typealias Inspection = Pair<Int, Long>

object Task11 : Task {

    private val lcmDividers = lowestCommonMultiple(parseInput().map { it.divisibleBy })

    override fun partA() = chaseMonkeys(rounds = 20) { worryLevel ->
        worryLevel / 3
    }

    override fun partB() = chaseMonkeys(rounds = 10_000) { worryLevel ->
        worryLevel % lcmDividers
    }

    private fun chaseMonkeys(rounds: Int, reliefWorryLevel: ReliefWorryLevel): Long = parseInput()
        .let { monkeys ->
            (0 until rounds).fold(Array(monkeys.size) { 0L }) { inspections, _ ->
                monkeys.forEachIndexed { index, monkey ->
                    monkey.`throw`(reliefWorryLevel).forEach { (throwIndex, item) ->
                        inspections[index]++
                        monkeys[throwIndex].catch(item)
                    }
                }

                inspections
            }
        }
        .sortedDescending()
        .take(2)
        .reduce { acc, inspections -> acc * inspections }

    private fun parseInput() = readInput("_2022/11")
        .split("\n\n")
        .mapIndexed { index, it ->
            it.split("\n").let(::Monkey)
        }

    private data class Monkey(
        val items: ArrayDeque<Long>,
        val operation: String,
        val divisibleBy: Int,
        val throwIndexes: Map<Boolean, Int>
    ) {

        constructor(it: List<String>) : this(
            items = ArrayDeque(it[1].split(' ', ',').mapNotNull { it.toLongOrNull() }),
            operation = it[2].split('=')[1].trim(),
            divisibleBy = it[3].firstInt(),
            throwIndexes = mapOf(
                true to it[4].firstInt(),
                false to it[5].firstInt()
            )
        )

        fun `throw`(reliefWorryLevel: ReliefWorryLevel): List<Inspection> =
            (0 until items.size).fold(listOf()) { inspections, _ ->
                val item = items.removeFirst()

                val worryLevel = when {
                    operation == "old * old" -> item * item
                    operation.startsWith("old *") -> item * operation.firstInt()
                    operation.startsWith("old +") -> item + operation.firstInt()
                    else -> error("undefined operation $operation")
                }

                val inspection = reliefWorryLevel(worryLevel).let { manageableWorryLevel ->
                    val isDivisible = manageableWorryLevel % divisibleBy == 0L
                    val throwIndex = throwIndexes[isDivisible] ?: error("undefined index $isDivisible")
                    throwIndex to manageableWorryLevel
                }

                inspections + inspection
            }

        fun catch(item: Long) {
            items.add(item)
        }

    }

}