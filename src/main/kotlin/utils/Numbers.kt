package utils

object Numbers {

    fun greatestCommonDivisor(a: Int, b: Int): Int =
        when {
            b == 0 -> a
            else -> greatestCommonDivisor(b, a % b)
        }

    fun lowestCommonMultiple(a: Int, b: Int): Int =
        (a * b) / greatestCommonDivisor(a, b)

    fun lowestCommonMultiple(list: List<Int>): Int =
        list.reduce { acc, n -> lowestCommonMultiple(acc, n) }
}