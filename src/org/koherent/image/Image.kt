package org.koherent.image

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
        return pixels[safePixelIndex(y, x)]
    }

    operator fun set(x: Int, y: Int, pixel: Pixel) {
        pixels[safePixelIndex(x, y)] = pixel
    }

    fun pixel(x: Int, y: Int): Pixel? {
        if (!isValidX(x)) return null
        if (!isValidY(y)) return null
        return pixels[pixelIndex(x, y)]
    }

    val size: Int
        get() = width * height

    override fun iterator(): Iterator<Pixel> {
        return pixels.iterator()
    }

    override fun equals(other: Any?): Boolean {
        val image = other as? Image<*>
        image ?: return false
        if (width != image.width || height != image.height) return false
        pixels.zip(image.pixels) { a, b -> if (a != b)  return false }
        return true
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

inline fun <Pixel, W, reified T> Image<Pixel>.convoluted(kernel: Image<W>, multiply: (Pixel, W) -> T, zero: T, add: (T, T) -> T, divide: (T, Int) -> T): Image<T> {
    assert(kernel.width % 2 == 1) { "The `width` of the `kernel` must be odd: ${kernel.width}" }
    assert(kernel.height % 2 == 1) { "The `height` of the `kernel` must be odd: ${kernel.height}" }

    val hw = kernel.width / 2  // halfWidth
    val hh = kernel.height / 2 // halfHeight

    val pixels = mutableListOf<T>()

    for (y in 0..height) {
        for (x in 0..width) {
            var sum = zero
            var count = 0
            for (fy in 0..kernel.height) {
                for (fx in 0..kernel.width) {
                    val dx = fx - hw
                    val dy = fy - hh

                    val x2 = x + dx
                    val y2 = y + dy

                    if (x2 < 0 || width <= x2) continue
                    if (y2 < 0 || height <= y2) continue

                    val weight = kernel[fx, fy]
                    val value = this.pixels[y2 * width + x2]

                    sum = add(sum, multiply(value, weight))
                    count++
                }
            }
            pixels.add(divide(sum, count))
        }
    }

    return Image<T>(width, height, pixels.toTypedArray())
}

inline fun <reified Pixel> Image<Pixel>.xFlipped(): Image<Pixel> {
    val pixels = mutableListOf<Pixel>()

    val  maxX = width - 1
    for (y in 0..height) {
        for (x in 0..width) {
            pixels.add(this.pixels[y * width + (maxX - x)])
        }
    }

    return Image(width, height, pixels.toTypedArray())
}

inline fun <reified Pixel> Image<Pixel>.yFlipped(): Image<Pixel> {
    val pixels = mutableListOf<Pixel>()

    val  maxY = height - 1
    for (y in 0..height) {
        for (x in 0..width) {
            pixels.add(this.pixels[(maxY - y) * width + x])
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
            for (y in 0..width) {
                for (x in 0..height) {
                    pixels.add(this.pixels[(maxX - x) * width + y])
                }
            }

            Image(height, width, pixels.toTypedArray())
        }
        2, -2 -> {
            val pixels = mutableListOf<Pixel>()

            val  maxX = width - 1
            val  maxY = height - 1
            for (y in 0..height) {
                for (x in 0..width) {
                    pixels.add(this.pixels[(maxY - y) * width + (maxX - x)])
                }
            }

            Image(width, height, pixels.toTypedArray())
        }
        3, -1 -> {
            val pixels = mutableListOf<Pixel>()

            val  maxY = height - 1
            for (y in 0..width) {
                for (x in 0..height) {
                    pixels.add(this.pixels[x * width + (maxY - y)])
                }
            }

            Image(height, width, pixels.toTypedArray())
        }
        else -> throw Error("Never reaches here.")
    }
}
