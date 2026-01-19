package leetcode

import kotlin.math.abs

class Task16 {

    fun threeSumClosest(nums: IntArray, target: Int): Int {
        return `threeSumClosest two pointers`(nums, target)
    }

    private fun `threeSumClosest two pointers`(nums: IntArray, target: Int): Int {
        val sorted = nums.sorted()
        var closestSum = Int.MAX_VALUE

        for (i in 0 until sorted.size) {
            var j = i + 1
            var k = sorted.size - 1

            while (j < k) {
                val sum = sorted[i] + sorted[j] + sorted[k]

                if (sum == target) {
                    return sum
                }

                val current = abs(target - sum)
                val best = abs(target - closestSum)

                if (current < best) {
                    closestSum = sum
                }

                if (sum > target) {
                    k--
                } else {
                    j++
                }
            }
        }

        return closestSum
    }

}