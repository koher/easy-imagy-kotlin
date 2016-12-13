package org.koherent.image

import org.koherent.image.ConvolutionType.*

sealed class ConvolutionType<I, W, O> {
    object BII : ConvolutionType<Byte, Int, Int>()
    object BFB : ConvolutionType<Byte, Float, Byte>()
    object BDB : ConvolutionType<Byte, Double, Byte>()
    object III : ConvolutionType<Int, Int, Int>()
    object IFI : ConvolutionType<Int, Float, Int>()
    object IDI : ConvolutionType<Int, Double, Int>()
    object FFF : ConvolutionType<Float, Float, Float>()
    object FDF : ConvolutionType<Float, Double, Float>()
    object DDD : ConvolutionType<Double, Double, Double>()
    object B4II4 : ConvolutionType<RGBA<Byte>, Int, RGBA<Int>>()
    object B4FB4 : ConvolutionType<RGBA<Byte>, Float, RGBA<Byte>>()
    object B4DB4 : ConvolutionType<RGBA<Byte>, Double, RGBA<Byte>>()
    object I4II4 : ConvolutionType<RGBA<Int>, Int, RGBA<Int>>()
    object I4FI4 : ConvolutionType<RGBA<Int>, Float, RGBA<Int>>()
    object I4DI4 : ConvolutionType<RGBA<Int>, Double, RGBA<Int>>()
    object F4FF4 : ConvolutionType<RGBA<Float>, Float, RGBA<Float>>()
    object F4DF4 : ConvolutionType<RGBA<Float>, Double, RGBA<Float>>()
    object D4DD4 : ConvolutionType<RGBA<Double>, Double, RGBA<Double>>()
}

fun Image<Byte>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<Byte>, type: BII = BII): Image<Int> {
    return convoluted<Byte, Int, Int, Int>(
            kernel,
            extrapolation,
            { a, b -> (a.toInt() and 0xff) * b },
            0,
            { a, b -> a + b },
            { it }
    )
}

fun Image<Byte>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<Byte>, typed: BFB = BFB): Image<Byte> {
    return convoluted<Byte, Float, Float, Byte>(
            kernel,
            extrapolation,
            { a, b -> (a.toInt() and 0xff) * b },
            0.0f,
            { a, b -> a + b },
            { it.toByte() }
    )
}

fun Image<Byte>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Byte>, type: BDB = BDB): Image<Byte> {
    return convoluted<Byte, Double, Double, Byte>(
            kernel,
            extrapolation,
            { a, b -> (a.toInt() and 0xff) * b },
            0.0,
            { a, b -> a + b },
            { it.toByte() }
    )
}

fun Image<Int>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<Int>, type: III = III): Image<Int> {
    return convoluted<Int, Int, Int, Int>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0,
            { a, b -> a + b },
            { it }
    )
}

fun Image<Int>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<Int>, type: IFI = IFI): Image<Int> {
    return convoluted<Int, Float, Float, Int>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0f,
            { a, b -> a + b },
            { it.toInt() }
    )
}

fun Image<Int>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Int>, type: IDI = IDI): Image<Int> {
    return convoluted<Int, Double, Double, Int>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            { it.toInt() }
    )
}

fun Image<Float>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<Float>, type: FFF = FFF): Image<Float> {
    return convoluted<Float, Float, Float, Float>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0f,
            { a, b -> a + b },
            { it }
    )
}

fun Image<Float>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Float>, type: FDF = FDF): Image<Float> {
    return convoluted<Float, Double, Double, Float>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            { it.toFloat() }
    )
}

fun Image<Double>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<Double>, type: DDD = DDD): Image<Double> {
    return convoluted<Double, Double, Double, Double>(
            kernel,
            extrapolation,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            { it }
    )
}

fun Image<RGBA<Byte>>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<RGBA<Byte>>, type: B4II4 = B4II4): Image<RGBA<Int>> {
    return convoluted<RGBA<Byte>, Int, RGBA<Int>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_bi b },
            RGBA(0, 0, 0, 0),
            { a, b -> a plus_ii b },
            { it }
    )
}

fun Image<RGBA<Byte>>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<RGBA<Byte>>, type: B4FB4 = B4FB4): Image<RGBA<Byte>> {
    return convoluted<RGBA<Byte>, Float, RGBA<Float>, RGBA<Byte>>(
            kernel,
            extrapolation,
            { a, b -> a times_bf b },
            RGBA(0.0f, 0.0f, 0.0f, 0.0f),
            { a, b -> a plus_ff b },
            { RGBA(it.red.toByte(), it.green.toByte(), it.blue.toByte(), it.alpha.toByte()) }
    )
}

fun Image<RGBA<Byte>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Byte>>, type: B4DB4 = B4DB4): Image<RGBA<Byte>> {
    return convoluted<RGBA<Byte>, Double, RGBA<Double>, RGBA<Byte>>(
            kernel,
            extrapolation,
            { a, b -> a times_bd b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { RGBA(it.red.toByte(), it.green.toByte(), it.blue.toByte(), it.alpha.toByte()) }
    )
}

fun Image<RGBA<Int>>.convoluted(kernel: Image<Int>, extrapolation: Extrapolation<RGBA<Int>>, type: I4II4 = I4II4): Image<RGBA<Int>> {
    return convoluted<RGBA<Int>, Int, RGBA<Int>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_ii b },
            RGBA(0, 0, 0, 0),
            { a, b -> a plus_ii b },
            { it }
    )
}

fun Image<RGBA<Int>>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<RGBA<Int>>, type: I4FI4 = I4FI4): Image<RGBA<Int>> {
    return convoluted<RGBA<Int>, Float, RGBA<Float>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_if b },
            RGBA(0.0f, 0.0f, 0.0f, 0.0f),
            { a, b -> a plus_ff b },
            { RGBA(it.red.toInt(), it.green.toInt(), it.blue.toInt(), it.alpha.toInt()) }
    )
}

fun Image<RGBA<Int>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Int>>, type: I4DI4 = I4DI4): Image<RGBA<Int>> {
    return convoluted<RGBA<Int>, Double, RGBA<Double>, RGBA<Int>>(
            kernel,
            extrapolation,
            { a, b -> a times_id b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { RGBA(it.red.toInt(), it.green.toInt(), it.blue.toInt(), it.alpha.toInt()) }
    )
}

fun Image<RGBA<Float>>.convoluted(kernel: Image<Float>, extrapolation: Extrapolation<RGBA<Float>>, type: F4FF4 = F4FF4): Image<RGBA<Float>> {
    return convoluted<RGBA<Float>, Float, RGBA<Float>, RGBA<Float>>(
            kernel,
            extrapolation,
            { a, b -> a times_ff b },
            RGBA(0.0f, 0.0f, 0.0f, 0.0f),
            { a, b -> a plus_ff b },
            { it }
    )
}

fun Image<RGBA<Float>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Float>>, type: F4DF4 = F4DF4): Image<RGBA<Float>> {
    return convoluted<RGBA<Float>, Double, RGBA<Double>, RGBA<Float>>(
            kernel,
            extrapolation,
            { a, b -> a times_fd b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { RGBA(it.red.toFloat(), it.green.toFloat(), it.blue.toFloat(), it.alpha.toFloat()) }
    )
}

fun Image<RGBA<Double>>.convoluted(kernel: Image<Double>, extrapolation: Extrapolation<RGBA<Double>>, type: D4DD4 = D4DD4): Image<RGBA<Double>> {
    return convoluted<RGBA<Double>, Double, RGBA<Double>, RGBA<Double>>(
            kernel,
            extrapolation,
            { a, b -> a times_dd b },
            RGBA(0.0, 0.0, 0.0, 0.0),
            { a, b -> a plus_dd b },
            { it }
    )
}
