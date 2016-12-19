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
}
