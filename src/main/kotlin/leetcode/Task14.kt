package leetcode

import kotlin.math.min

class Task14 {

    fun longestCommonPrefix(strs: Array<String>): String {
        return `longestCommonPrefix divide and conquer`(strs)
    }

    private fun `longestCommonPrefix compare all`(strs: Array<String>): String {
        val size = strs.minOf { it.length }
        var result = ""

        for (i in 0 until size) {
            val first = strs[0][i]

            if (strs.all { it[i] == first }) {
                result += first
            } else {
                break
            }
        }

        return result
    }

    private fun `longestCommonPrefix sort`(strs: Array<String>): String {
        val sorted = strs.sorted()

        val first = sorted.first()
        val last = sorted.last()

        val size = min(first.length, last.length)
        var substring = ""

        for (i in 0 until size) {
            if (first[i] == last[i]) {
                substring += first[i]
            } else {
                break
            }
        }

        return substring
    }

    private fun `longestCommonPrefix binary search`(strs: Array<String>): String {
        val size = strs.minOf { it.length }

        var low = 0
        var high = size

        fun isPrefix(length: Int): Boolean {
            val prefix = strs[0].take(length)
            return strs.all { it.startsWith(prefix) }
        }

        while (low <= high) {
            val mid = (low + high) / 2

            if (isPrefix(mid)) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }

        return strs[0].substring(0, (low + high) / 2)
    }

    private fun `longestCommonPrefix divide and conquer`(strs: Array<String>): String {
        fun lcp(start: Int, end: Int): String {
            if (start == end) {
                return strs[start]
            }

            val mid = (start + end) / 2
            val left = lcp(start, mid)
            val right = lcp(mid + 1, end)

            val size = min(left.length, right.length)
            var substring = ""

            for (i in 0 until size) {
                if (left[i] == right[i]) {
                    substring += left[i]
                } else {
                    break
                }
            }

            return substring
        }

        return lcp(0, strs.size - 1)
    }

}