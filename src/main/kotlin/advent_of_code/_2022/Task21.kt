package advent_of_code._2022

import Task
import readInput

typealias Monkeys = Map<String, Task21.Monkey>

object Task21 : Task {

    private const val ROOT = "root"
    private const val HUMAN = "humn"

    override fun partA() = parseInput().let { monkeys ->
        val root = monkeys[ROOT] ?: error("monkey root not found")
        val result = root.yellNumber(monkeys)
        result
    }

    override fun partB() = parseInput().let { monkeys ->
        val root = monkeys[ROOT] ?: error("monkey root not found")
        val humanNumber = root.equalityCheck(monkeys)
        humanNumber
    }

    class Monkey(
        val name: String,
        var number: Long? = null,
        val left: String? = null,
        val operator: String? = null,
        val right: String? = null
    ) {

        private val isRoot = name == ROOT
        private val isHuman = name == HUMAN

        fun yellNumber(monkeys: Map<String, Monkey>): Long {
            val monkeyNumber = number

            if (monkeyNumber != null) return monkeyNumber

            val left = leftMonkey(monkeys).yellNumber(monkeys)
            val right = rightMonkey(monkeys).yellNumber(monkeys)

            return calculate(left, right, operator).also {
                number = it
            }
        }

        fun equalityCheck(monkeys: Map<String, Monkey>): Long {
            if (!isRoot) error("only root is supposed to to equality check")

            val humanIsInLeftTree = leftMonkey(monkeys).findHuman(monkeys)
            val humanIsInRightTree = rightMonkey(monkeys).findHuman(monkeys)

            return when {
                humanIsInLeftTree -> {
                    val operand2 = rightMonkey(monkeys).yellNumber(monkeys)
                    leftMonkey(monkeys).findHumanNumber(monkeys, operand2)
                }

                humanIsInRightTree -> {
                    val operand1 = leftMonkey(monkeys).yellNumber(monkeys)
                    rightMonkey(monkeys).findHumanNumber(monkeys, operand1)
                }

                else -> error("human is either not found or in both trees")
            }
        }

        private fun leftMonkey(monkeys: Monkeys) = monkeys[left] ?: error("monkey $left not found")

        private fun rightMonkey(monkeys: Monkeys) = monkeys[right] ?: error("monkey $right not found")

        private fun calculate(left: Long, right: Long, operator: String?): Long {
            return when (operator) {
                "+" -> left + right
                "-" -> left - right
                "*" -> left * right
                "/" -> left / right
                else -> error("unknown operator $operator")
            }
        }

        private fun invertLeft(right: Long, result: Long, operator: String?) = when (operator) {
            "+" -> result - right
            "-" -> result + right
            "*" -> result / right
            "/" -> result * right
            else -> error("undefined operator $operator")
        }

        private fun invertRight(left: Long, result: Long, operator: String?) = when (operator) {
            "+" -> result - left
            "-" -> left - result
            "*" -> result / left
            "/" -> left / result
            else -> error("undefined operator $operator")
        }

        private fun findHumanNumber(monkeys: Monkeys, value: Long): Long {
            if (isHuman) {
                return value
            }

            val leftMonkey = leftMonkey(monkeys)
            val rightMonkey = rightMonkey(monkeys)

            val humanIsInLeft = leftMonkey.findHuman(monkeys)
            val humanIsInRight = rightMonkey.findHuman(monkeys)

            return when {
                humanIsInLeft -> {
                    val result = invertLeft(rightMonkey.yellNumber(monkeys), value, operator)
                    leftMonkey.findHumanNumber(monkeys, result)
                }

                humanIsInRight -> {
                    val result = invertRight(leftMonkey.yellNumber(monkeys), value, operator)
                    rightMonkey.findHumanNumber(monkeys, result)
                }

                else -> error("impossible human position")
            }
        }

        private fun findHuman(monkeys: Monkeys): Boolean {
            if (isHuman) return true
            if (number != null) return false

            val leftMonkey = leftMonkey(monkeys)
            val rightMonkey = rightMonkey(monkeys)

            return leftMonkey.findHuman(monkeys) || rightMonkey.findHuman(monkeys)
        }

        override fun toString(): String {
            return if (number != null) {
                "$name: $number"
            } else {
                "$name: $left $operator $right"
            }
        }
    }

    private fun parseInput() = readInput("_2022/21")
        .split("\n")
        .map {
            val split = it.split(':', ' ').filter { it.isNotEmpty() }

            if (split.size == 2) {
                val (name, number) = split
                Monkey(name, number = number.toLong())
            } else {
                val (name, operand1, operator, operand2) = split
                Monkey(name, left = operand1, operator = operator, right = operand2)
            }
        }
        .associateBy { it.name }

}