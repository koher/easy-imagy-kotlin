package org.koherent.image

data class RGBA<Value>(val red: Value, val green: Value, val blue: Value, val alpha: Value) {
    constructor(gray: Value, alpha: Value): this(gray, gray, gray, alpha)
}

val RGBA<Byte>.redInt: Int
    get() = red.toInt() and 0xff

val RGBA<Byte>.greenInt: Int
    get() = green.toInt() and 0xff

val RGBA<Byte>.blueInt: Int
    get() = blue.toInt() and 0xff

val RGBA<Byte>.alphaInt: Int
    get() = alpha.toInt() and 0xff

val RGBA<Byte>.grayInt: Int
    get() = (redInt + greenInt + blueInt) / 3

val RGBA<Byte>.gray: Byte
    get() = grayInt.toByte()

val RGBA<Int>.gray: Int
    get() = (red + green + blue) / 3

val RGBA<Float>.gray: Float
    get() = (red + green + blue) / 3

val RGBA<Double>.gray: Double
    get() = (red + green + blue) / 3
