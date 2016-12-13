package org.koherent.image

import java.util.*

class Image<Pixel>(val width: Int, val height: Int, val pixels: Array<Pixel>) : Iterable<Pixel> {
    @Suppress("NOTHING_TO_INLINE")
    internal inline fun isValidX(x: Int): Boolean {
        return 0 <= x && x < width
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun isValidY(y: Int): Boolean {
        return 0 <= y && y < height
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun pixelIndex(x: Int, y: Int): Int {
        return y * width + x
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun safePixelIndex(x: Int, y: Int): Int {
        assert(isValidX(x)) { "`x` is out of bounds: $x" }
        assert(isValidY(y)) { "`y` is out of bounds: $y" }
        return pixelIndex(x, y)
    }

    operator fun get(x: Int, y: Int): Pixel {
        return pixels[safePixelIndex(x, y)]
    }

    operator fun set(x: Int, y: Int, pixel: Pixel) {
        pixels[safePixelIndex(x, y)] = pixel
    }

    fun pixel(x: Int, y: Int): Pixel? {
        if (!isValidX(x)) return null
        if (!isValidY(y)) return null
        return pixels[pixelIndex(x, y)]
    }

    fun pixel(x: Int, y: Int, extrapolation: Extrapolation<Pixel>): Pixel {
        return pixel(x, y) ?: when (extrapolation) {
            is Extrapolation.Constant -> extrapolation.value
            is Extrapolation.Replicate -> this[clamp(x, 0, width), clamp(y, 0, height)]
        }
    }

    val size: Int
        get() = width * height

    override fun iterator(): Iterator<Pixel> {
        return pixels.iterator()
    }

    override fun equals(other: Any?): Boolean {
        val image = other as? Image<*>
        image ?: return false
        return width == image.width && height == image.height && Arrays.equals(pixels, image.pixels)
    }

    override fun hashCode(): Int {
        return pixels.hashCode()
    }
}

inline operator fun <reified Pixel> Image<Pixel>.get(xRange: IntRange, yRange: IntRange): Image<Pixel> {
    val pixels = mutableListOf<Pixel>()
    for (y in yRange) {
        for (x in xRange) {
            pixels.add(this[x, y])
        }
    }
    return Image(xRange.endInclusive - xRange.start + 1, yRange.endInclusive - yRange.start + 1, pixels.toTypedArray())
}

inline fun <Pixel, reified T> Image<Pixel>.map(transform: (Pixel) -> T): Image<T> {
    return Image<T>(width, height, pixels.map(transform).toTypedArray())
}

inline fun <Pixel> Image<Pixel>.update(transform: (Pixel) -> Pixel) {
    for (i in 0..size) {
        pixels[i] = transform(pixels[i])
    }
}

inline fun <Pixel, W, T, reified R> Image<Pixel>.convoluted(kernel: Image<W>, extrapolation: Extrapolation<Pixel>, multiply: (Pixel, W) -> T, zero: T, add: (T, T) -> T, convert: (T) -> R): Image<R> {
    assert(kernel.width > 0) { "The `width` of the `kernel` must be positive: ${kernel.width}" }
    assert(kernel.height > 0) { "The `height` of the `kernel` must be positive: ${kernel.height}" }

    val hw = kernel.width / 2  // halfWidth
    val hh = kernel.height / 2 // halfHeight

    val pixels = mutableListOf<R>()

    for (y in 0 until height) {
        for (x in 0 until width) {
            var sum = zero
            for (fy in 0 until kernel.height) {
                for (fx in 0 until kernel.width) {
                    val dx = fx - hw
                    val dy = fy - hh

                    val weight = kernel[fx, fy]
                    val value = pixel(x + dx, y + dy, extrapolation)

                    sum = add(sum, multiply(value, weight))
                }
            }
            pixels.add(convert(sum))
        }
    }

    return Image(width, height, pixels.toTypedArray())
}

inline fun <reified Pixel> Image<Pixel>.xFlipped(): Image<Pixel> {
    val pixels = mutableListOf<Pixel>()

    val  maxX = width - 1
    for (y in 0 until height) {
        for (x in 0 until width) {
            pixels.add(this[maxX - x, y])
        }
    }

    return Image(width, height, pixels.toTypedArray())
}

inline fun <reified Pixel> Image<Pixel>.yFlipped(): Image<Pixel> {
    val pixels = mutableListOf<Pixel>()

    val  maxY = height - 1
    for (y in 0 until height) {
        for (x in 0 until width) {
            pixels.add(this[x, maxY - y])
        }
    }

    return Image(width, height, pixels.toTypedArray())
}

inline fun <reified Pixel> Image<Pixel>.rotated(times: Int): Image<Pixel> {
    return when (times % 4) {
        0 -> {
            Image(width, height, pixels.clone())
        }
        1, -3 -> {
            val pixels = mutableListOf<Pixel>()

            val maxX = height - 1
            for (y in 0 until width) {
                for (x in 0 until height) {
                    pixels.add(this[y, maxX - x])
                }
            }

            Image(height, width, pixels.toTypedArray())
        }
        2, -2 -> {
            val pixels = mutableListOf<Pixel>()

            val  maxX = width - 1
            val  maxY = height - 1
            for (y in 0 until height) {
                for (x in 0 until width) {
                    pixels.add(this[maxX - x, maxY - y])
                }
            }

            Image(width, height, pixels.toTypedArray())
        }
        3, -1 -> {
            val pixels = mutableListOf<Pixel>()

            val  maxY = height - 1
            for (y in 0 until width) {
                for (x in 0 until height) {
                    pixels.add(this[maxY - y, x])
                }
            }

            Image(height, width, pixels.toTypedArray())
        }
        else -> throw Error("Never reaches here.")
    }
}

private fun clamp(x: Int, min: Int, max: Int): Int {
    return Math.max(Math.min(x, max), min)
}
