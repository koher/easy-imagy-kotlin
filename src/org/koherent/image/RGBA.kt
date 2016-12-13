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

internal infix fun RGBA<Int>.plus_ii(other: RGBA<Int>): RGBA<Int> {
    return RGBA(red + other.red, green + other.green, blue + other.blue, alpha + other.alpha)
}

internal infix fun RGBA<Float>.plus_ff(other: RGBA<Float>): RGBA<Float> {
    return RGBA(red + other.red, green + other.green, blue + other.blue, alpha + other.alpha)
}

internal infix fun RGBA<Double>.plus_dd(other: RGBA<Double>): RGBA<Double> {
    return RGBA(red + other.red, green + other.green, blue + other.blue, alpha + other.alpha)
}

internal infix fun RGBA<Byte>.times_bi(k: Int): RGBA<Int> {
    return RGBA(redInt * k, greenInt * k, blueInt * k, alphaInt * k)
}

internal infix fun RGBA<Byte>.times_bf(k: Float): RGBA<Float> {
    return RGBA(redInt * k, greenInt * k, blueInt * k, alphaInt * k)
}

internal infix fun RGBA<Byte>.times_bd(k: Double): RGBA<Double> {
    return RGBA(redInt * k, greenInt * k, blueInt * k, alphaInt * k)
}

internal infix fun RGBA<Int>.times_ii(k: Int): RGBA<Int> {
    return RGBA(red * k, green * k, blue * k, alpha * k)
}

internal infix fun RGBA<Int>.times_if(k: Float): RGBA<Float> {
    return RGBA(red * k, green * k, blue * k, alpha * k)
}

internal infix fun RGBA<Int>.times_id(k: Double): RGBA<Double> {
    return RGBA(red * k, green * k, blue * k, alpha * k)
}

internal infix fun RGBA<Float>.times_ff(k: Float): RGBA<Float> {
    return RGBA(red * k, green * k, blue * k, alpha * k)
}

internal infix fun RGBA<Float>.times_fd(k: Double): RGBA<Double> {
    return RGBA(red * k, green * k, blue * k, alpha * k)
}

internal infix fun RGBA<Double>.times_dd(k: Double): RGBA<Double> {
    return RGBA(red * k, green * k, blue * k, alpha * k)
}
