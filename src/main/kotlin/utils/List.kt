package utils

fun List<Number>.toInt() = this.joinToString("").toInt()
fun List<Number>.toLong() = this.joinToString("").toLong()

fun <T> List<List<T>>.transpose(): List<List<T>> {
    if (isEmpty()) {
        return emptyList()
    }

    val rows = size
    val cols = first().size

    return (0 until cols).map { col ->
        (0 until rows).map { row ->
            this[row][col]
        }
    }
}

fun List<String>.transposeStrings(): List<String> {
    if (isEmpty()) {
        return emptyList()
    }

    val len = maxOf { it.length }

    return (0 until len).map { col ->
        this.map { row -> row.getOrNull(col) ?: ' ' }
            .joinToString(separator = "")
    }
}