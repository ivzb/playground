package leetcode

class Task18 {

    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        return `fourSum two pointers`(nums, target)
    }

    private fun `fourSum two pointers`(nums: IntArray, target: Int): List<List<Int>> {
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

}