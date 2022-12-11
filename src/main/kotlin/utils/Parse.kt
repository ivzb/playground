package utils

object Parse {

    fun String.firstInt(delimiter: Char = ' '): Int =
        this.split(delimiter).firstNotNullOf { it.toIntOrNull() }

    fun String.firstLong(delimiter: Char = ' '): Long =
        this.split(delimiter).firstNotNullOf { it.toLongOrNull() }
}