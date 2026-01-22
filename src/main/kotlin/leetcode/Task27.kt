package leetcode

class Task27 {

    fun removeElement(nums: IntArray, `val`: Int): Int {
        return `removeElement two pointers`(nums, `val`)
    }

    private fun `removeElement two pointers`(nums: IntArray, `val`: Int): Int {
        var slow = 0

        for (fast in 0 until nums.size) {
            if (nums[fast] != `val`) {
                nums[slow] = nums[fast]
                slow++
            }
        }

        return slow
    }

}