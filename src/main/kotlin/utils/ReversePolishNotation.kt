package utils

import java.util.*

object ReversePolishNotation {

    private val operators = setOf("+", "-", "*", "/")

    fun eval(tokens: List<String>): Double {
        val stack = Stack<Double>()

        for (token in tokens) {
            if (!operators.contains(token)) {
                stack.push(token.toDouble())
                continue
            }

            val a = stack.pop()
            val b = stack.pop()

            when (token) {
                "+" -> stack.push(b + a)
                "-" -> stack.push(b - a)
                "*" -> stack.push(b * a)
                "/" -> stack.push(b / a)
            }
        }

        return stack.pop()
    }
}