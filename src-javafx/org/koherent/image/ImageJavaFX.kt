package org.koherent.image

import javafx.embed.swing.SwingFXUtils

fun javafx.scene.image.Image.toEasyImage(type: ParameterType.RGBAByte = ParameterType.RGBAByte): Image<RGBA<Byte>> {
    return SwingFXUtils.fromFXImage(this, null).toEasyImage(type)
}

fun javafx.scene.image.Image.toEasyImage(type: ParameterType.Byte = ParameterType.Byte): Image<Byte> {
    return SwingFXUtils.fromFXImage(this, null).toEasyImage(type)
}

fun Image<RGBA<Byte>>.toFXImage(type: ParameterType.RGBAByte = ParameterType.RGBAByte): javafx.scene.image.Image {
    return SwingFXUtils.toFXImage(toBufferedImage(), null)
}

fun Image<Byte>.toFXImage(type: ParameterType.Byte = ParameterType.Byte): javafx.scene.image.Image {
    return SwingFXUtils.toFXImage(toBufferedImage(), null)
}
