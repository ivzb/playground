package utils

import utils.Math.factorial

object Combinatorics {

    inline fun <reified T> permutationPairs(elements: List<T>): List<Pair<T, T>> {
        val permutations: HashSet<Pair<T, T>> = HashSet()

        for (i in elements.indices) {
            for (j in i + 1 until elements.size) {
                val pair = Pair(elements[i], elements[j])
                val reversePair = Pair(elements[j], elements[i])

                if (!permutations.contains(pair) && !permutations.contains(reversePair)) {
                    permutations.add(pair)
                }
            }
        }

        return permutations.toList()
    }

    fun <T> permute(items: List<T>): Sequence<List<T>> = sequence {
        if (items.size == 1) {
            yield(items)
            return@sequence
        }

        val item = items[0]

        for (permutation in permute(items.drop(1))) {
            for (i in 0..permutation.size) {
                val newPermutation = permutation.toMutableList()
                newPermutation.add(i, item)
                yield(newPermutation)
            }
        }
    }

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

    fun isPermutation(n1: Long, n2: Long): Boolean {
        val digits = IntArray(10)

        var n1 = n1
        var n2 = n2

        while (n1 != 0L) {
            digits[(n1 % 10).toInt()]++
            n1 /= 10
        }

        while (n2 != 0L) {
            digits[(n2 % 10).toInt()]--
            n2 /= 10
        }

        for (digit in digits) {
            if (digit != 0) {
                return false
            }
        }

        return true
    }

    fun <T> combinationsWithoutRepetition(items: List<T>, k: Int): List<List<T>> {
        val mp = arrayOfNulls<Any?>(k)
        val result = ArrayList<List<T>>()

        fun next(i: Int, after: Int) {
            if (i > k) return

            for (j in after + 1 .. items.size) {
                mp[i - 1] = items[j - 1]

                if (i == k) {
                    result.add(mp.toList() as List<T>)
                }

                next(i + 1, j)
            }
        }

        next(1, 0)

        return result
    }
}