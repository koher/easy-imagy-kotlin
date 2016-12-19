package org.koherent.image

import org.testng.Assert.*
import org.testng.annotations.Test
import java.io.File
import javax.imageio.ImageIO

class ImageAWTTest {
    @Test
    fun testRgbaImage() {
        run {
            val image = rgbaImage(ImageIO.read(File("res/test-2x1.png")))
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
            val image = rgbaImage(ImageIO.read(File("res/test-2x2.png")))
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
            val image = grayImage(ImageIO.read(File("res/test-2x1.png")))
            assertEquals(image.width, 2)
            assertEquals(image.height, 1)

            assertEquals(image[0, 0].toInt() and 0xff, 127)
            assertEquals(image[1, 0].toInt() and 0xff, 0)
        }

        run {
            val image = grayImage(ImageIO.read(File("res/test-2x2.png")))
            assertEquals(image.width, 2)
            assertEquals(image.height, 2)

            assertEquals(image[0, 0].toInt() and 0xff, 21)
            assertEquals(image[1, 0].toInt() and 0xff, 42)
            assertEquals(image[0, 1].toInt() and 0xff, 63)
            assertEquals(image[1, 1].toInt() and 0xff, 170)
        }
    }

    @Test
    fun toBufferedImage() {
        val image = Image<RGBA<Byte>>(2, 2, arrayOf<RGBA<Byte>>(
                RGBA(0, 1, 2, 3),
                RGBA(4, 5, 6, 7),
                RGBA(126, 127, -128, -127),
                RGBA(-4, -3, -2, -1)
        ))

        val bufferedImage = image.toBufferedImage()

        bufferedImage.getRGB(0, 0).let {
            assertEquals((it ushr 16) and 0xff, 0)
            assertEquals((it ushr  8) and 0xff, 1)
            assertEquals((it ushr  0) and 0xff, 2)
            assertEquals((it ushr 24) and 0xff, 3)
        }
        bufferedImage.getRGB(1, 0).let {
            assertEquals((it ushr 16) and 0xff, 4)
            assertEquals((it ushr  8) and 0xff, 5)
            assertEquals((it ushr  0) and 0xff, 6)
            assertEquals((it ushr 24) and 0xff, 7)
        }
        bufferedImage.getRGB(0, 1).let {
            assertEquals((it ushr 16) and 0xff, 126)
            assertEquals((it ushr  8) and 0xff, 127)
            assertEquals((it ushr  0) and 0xff, 128)
            assertEquals((it ushr 24) and 0xff, 129)
        }
        bufferedImage.getRGB(1, 1).let {
            assertEquals((it ushr 16) and 0xff, 252)
            assertEquals((it ushr  8) and 0xff, 253)
            assertEquals((it ushr  0) and 0xff, 254)
            assertEquals((it ushr 24) and 0xff, 255)
        }
    }
}
