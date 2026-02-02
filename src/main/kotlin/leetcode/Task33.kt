package leetcode

class Task33 {

    fun search(nums: IntArray, target: Int): Int {
        var shiftPosition = nums.size - 1

        if (nums[shiftPosition] == target) {
            return shiftPosition
        }

        while (shiftPosition > 0 && nums[shiftPosition] > nums[shiftPosition - 1]) {
            shiftPosition--

            if (nums[shiftPosition] == target) {
                return shiftPosition
            }
        }

        val shift = nums[shiftPosition]

        if (shift > target) {
            return binarySearch(nums, target, shiftPosition + 1, nums.size - 1)
        }

        if (shift < target) {
            return binarySearch(nums, target, 0, shiftPosition - 1)
        }

        return shiftPosition
    }

    private fun binarySearch(nums: IntArray, target: Int, low: Int, high: Int): Int {
        if (low > high) {
            return -1
        }

        val mid = low + (high - low) / 2

        if (nums[mid] == target) {
            return mid
        }

        if (nums[mid] > target) {
            return binarySearch(nums, target, 0, mid - 1)
        }

        if (nums[mid] < target) {
            return binarySearch(nums, target, mid + 1, high)
        }

        return -1
    }

}