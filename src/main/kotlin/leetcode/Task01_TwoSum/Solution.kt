package leetcode.task01_TwoSum

class Solution {

    fun twoSum(nums: IntArray, target: Int): IntArray {
        return `twoSum hash table`(nums, target)
    }

    fun `twoSum brute force`(nums: IntArray, target: Int): IntArray {
        for (i in 0 until nums.size) {
            for (j in i + 1 until nums.size) {
                val n = nums[i]
                val m = nums[j]

                if (n + m == target) {
                    return intArrayOf(i, j)
                }
            }
        }

        return intArrayOf()
    }

    fun `twoSum hash table`(nums: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()

        for (i in nums.indices) {
            map[nums[i]] = i
        }

        for (i in nums.indices) {
            val reminder = target - nums[i]
            val attempt = map[reminder]

            if (attempt != null && attempt != i) {
                return intArrayOf(i, attempt)
            }
        }

        return intArrayOf()
    }

}