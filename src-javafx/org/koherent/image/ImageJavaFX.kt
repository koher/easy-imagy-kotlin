package org.koherent.image

import javafx.embed.swing.SwingFXUtils

@JvmName("toEasyImageOfRGBAByte") fun javafx.scene.image.Image.toEasyImage(type: ParameterType.RGBAByte = ParameterType.RGBAByte): Image<RGBA<Byte>> {
    return SwingFXUtils.fromFXImage(this, null).toEasyImage(type)
}

@JvmName("toEasyImageOfByte") fun javafx.scene.image.Image.toEasyImage(type: ParameterType.Byte = ParameterType.Byte): Image<Byte> {
    return SwingFXUtils.fromFXImage(this, null).toEasyImage(type)
}

@JvmName("toFXImageFromImageOfRGBAByte") fun Image<RGBA<Byte>>.toFXImage(): javafx.scene.image.Image {
    return SwingFXUtils.toFXImage(toBufferedImage(), null)
}

@JvmName("toFXImageFromImageOfByte") fun Image<Byte>.toFXImage(): javafx.scene.image.Image {
    return SwingFXUtils.toFXImage(toBufferedImage(), null)
}
