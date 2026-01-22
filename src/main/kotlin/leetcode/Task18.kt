package leetcode

import java.util.*


class Task18 {

    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        return `fourSum two pointers kSum`(nums, target)
    }

    private fun `fourSum two pointers nested fors`(nums: IntArray, target: Int): List<List<Int>> {
        val sorted = nums.sorted()
        val result = HashSet<List<Int>>()

        for (i in 0 until sorted.size) {
            for (j in i + 1 until sorted.size) {
                var k = j + 1
                var l = sorted.size - 1

                while (k < l) {
                    val sum = sorted[i].toLong() + sorted[j] + sorted[k] + sorted[l]

                    when {
                        sum > target -> {
                            l--
                        }

                        sum < target -> {
                            k++
                        }

                        else -> {
                            result.add(listOf(sorted[i], sorted[j], sorted[k], sorted[l]))
                            k++
                        }
                    }
                }
            }
        }

        return result.toList()
    }

    private fun `fourSum two pointers kSum`(nums: IntArray, target: Int): List<List<Int>> {
        return kSum(nums, target.toLong(), start = 0, k = 4)
    }

    private fun kSum(nums: IntArray, target: Long, start: Int, k: Int): List<List<Int>> {
        val result: ArrayList<ArrayList<Int>> = ArrayList()
        Arrays.sort(nums)

        if (start == nums.size) {
            return result
        }

        val averageValue = target / k

        if (nums[start] > averageValue || averageValue > nums[nums.size - 1]) {
            return result
        }

        if (k == 2) {
            return twoSum(nums, target, start)
        }

        for (i in start until nums.size) {
            if (i == start || nums[i - 1] != nums[i]) {
                for (subset in kSum(nums, target - nums[i], i + 1, k - 1)) {
                    result.add(ArrayList(arrayListOf(nums[i])))
                    result[result.size - 1].addAll(subset)
                }
            }
        }

        return result
    }

    private fun twoSum(nums: IntArray, target: Long, start: Int): ArrayList<ArrayList<Int>> {
        val result: ArrayList<ArrayList<Int>> = ArrayList()
        var low = start
        var high = nums.size - 1

        while (low < high) {
            val currentSum = nums[low] + nums[high]

            if (currentSum < target || (low > start && nums[low] == nums[low - 1])) {
                ++low
            } else if (currentSum > target || (high < nums.size - 1 && nums[high] == nums[high + 1])) {
                --high
            } else {
                result.add(arrayListOf(nums[low++], nums[high--]))
            }
        }

        return result
    }

}