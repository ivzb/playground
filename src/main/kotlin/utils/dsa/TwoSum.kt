package utils.dsa

fun twoSum(sorted: List<Int>, target: Int): Pair<Int, Int>? {
    var left = 0
    var right = sorted.size - 1

    while (left < right) {
        val sum = sorted[left] + sorted[right]

        when {
            sum < target -> {
                left++
            }

            sum > target -> {
                right--
            }

            else -> {
                return left to right
            }
        }
    }

    return null
}