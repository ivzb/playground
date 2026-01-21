package leetcode

class Task26 {

    fun removeDuplicates(nums: IntArray): Int {
        return `removeDuplicates two pointers`(nums)
    }

    private fun `removeDuplicates naive`(nums: IntArray): Int {
        var unique = 1

        for (i in 1 until nums.size) {
            val current = nums[i]
            val prev = nums[i - 1]

            if (current == prev || current + 1_000 == prev) {
                nums[i] = current + 1_000
                continue
            }

            unique++
        }

        nums.sort()

        return unique
    }

    private fun `removeDuplicates two pointers`(nums: IntArray): Int {
        var slow = 1

        for (fast in 1 until nums.size) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast]
                slow++
            }
        }

        return slow
    }

}