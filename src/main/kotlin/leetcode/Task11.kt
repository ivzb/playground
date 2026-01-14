package leetcode

import kotlin.math.max
import kotlin.math.min

class Task11 {

    fun maxArea(height: IntArray): Int {
        return `maxArea two pointers`(height)
    }

    private fun `maxArea brute force`(height: IntArray): Int {
        var bestArea = Int.MIN_VALUE

        val best = height
            .mapIndexed { index, n -> index to n }
            .sortedByDescending { (_, n) -> n }
            .take(11_000)
            .sortedBy { (index, _) -> index }

        for (i in 0 until best.size) {
            for (j in i + 1 until best.size) {
                val (indexI, heightI) = best[i]
                val (indexJ, heightJ) = best[j]

                val height = min(heightI, heightJ)
                val distance = indexJ - indexI
                val area = height * distance
                bestArea = max(bestArea, area)
            }
        }

        return bestArea
    }

    private fun `maxArea two pointers`(height: IntArray): Int {
        var leftIndex = 0
        var rightIndex = height.size - 1
        var bestArea = Int.MIN_VALUE

        while (leftIndex < rightIndex) {
            val leftHeight = height[leftIndex]
            val rightHeight = height[rightIndex]

            val height = min(leftHeight, rightHeight)
            val distance = rightIndex - leftIndex
            val area = height * distance
            bestArea = max(bestArea, area)

            if (leftHeight < rightHeight) {
                leftIndex++
            } else {
                rightIndex--
            }
        }

        return bestArea
    }

}