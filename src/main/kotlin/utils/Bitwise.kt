package utils

object Bitwise {

    fun setBit(index: Int, mask: Int): Int {
        return mask or (1 shl index)
    }

    fun hasBit(index: Int, mask: Int): Boolean {
        return (mask shr index) and 1 == 1
    }
}