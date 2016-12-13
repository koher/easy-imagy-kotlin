package org.koherent.image

fun Image<Byte>.convolutedBbb(kernel: Image<Byte>): Image<Byte> {
    return convoluted<Byte, Byte, Int, Byte>(
            kernel,
            { a, b -> (a.toInt() and 0xff) * (b.toInt() and 0xff) },
            0,
            { a, b -> a + b },
            { a, b -> (a / b).toByte() }
    )
}

fun Image<Byte>.convolutedBib(kernel: Image<Int>): Image<Byte> {
    return convoluted<Byte, Int, Int, Byte>(
            kernel,
            { a, b -> (a.toInt() and 0xff) * b },
            0,
            { a, b -> a + b },
            { a, b -> (a / b).toByte() }
    )
}

fun Image<Byte>.convolutedBfb(kernel: Image<Float>): Image<Byte> {
    return convoluted<Byte, Float, Float, Byte>(
            kernel,
            { a, b -> (a.toInt() and 0xff) * b },
            0.0f,
            { a, b -> a + b },
            { a, b -> (a / b).toByte() }
    )
}

fun Image<Byte>.convolutedBdb(kernel: Image<Double>): Image<Byte> {
    return convoluted<Byte, Double, Double, Byte>(
            kernel,
            { a, b -> (a.toInt() and 0xff) * b },
            0.0,
            { a, b -> a + b },
            { a, b -> (a / b).toByte() }
    )
}

fun Image<Int>.convolutedIii(kernel: Image<Int>): Image<Int> {
    return convoluted<Int, Int, Int, Int>(
            kernel,
            { a, b -> a * b },
            0,
            { a, b -> a + b },
            { a, b -> a / b }
    )
}

fun Image<Int>.convolutedIfi(kernel: Image<Float>): Image<Int> {
    return convoluted<Int, Float, Float, Int>(
            kernel,
            { a, b -> a * b },
            0.0f,
            { a, b -> a + b },
            { a, b -> (a / b).toInt() }
    )
}

fun Image<Int>.convolutedIdi(kernel: Image<Double>): Image<Int> {
    return convoluted<Int, Double, Double, Int>(
            kernel,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            { a, b -> (a / b).toInt() }
    )
}

fun Image<Float>.convolutedFff(kernel: Image<Float>): Image<Float> {
    return convoluted<Float, Float, Float, Float>(
            kernel,
            { a, b -> a * b },
            0.0f,
            { a, b -> a + b },
            { a, b -> a / b }
    )
}

fun Image<Float>.convolutedFdf(kernel: Image<Double>): Image<Float> {
    return convoluted<Float, Double, Double, Float>(
            kernel,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            { a, b -> (a / b).toFloat() }
    )
}

fun Image<Double>.convolutedDdd(kernel: Image<Double>): Image<Double> {
    return convoluted<Double, Double, Double, Double>(
            kernel,
            { a, b -> a * b },
            0.0,
            { a, b -> a + b },
            { a, b -> a / b }
    )
}
