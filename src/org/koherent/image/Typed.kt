package org.koherent.image

sealed class ParameterType {
    object RGBAByte : ParameterType()
    object Byte : ParameterType()
}

@JvmName("scaledImageOfByte") fun Image<Byte>.scaled(width: Int, height: Int, interpolation: Interpolation, extrapolation: Extrapolation<Byte> = Extrapolation.Replicate): Image<Byte> {
    return scaled<Byte, Double, Byte>(
            width,
            height,
            interpolation,
            extrapolation,
            { a, b -> (a.toInt() and 0xff) * b },
            { a, b -> a + b },
            { it.toByte() }
    )
}

@JvmName("scaledImageOfInt") fun Image<Int>.scaled(width: Int, height: Int, interpolation: Interpolation, extrapolation: Extrapolation<Int> = Extrapolation.Replicate): Image<Int> {
    return scaled<Int, Double, Int>(
            width,
            height,
            interpolation,
            extrapolation,
            { a, b -> a * b },
            { a, b -> a + b },
            { it.toInt() }
    )
}

@JvmName("scaledImageOfFloat") fun Image<Float>.scaled(width: Int, height: Int, interpolation: Interpolation, extrapolation: Extrapolation<Float> = Extrapolation.Replicate): Image<Float> {
    return scaled<Float, Double, Float>(
            width,
            height,
            interpolation,
            extrapolation,
            { a, b -> a * b },
            { a, b -> a + b },
            { it.toFloat() }
    )
}

@JvmName("scaledImageOfDouble") fun Image<Double>.scaled(width: Int, height: Int, interpolation: Interpolation, extrapolation: Extrapolation<Double> = Extrapolation.Replicate): Image<Double> {
    return scaled<Double, Double, Double>(
            width,
            height,
            interpolation,
            extrapolation,
            { a, b -> a * b },
            { a, b -> a + b },
            { it }
    )
}

@JvmName("convolutedFromByteViaIntToInt") fun Image<Byte>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<Byte>): Image<Int> {
    return convoluted<Byte, Int, Int, Int>(
            kernel,
            extrapolation,
            { a, b -> (a.toInt() and 0xff) * b },
            0,
            { a, b -> a + b },
            { it }
    )
}

@JvmName("convolutedFromByteViaFloatToByte") fun Image<Byte>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<Byte>): Image<Byte> {
    return convoluted<Byte, Float, Float, Byte>(
            kernel,
            extrapolation,
            { a, b -> (a.toInt() and 0xff) * b },
            0.0f,
            { a, b -> a + b },
            Float::toByte
    )
}

@JvmName("convolutedFromByteViaDoubleToByte") fun Image<Byte>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Byte>): Image<Byte> {
    return convoluted<Byte, Double, Double, Byte>(
            kernel,
            extrapolation,
            { a, b -> (a.toInt() and 0xff) * b },
            0.0,
            { a, b -> a + b },
            Double::toByte
    )
}

@JvmName("convolutedFromIntViaIntToInt") fun Image<Int>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<Int>): Image<Int> {
    return convoluted<Int, Int, Int, Int>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0,
            { a, b -> a + b },
            { it }
    )
}

@JvmName("convolutedFromIntViaFloatToInt") fun Image<Int>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<Int>): Image<Int> {
    return convoluted<Int, Float, Float, Int>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0f,
            { a, b -> a + b },
            Float::toInt
    )
}

@JvmName("convolutedFromIntViaDoubleToInt") fun Image<Int>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Int>): Image<Int> {
    return convoluted<Int, Double, Double, Int>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            Double::toInt
    )
}

@JvmName("convolutedFromFloatViaFloatToFloat") fun Image<Float>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<Float>): Image<Float> {
    return convoluted<Float, Float, Float, Float>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0f,
            { a, b -> a + b },
            { it }
    )
}

@JvmName("convolutedFromFloatViaDoubleToFloat") fun Image<Float>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Float>): Image<Float> {
    return convoluted<Float, Double, Double, Float>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            Double::toFloat
    )
}

@JvmName("convolutedFromDoubleViaDoubleToDouble") fun Image<Double>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Double>): Image<Double> {
    return convoluted<Double, Double, Double, Double>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            { it }
    )
}

@JvmName("convolutedFromRGBAByteViaIntToRGBAInt") fun Image<RGBA<Byte>>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<RGBA<Byte>>): Image<RGBA<Int>> {
    return convoluted<RGBA<Byte>, Int, RGBA<Int>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_bi b },
            RGBA(0, 0, 0, 0),
            { a, b -> a plus_ii b },
            { it }
    )
}

@JvmName("convolutedFromRGBABViaFloatToRGBAByte") fun Image<RGBA<Byte>>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<RGBA<Byte>>): Image<RGBA<Byte>> {
    return convoluted<RGBA<Byte>, Float, RGBA<Float>, RGBA<Byte>>(
            kernel,
            extrapolation,
            { a, b -> a times_bf b },
            RGBA(0.0f, 0.0f, 0.0f, 0.0f),
            { a, b -> a plus_ff b },
            { RGBA(it.red.toByte(), it.green.toByte(), it.blue.toByte(), it.alpha.toByte()) }
    )
}

@JvmName("convolutedFromRGBAByteViaDoubleToRGBAByte") fun Image<RGBA<Byte>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Byte>>): Image<RGBA<Byte>> {
    return convoluted<RGBA<Byte>, Double, RGBA<Double>, RGBA<Byte>>(
            kernel,
            extrapolation,
            { a, b -> a times_bd b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { RGBA(it.red.toByte(), it.green.toByte(), it.blue.toByte(), it.alpha.toByte()) }
    )
}

@JvmName("convolutedFromRGBAIntViaIntToRGBAInt") fun Image<RGBA<Int>>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<RGBA<Int>>): Image<RGBA<Int>> {
    return convoluted<RGBA<Int>, Int, RGBA<Int>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_ii b },
            RGBA(0, 0, 0, 0),
            { a, b -> a plus_ii b },
            { it }
    )
}

@JvmName("convolutedFromRGBAIntViaFloatToRGBAInt") fun Image<RGBA<Int>>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<RGBA<Int>>): Image<RGBA<Int>> {
    return convoluted<RGBA<Int>, Float, RGBA<Float>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_if b },
            RGBA(0.0f, 0.0f, 0.0f, 0.0f),
            { a, b -> a plus_ff b },
            { RGBA(it.red.toInt(), it.green.toInt(), it.blue.toInt(), it.alpha.toInt()) }
    )
}

@JvmName("convolutedFromRGBAIntViaDoubleToRGBAInt") fun Image<RGBA<Int>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Int>>): Image<RGBA<Int>> {
    return convoluted<RGBA<Int>, Double, RGBA<Double>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_id b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { RGBA(it.red.toInt(), it.green.toInt(), it.blue.toInt(), it.alpha.toInt()) }
    )
}

@JvmName("convolutedFromRGBAFloatViaFloatToRGBAFloat") fun Image<RGBA<Float>>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<RGBA<Float>>): Image<RGBA<Float>> {
    return convoluted<RGBA<Float>, Float, RGBA<Float>, RGBA<Float>>(
            kernel,
            extrapolation,
            { a, b -> a times_ff b },
            RGBA(0.0f, 0.0f, 0.0f, 0.0f),
            { a, b -> a plus_ff b },
            { it }
    )
}

@JvmName("convolutedFromRGBAFloatViaDoubleToRGBAFloat") fun Image<RGBA<Float>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Float>>): Image<RGBA<Float>> {
    return convoluted<RGBA<Float>, Double, RGBA<Double>, RGBA<Float>>(
            kernel,
            extrapolation,
            { a, b -> a times_fd b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { RGBA(it.red.toFloat(), it.green.toFloat(), it.blue.toFloat(), it.alpha.toFloat()) }
    )
}

@JvmName("convolutedFromRGBADoubleViaDoubleToRGBADouble") fun Image<RGBA<Double>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Double>>): Image<RGBA<Double>> {
    return convoluted<RGBA<Double>, Double, RGBA<Double>, RGBA<Double>>(
            kernel,
            extrapolation,
            { a, b -> a times_dd b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { it }
    )
}
