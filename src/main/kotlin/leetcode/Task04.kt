package leetcode

class Task04 {

    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        return `findMedianSortedArrays sorting`(nums1, nums2)
    }

    private fun `findMedianSortedArrays trivial`(nums1: IntArray, nums2: IntArray): Double {
        val sorted = (nums1 + nums2).sorted().toIntArray()
        return median(sorted)
    }

    private fun `findMedianSortedArrays sorting`(nums1: IntArray, nums2: IntArray): Double {
        var i = 0
        var j = 0
        val sorted = IntArray(nums1.size + nums2.size)
        var index = 0

        while (i < nums1.size || j < nums2.size) {
            fun takeA() {
                sorted[index] = nums1[i]
                i++
                index++
            }

            fun takeB() {
                sorted[index] = nums2[j]
                j++
                index++
            }

            when {
                i == nums1.size -> {
                    takeB()
                }

                j == nums2.size -> {
                    takeA()
                }

                nums1[i] >= nums2[j] -> {
                    takeB()
                }

                nums1[i] < nums2[j] -> {
                    takeA()
                }
            }
        }

        return median(sorted)
    }

    private fun median(arr: IntArray): Double {
        val mid = arr.size / 2

        return if (arr.size % 2 == 0) {
            (arr[mid] + arr[mid - 1]) / 2.0
        } else {
            arr[mid].toDouble()
        }
    }

}