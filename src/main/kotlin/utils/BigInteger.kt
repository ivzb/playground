package utils

import java.math.BigInteger
import kotlin.math.ceil

fun BigInteger.length() = ceil(bitLength() / 3.32192809).toInt()