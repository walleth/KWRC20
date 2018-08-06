fun ByteArray.toI32(): Int {
    return (this[0].toInt()) or
            (this[1].toInt().shl(8)) or
            (this[2].toInt().shl(16)) or
            (this[3].toInt().shl(24))
}

fun Int.toByteArray() = ByteArray(32) { currentPosition ->
    shr((currentPosition) * 8).toByte()
}

