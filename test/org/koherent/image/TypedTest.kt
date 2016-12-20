package org.koherent.image

import org.testng.Assert.*
import org.testng.annotations.Test

class TypedTest {
    @Test
    fun scaledImageOfByte() {
        val image = Image<Byte>(2, 2, arrayOf(0, 48, 96, 144.toByte()))
        run {
            val result = image.scaled(1, 1, Interpolation.Bilinear)
            assertEquals(result, Image<Byte>(1, 1, arrayOf(72)))
        }
        run {
            val result = image.scaled(2, 2, Interpolation.Bilinear)
            assertEquals(result, Image<Byte>(1, 1, arrayOf(0, 48, 96, 144.toByte())))
        }
        run {
            val result = image.scaled(4, 4, Interpolation.Bilinear)
            assertEquals(result, Image<Byte>(4, 4, arrayOf(0, 12, 36, 48, 24, 36, 60, 72, 72, 84, 108, 120, 96, 108, 132.toByte(), 144.toByte())))
        }
    }

    @Test
    fun scaledImageOfInt() {
        val image = Image<Int>(2, 2, arrayOf(0, 48, 96, 144))
        run {
            val result = image.scaled(1, 1, Interpolation.Bilinear)
            assertEquals(result, Image<Int>(1, 1, arrayOf(72)))
        }
        run {
            val result = image.scaled(2, 2, Interpolation.Bilinear)
            assertEquals(result, Image<Int>(1, 1, arrayOf(0, 48, 96, 144)))
        }
        run {
            val result = image.scaled(4, 4, Interpolation.Bilinear)
            assertEquals(result, Image<Int>(4, 4, arrayOf(0, 12, 36, 48, 24, 36, 60, 72, 72, 84, 108, 120, 96, 108, 132, 144)))
        }
    }

    @Test
    fun scaledImageOfFloat() {
        val image = Image<Float>(2, 2, arrayOf(0.0f, 48.0f, 96.0f, 144.0f))
        run {
            val result = image.scaled(1, 1, Interpolation.Bilinear)
            assertEquals(result, Image<Float>(1, 1, arrayOf(72.0f)))
        }
        run {
            val result = image.scaled(2, 2, Interpolation.Bilinear)
            assertEquals(result, Image<Float>(1, 1, arrayOf(0.0f, 48.0f, 96.0f, 144.0f)))
        }
        run {
            val result = image.scaled(4, 4, Interpolation.Bilinear)
            assertEquals(result, Image<Float>(4, 4, arrayOf(0.0f, 12.0f, 36.0f, 48.0f, 24.0f, 36.0f, 60.0f, 72.0f, 72.0f, 84.0f, 108.0f, 120.0f, 96.0f, 108.0f, 132.0f, 144.0f)))
        }
    }

    @Test
    fun scaledImageOfDouble() {
        val image = Image<Double>(2, 2, arrayOf(0.0, 48.0, 96.0, 144.0))
        run {
            val result = image.scaled(1, 1, Interpolation.Bilinear)
            assertEquals(result, Image<Double>(1, 1, arrayOf(72.0)))
        }
        run {
            val result = image.scaled(2, 2, Interpolation.Bilinear)
            assertEquals(result, Image<Double>(1, 1, arrayOf(0.0, 48.0, 96.0, 144.0)))
        }
        run {
            val result = image.scaled(4, 4, Interpolation.Bilinear)
            assertEquals(result, Image<Double>(4, 4, arrayOf(0.0, 12.0, 36.0, 48.0, 24.0, 36.0, 60.0, 72.0, 72.0, 84.0, 108.0, 120.0, 96.0, 108.0, 132.0, 144.0)))
        }
    }

    @Test
    fun convolutedIii() {
        val image = Image<Int>(3, 3, arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
        val kernel = Image<Int>(3, 3, arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
        val result = image.convoluted(kernel, Extrapolation.Constant(0))
        assertEquals(result, Image<Int>(3, 3, arrayOf(94, 154, 106, 186, 285, 186, 106, 154, 94)))
    }
}