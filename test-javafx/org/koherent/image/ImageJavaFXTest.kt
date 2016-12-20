package org.koherent.image

import org.testng.annotations.Test
import org.testng.Assert.*
import java.io.File

class ImageJavaFXText {
    val epsilon = 1.0e-5

    @Test
    fun testRgbaImage() {
        run {
            val image: Image<RGBA<Byte>> = javafx.scene.image.Image(File("res/test-2x1.png").inputStream()).toEasyImage(ParameterType.RGBAByte)
            assertEquals(image.width, 2)
            assertEquals(image.height, 1)

            assertEquals(image[0, 0].redInt, 255)
            assertEquals(image[0, 0].greenInt, 255)
            assertEquals(image[0, 0].blueInt, 255)
            assertEquals(image[0, 0].alphaInt, 127)

            assertEquals(image[1, 0].redInt, 0)
            assertEquals(image[1, 0].greenInt, 0)
            assertEquals(image[1, 0].blueInt, 0)
            assertEquals(image[1, 0].alphaInt, 255)
        }

        run {
            val image: Image<RGBA<Byte>> = javafx.scene.image.Image(File("res/test-2x2.png").inputStream()).toEasyImage(ParameterType.RGBAByte)
            assertEquals(image.width, 2)
            assertEquals(image.height, 2)

            assertEquals(image[0, 0].redInt, 255)
            assertEquals(image[0, 0].greenInt, 0)
            assertEquals(image[0, 0].blueInt, 0)
            assertEquals(image[0, 0].alphaInt, 64)

            assertEquals(image[1, 0].redInt, 0)
            assertEquals(image[1, 0].greenInt, 255)
            assertEquals(image[1, 0].blueInt, 0)
            assertEquals(image[1, 0].alphaInt, 127)

            assertEquals(image[0, 1].redInt, 0)
            assertEquals(image[0, 1].greenInt, 0)
            assertEquals(image[0, 1].blueInt, 255)
            assertEquals(image[0, 1].alphaInt, 191)

            assertEquals(image[1, 1].redInt, 255)
            assertEquals(image[1, 1].greenInt, 255)
            assertEquals(image[1, 1].blueInt, 0)
            assertEquals(image[1, 1].alphaInt, 255)
        }
    }

    @Test
    fun testGrayImage() {
        run {
            val image: Image<Byte> = javafx.scene.image.Image(File("res/test-2x1.png").inputStream()).toEasyImage(ParameterType.Byte)
            assertEquals(image.width, 2)
            assertEquals(image.height, 1)

            assertEquals(image[0, 0].toInt() and 0xff, 127)
            assertEquals(image[1, 0].toInt() and 0xff, 0)
        }

        run {
            val image: Image<Byte> = javafx.scene.image.Image(File("res/test-2x2.png").inputStream()).toEasyImage(ParameterType.Byte)
            assertEquals(image.width, 2)
            assertEquals(image.height, 2)

            assertEquals(image[0, 0].toInt() and 0xff, 21)
            assertEquals(image[1, 0].toInt() and 0xff, 42)
            assertEquals(image[0, 1].toInt() and 0xff, 63)
            assertEquals(image[1, 1].toInt() and 0xff, 170)
        }
    }

    @Test
    fun toFXImage() {
        run { // RGBA<Byte>
            val image = Image<RGBA<Byte>>(2, 2, arrayOf<RGBA<Byte>>(
                    RGBA(0, 1, 2, 3),
                    RGBA(4, 5, 6, 7),
                    RGBA(126, 127, -128, -127),
                    RGBA(-4, -3, -2, -1)
            ))

            val fxImage = image.toFXImage()

            fun premultiplyAndRestore(value: Int, alpha: Int): Int {
                val premultiplied = Math.round(value * alpha / 255.0)
                val restored = Math.round(premultiplied * 255.0 / alpha)
                return restored.toInt()
            }

            fxImage.pixelReader.getColor(0, 0).apply {
                assertEquals(red,     premultiplyAndRestore(0, 3) / 255.0, epsilon)
                assertEquals(green,   premultiplyAndRestore(1, 3) / 255.0, epsilon)
                assertEquals(blue,    premultiplyAndRestore(2, 3) / 255.0, epsilon)
                assertEquals(opacity, 3.0 / 255.0, epsilon)
            }
            fxImage.pixelReader.getColor(1, 0).apply {
                assertEquals(red,     premultiplyAndRestore(4, 7) / 255.0, epsilon)
                assertEquals(green,   premultiplyAndRestore(4, 7) / 255.0, epsilon)
                assertEquals(blue,    premultiplyAndRestore(4, 7) / 255.0, epsilon)
                assertEquals(opacity, 7.0 / 255.0, epsilon)
            }
            fxImage.pixelReader.getColor(0, 1).apply {
                assertEquals(red,     premultiplyAndRestore(126, 129) / 255.0, epsilon)
                assertEquals(green,   premultiplyAndRestore(127, 129) / 255.0, epsilon)
                assertEquals(blue,    premultiplyAndRestore(128, 129) / 255.0, epsilon)
                assertEquals(opacity, 129.0 / 255.0, epsilon)
            }
            fxImage.pixelReader.getColor(1, 1).apply {
                assertEquals(red,     premultiplyAndRestore(252, 255) / 255.0, epsilon)
                assertEquals(green,   premultiplyAndRestore(253, 255) / 255.0, epsilon)
                assertEquals(blue,    premultiplyAndRestore(254, 255) / 255.0, epsilon)
                assertEquals(opacity, 255.0 / 255.0, epsilon)
            }
        }
        run { // Byte
            val image = Image<Byte>(2, 2, arrayOf<Byte>(
                    0, 127, -128, -1
            ))

            val fxImage = image.toFXImage()

            fxImage.pixelReader.getColor(0, 0).apply {
                assertEquals(red,       0.0 / 255.0, epsilon)
                assertEquals(green,     0.0 / 255.0, epsilon)
                assertEquals(blue,      0.0 / 255.0, epsilon)
                assertEquals(opacity, 255.0 / 255.0, epsilon)
            }
            fxImage.pixelReader.getColor(1, 0).apply {
                assertEquals(red,     127.0 / 255.0, epsilon)
                assertEquals(green,   127.0 / 255.0, epsilon)
                assertEquals(blue,    127.0 / 255.0, epsilon)
                assertEquals(opacity, 255.0 / 255.0, epsilon)
            }
            fxImage.pixelReader.getColor(0, 1).apply {
                assertEquals(red,     128.0 / 255.0, epsilon)
                assertEquals(green,   128.0 / 255.0, epsilon)
                assertEquals(blue,    128.0 / 255.0, epsilon)
                assertEquals(opacity, 255.0 / 255.0, epsilon)
            }
            fxImage.pixelReader.getColor(1, 1).apply {
                assertEquals(red,     255.0 / 255.0, epsilon)
                assertEquals(green,   255.0 / 255.0, epsilon)
                assertEquals(blue,    255.0 / 255.0, epsilon)
                assertEquals(opacity, 255.0 / 255.0, epsilon)
            }
        }
    }
}