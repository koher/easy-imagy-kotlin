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
        run { // RGBA<Byte>
            val image = Image<RGBA<Byte>>(2, 2, arrayOf<RGBA<Byte>>(
                    RGBA(0, 1, 2, 3),
                    RGBA(4, 5, 6, 7),
                    RGBA(126, 127, -128, -127),
                    RGBA(-4, -3, -2, -1)
            ))

            val bufferedImage = image.toBufferedImage()

            assertEquals(bufferedImage.raster.getSample(0, 0, 0), 0)
            assertEquals(bufferedImage.raster.getSample(0, 0, 1), 1)
            assertEquals(bufferedImage.raster.getSample(0, 0, 2), 2)
            assertEquals(bufferedImage.raster.getSample(0, 0, 3), 3)

            assertEquals(bufferedImage.raster.getSample(1, 0, 0), 4)
            assertEquals(bufferedImage.raster.getSample(1, 0, 1), 5)
            assertEquals(bufferedImage.raster.getSample(1, 0, 2), 6)
            assertEquals(bufferedImage.raster.getSample(1, 0, 3), 7)

            assertEquals(bufferedImage.raster.getSample(0, 1, 0), 126)
            assertEquals(bufferedImage.raster.getSample(0, 1, 1), 127)
            assertEquals(bufferedImage.raster.getSample(0, 1, 2), 128)
            assertEquals(bufferedImage.raster.getSample(0, 1, 3), 129)

            assertEquals(bufferedImage.raster.getSample(1, 1, 0), 252)
            assertEquals(bufferedImage.raster.getSample(1, 1, 1), 253)
            assertEquals(bufferedImage.raster.getSample(1, 1, 2), 254)
            assertEquals(bufferedImage.raster.getSample(1, 1, 3), 255)
        }
        run { // Byte
            val image = Image<Byte>(2, 2, arrayOf<Byte>(
                    0, 127, -128, -1
            ))

            val bufferedImage = image.toBufferedImage()

            assertEquals(bufferedImage.raster.getSample(0, 0, 0), 0)
            assertEquals(bufferedImage.raster.getSample(1, 0, 0), 127)
            assertEquals(bufferedImage.raster.getSample(0, 1, 0), 128)
            assertEquals(bufferedImage.raster.getSample(1, 1, 0), 255)
        }
    }
}
