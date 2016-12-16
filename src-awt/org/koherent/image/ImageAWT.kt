package org.koherent.image

import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte

fun rgbaImage(from: BufferedImage): Image<RGBA<Byte>> {
    when (from.type) {
        BufferedImage.TYPE_4BYTE_ABGR -> {}
        else -> throw IllegalArgumentException("Unsupported type: ${from.type}")
    }

    val bytes = (from.raster.dataBuffer as DataBufferByte).data
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

    return Image(from.width, from.height, pixels.toTypedArray())
}


