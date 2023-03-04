package euler

import EulerTask
import utils.Combinatorics.combinationsWithoutRepetition
import utils.Combinatorics.permute
import utils.ReversePolishNotation

object Task93ArithmeticExpressions : EulerTask("Arithmetic expressions") {

    override fun solution(): Int {
        var bestResult = 0
        var bestSet = listOf<String>()

        val allNumberSets = combinationsWithoutRepetition((1..9).map { it.toString() }, 4)
        val operators = listOf("+", "-", "*", "/")

        for (currentSet in allNumberSets) {
            val results = HashMap<Double, Int>()

            fun check(input: List<String>) {
                val result = ReversePolishNotation.eval(input)
                results[result] = results.getOrDefault(result, 0) + 1
            }

            for (numbers in permute(currentSet)) {
                for (op0 in operators.indices) {
                    for (op1 in operators.indices) {
                        for (op2 in operators.indices) {
                            check(listOf(numbers[0], numbers[1], operators[op0], numbers[2], operators[op1], numbers[3], operators[op2])) // ((a . b) . c) . d
                            check(listOf(numbers[0], numbers[1], operators[op0], numbers[2], numbers[3], operators[op1], operators[op2])) // (a . b) . (c . d)
                            check(listOf(numbers[1], numbers[2], operators[op1], numbers[0], operators[op0], numbers[3], operators[op2])) // (a . (b . c)) . d
                            check(listOf(numbers[1], numbers[2], operators[op1], numbers[3], operators[op2], numbers[0], operators[op0])) // a . ((b . c) . d)
                            check(listOf(numbers[2], numbers[3], operators[op2], numbers[1], operators[op1], numbers[0], operators[op0])) // a . (b . (c . d))
                        }
                    }
                }
            }

            var currentResult = 0
            var n = 1

            while (results.containsKey(n.toDouble())) {
                currentResult++
                n++
            }

            if (currentResult > bestResult) {
                bestResult = currentResult
                bestSet = currentSet
            }
        }

        return bestSet.joinToString("").toInt()
    }

}
