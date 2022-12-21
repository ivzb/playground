package _2022

import Task
import readInput

typealias Monkeys = Map<String, Task21.Monkey>

object Task21 : Task {

    private const val ROOT = "root"
    private const val HUMAN = "humn"

    class Monkey(
        val name: String,
        var number: Long? = null,
        val operand1: String? = null,
        val operator: String? = null,
        val operand2: String? = null
    ) {

        private val isRoot = name == ROOT
        private val isHuman = name == HUMAN

        fun yellNumber(monkeys: Map<String, Monkey>, cache: Boolean = false): Long {
            val monkeyNumber = number

            if (monkeyNumber != null) {
                return monkeyNumber
            }

            val operand1 = operand1Monkey(monkeys).yellNumber(monkeys)
            val operand2 = operand2Monkey(monkeys).yellNumber(monkeys)

            val monkeysNumber = calculate(operand1, operand2, operator)

            // todo: remove cache arg and always cache the number
            if (cache) {
                number = monkeyNumber
            }
            return monkeysNumber
        }

        private fun operand1Monkey(monkeys: Monkeys) = monkeys[operand1] ?: error("monkey $operand1 not found")

        private fun operand2Monkey(monkeys: Monkeys) = monkeys[operand2] ?: error("monkey $operand2 not found")

        private fun calculate(operand1: Long, operand2: Long, operator: String?): Long {
            return when (operator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> operand1 / operand2
                else -> error("unknown operator $operator")
            }
        }

        private fun reverse(operator: String?): String {
            return when (operator) {
                "+" -> "-"
                "-" -> "+"
                "*" -> "/"
                "/" -> "*"
                else -> error("unknown operator $operator")
            }
        }

        // todo: remove
        fun precache(monkeys: Map<String, Monkey>) {
            val monkeyNumber = number

            if (monkeyNumber != null) {
                return
            }

            val operand1Monkey = operand1Monkey(monkeys)
            val operand2Monkey = operand2Monkey(monkeys)

            val humanIsOperator1 = operand1Monkey.isHuman
            val humanIsOperator2 = operand2Monkey.isHuman

            if (humanIsOperator1) {
                operand2Monkey.yellNumber(monkeys, true)
                return
            } else if (humanIsOperator2) {
                operand1Monkey.yellNumber(monkeys, true)
                return
            }

            val humanIsInOperand1Tree = operand1Monkey.findHuman(monkeys)
            val humanIsInOperand2Tree = operand2Monkey.findHuman(monkeys)

            if (humanIsInOperand1Tree && !humanIsInOperand2Tree) {
                operand2Monkey.yellNumber(monkeys, true)
                operand1Monkey.precache(monkeys)
            } else if (!humanIsInOperand1Tree && humanIsInOperand2Tree) {
                operand1Monkey.yellNumber(monkeys, true)
                operand2Monkey.precache(monkeys)
            } else {
                error("human is either not found or in both trees?")
            }
        }

        fun equalityCheck(monkeys: Map<String, Monkey>): Long {
            if (!isRoot) {
                error("only root is supposed to to equality check")
            }

            val operand1Monkey = operand1Monkey(monkeys)
            val operand2Monkey = operand2Monkey(monkeys)

            val humanIsInOperand1Tree = operand1Monkey.findHuman(monkeys)
            val humanIsInOperand2Tree = operand2Monkey.findHuman(monkeys)

            return if (humanIsInOperand1Tree && !humanIsInOperand2Tree) {
                val operand2 = operand2Monkey(monkeys).yellNumber(monkeys)
                operand1Monkey(monkeys).findHumanNumber(monkeys, operand2)
            } else if (!humanIsInOperand1Tree && humanIsInOperand2Tree) {
                val operand1 = operand1Monkey(monkeys).yellNumber(monkeys)
                operand2Monkey(monkeys).findHumanNumber(monkeys, operand1)
            } else {
                error("human is either not found or in both trees?")
            }
        }

        // todo: remove
        fun equalityCheck2(monkeys: Map<String, Monkey>): Long {
            if (!isRoot) {
                error("only root is supposed to to equality check")
            }

            val operand1Monkey = operand1Monkey(monkeys)
            val operand2Monkey = operand2Monkey(monkeys)

            val number1 = operand1Monkey.yellNumber(monkeys)
            val number2 = operand2Monkey.yellNumber(monkeys)

            return (number1 - number2).coerceIn(LESS, MORE)
        }

        fun findHumanNumber(monkeys: Monkeys, value: Long): Long {
            val monkeyNumber = number

            if (monkeyNumber != null) {
                return monkeyNumber
            }

            val operand1Monkey = operand1Monkey(monkeys)
            val operand2Monkey = operand2Monkey(monkeys)

            val humanIsOperator1 = operand1Monkey.isHuman
            val humanIsOperator2 = operand2Monkey.isHuman

            val operator = reverse(this.operator)

            if (humanIsOperator1) {
                val operand2 = operand2Monkey(monkeys).yellNumber(monkeys)
                val calc = calculate(operand2, value, operator)
                return calc
            } else if (humanIsOperator2) {
                val operand1 = operand1Monkey(monkeys).yellNumber(monkeys)
                val calc = calculate(value, operand1, operator)
                return calc
            }

            val humanIsInOperand1Tree = operand1Monkey.findHuman(monkeys)
            val humanIsInOperand2Tree = operand2Monkey.findHuman(monkeys)

            return if (humanIsInOperand1Tree && !humanIsInOperand2Tree) {
                val op1 = operand2Monkey(monkeys).yellNumber(monkeys)
                val op2 = value
                val calc = calculate(op1, op2, operator)
                operand1Monkey(monkeys).findHumanNumber(monkeys, calc)
            } else if (!humanIsInOperand1Tree && humanIsInOperand2Tree) {
                val op1 = value
                val op2 = operand1Monkey(monkeys).yellNumber(monkeys)
                val calc = calculate(op1, op2, operator)
                operand2Monkey(monkeys).findHumanNumber(monkeys, calc)
            } else {
                error("human is either not found or in both trees?")
            }
        }

        private fun findHuman(monkeys: Monkeys): Boolean {
            if (isHuman) {
                return true
            }

            if (number != null) {
                return false
            }

            val operand1Monkey = operand1Monkey(monkeys)
            val operand2Monkey = operand2Monkey(monkeys)

            return operand1Monkey.findHuman(monkeys) || operand2Monkey.findHuman(monkeys)
        }

        override fun toString(): String {
            if (number != null) {
                return "$name: $number"
            }

            return "$name: $operand1 $operator $operand2"
        }
    }

    override fun partA() = parseInput().let { monkeys ->
        val root = monkeys[ROOT] ?: error("monkey root not found")
        val result = root.yellNumber(monkeys)
        result
    }

    // todo: remove
    val LESS = -1L
    val SAME = 0L
    val MORE = 1L

    override fun partB() = parseInput().let { monkeys ->
        // todo: make it work without binary search
        val root = monkeys[ROOT] ?: error("monkey root not found")
//        val humanNumber = root.equalityCheck(monkeys)
//        println("humanNumber = $humanNumber")

        // todo: remove
        root.precache(monkeys)

        var min = 866L
        var max = 7_243_227_128_687L

        while (true) {
            val humanNumber = (min + max) / 2

            monkeys[HUMAN]!!.number = humanNumber

            when (root.equalityCheck2(monkeys)) {
                SAME -> return@let humanNumber
                LESS -> max = humanNumber
                MORE -> min = humanNumber
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
                Monkey(name, operand1 = operand1, operator = operator, operand2 = operand2)
            }
        }
        .associateBy { it.name }

}