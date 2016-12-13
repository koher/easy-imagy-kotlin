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
