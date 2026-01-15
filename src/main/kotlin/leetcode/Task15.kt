package leetcode

class Task15 {

    fun threeSum(nums: IntArray): List<List<Int>> {
        return `threeSum two pointers`(nums)
    }

    fun `threeSum two pointers`(nums: IntArray): List<List<Int>> {
        val sorted = nums.sorted()
        val result = HashSet<List<Int>>()

        for (i in 0 until sorted.size) {
            var j = i + 1
            var k = sorted.size - 1

            while (j < k) {
                val sum = sorted[i] + sorted[j] + sorted[k]

                when {
                    sum > 0 -> {
                        k--
                    }

                    sum < 0 -> {
                        j++
                    }

                    else -> {
                        result.add(listOf(sorted[i], sorted[j], sorted[k]))
                        j++
                    }
                }
            }
        }

        return result.toList()
    }

}