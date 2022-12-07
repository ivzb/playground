package _2022

import Task
import readInput
import java.util.*

object Task05 : Task {

    override fun partA() = parseInput().let { (stacks, instructions) ->
        instructions.forEach { (move, from, to) ->
            repeat(move) { stacks[to]?.push(stacks[from]?.pop()) }
        }

        findTopCrates(stacks)
    }

    override fun partB() = parseInput().let { (stacks, instructions) ->
        instructions.forEach { (move, from, to) ->
            val temp = LinkedList<String>()
            repeat(move) { temp.push(stacks[from]?.pop()) }
            repeat(move) { stacks[to]?.push(temp.pop()) }
        }

        findTopCrates(stacks)
    }

    private fun findTopCrates(stacks: Map<Int, LinkedList<String>>): String =
        stacks.entries.sortedBy { it.key }.joinToString("") { it.value.peek() }

    private fun parseInput() = readInput("_2022/05")
        .split("\n\n")
        .let { (stacks, instructions) -> parseStacks(stacks) to parseInstructions(instructions) }

    private fun parseStacks(input: String): Map<Int, LinkedList<String>> = input
        .split('\n')
        .map {
            it
                .chunked(4)
                .mapIndexed { index, it ->
                    val position = index + 1
                    val (_, crane) = it.toCharArray()
                    position to crane
                }
                .filter { (_, char) -> char.isLetter() }
        }
        .flatten()
        .groupBy { (index) -> index }
        .map { (index, list) -> index to LinkedList(list.map { (_, char) -> char.toString() }) }
        .toMap()

    private fun parseInstructions(input: String): List<List<Int>> = input
        .split('\n')
        .map { it.split(' ').mapNotNull { it.toIntOrNull() } }
}
