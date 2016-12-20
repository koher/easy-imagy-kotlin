package org.koherent.image

import java.awt.image.BufferedImage

import java.awt.image.DataBufferByte

fun BufferedImage.toEasyImage(type: ParameterType.RGBAByte = ParameterType.RGBAByte): Image<RGBA<Byte>> {
    when (this.type) {
        BufferedImage.TYPE_4BYTE_ABGR -> {}
        else -> {
            val converted = BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
            converted.graphics.drawImage(this, 0, 0, null)
            return converted.toEasyImage(ParameterType.RGBAByte)
        }
    }

    val bytes = (raster.dataBuffer as DataBufferByte).data
    val pixels: MutableList<RGBA<Byte>> = mutableListOf()
    bytes.forEachIndexed { i, value ->
        val channel = i % 4
        if (channel == 0) {
            pixels.add(RGBA<Byte>(0, 0, 0, value))
        } else {
            val pixel = pixels.last()
            when (channel) {
                1 -> pixel.blue = value
                2 -> pixel.green = value
                3 -> pixel.red = value
                else -> throw Error("Never reaches here.")
            }
        }
    }

    return Image(width, height, pixels.toTypedArray())
}

fun BufferedImage.toEasyImage(type: ParameterType.Byte = ParameterType.Byte): Image<Byte> {
    return toEasyImage(ParameterType.RGBAByte).map { ((it.grayInt * it.alphaInt) / 255).toByte() }
}

@JvmName("toBufferedImageFromImageOfRGBAByte") fun Image<RGBA<Byte>>.toBufferedImage(): BufferedImage {
    return BufferedImage(width,  height, BufferedImage.TYPE_4BYTE_ABGR).apply {
        val bytes = (raster.dataBuffer as DataBufferByte).data
        pixels.forEachIndexed { pixelIndex, rgba ->
            val i = pixelIndex * 4
            bytes[i] = rgba.alpha
            bytes[i + 1] = rgba.blue
            bytes[i + 2] = rgba.green
            bytes[i + 3] = rgba.red
        }
    }
}

@JvmName("toBufferedImageFromImageOfByte") fun Image<Byte>.toBufferedImage(): BufferedImage {
    return BufferedImage(width,  height, BufferedImage.TYPE_BYTE_GRAY).apply {
        val bytes = (raster.dataBuffer as DataBufferByte).data
        pixels.forEachIndexed { i, byte ->
            bytes[i] = byte
        }
    }
}
