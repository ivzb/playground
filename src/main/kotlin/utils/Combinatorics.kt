package utils

import utils.Math.factorial

object Combinatorics {

    fun <T> lexicographicPermutationAt(items: MutableList<T>, n: Int): List<T> {
        val permutation = ArrayList<T>(items.size)
        var target = n - 1

        while (items.isNotEmpty()) {
            val combos = factorial(items.size - 1).toInt()
            val item = items.removeAt(target / combos)
            permutation.add(item)
            target %= combos
        }

        return permutation
    }

    fun lexicographicPermutations(items: MutableList<Int>): Sequence<List<Int>> = sequence {
        do {
            yield(items)
        } while (nextPermutation(items))
    }

    private fun nextPermutation(items: MutableList<Int>): Boolean {
        // Find longest non-increasing suffix
        var i = items.size - 1
        while (i > 0 && items[i - 1] >= items[i]) i--
        // Now i is the head index of the suffix

        // Are we at the last permutation already?
        if (i <= 0) return false

        // Let items[i - 1] be the pivot
        // Find rightmost element greater than the pivot
        var j = items.size - 1
        while (items[j] <= items[i - 1]) j--
        // Now the value items[j] will become the new pivot
        // Assertion: j >= i

        // Swap the pivot with j
        var temp = items[i - 1]
        items[i - 1] = items[j]
        items[j] = temp

        // Reverse the suffix
        j = items.size - 1
        while (i < j) {
            temp = items[i]
            items[i] = items[j]
            items[j] = temp
            i++
            j--
        }

        // Successfully computed the next permutation
        return true
    }
}