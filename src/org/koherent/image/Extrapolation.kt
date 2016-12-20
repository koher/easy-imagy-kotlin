package org.koherent.image

sealed class Extrapolation<out Pixel> {
    class Constant<Pixel>(val value: Pixel) : Extrapolation<Pixel>()
    object Replicate : Extrapolation<Nothing>()
}
