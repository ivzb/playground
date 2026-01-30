package leetcode

class Task31 {

    fun nextPermutation(nums: IntArray): Unit {
        var i = nums.size - 1

        while (i > 0 && nums[i] < nums[i - 1]) {
            i--
        }

        if (i <= 0) {
            // break-point does not exist i.e. the array is sorted in decreasing order,
            // the given permutation is the last one in the sorted order of all possible permutations.
            // So, the next permutation must be the first i.e. the permutation in increasing order.
            nums.reverse()
            return
        }

        var j = nums.size - 1

        while (nums[i - 1] > nums[j]) {
            j--
        }

        swap(nums, i - 1, j)

        j = nums.size - 1

        while (i < j) {
            swap(nums, i, j)
            i++
            j--
        }
    }

    fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]
        nums[i] = nums[j]
        nums[j] = temp
    }

}