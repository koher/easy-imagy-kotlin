package org.koherent.image

sealed class Extrapolation<Pixel> {
    class Constant<Pixel>(val value: Pixel) : Extrapolation<Pixel>()
    object Replicate : Extrapolation<Nothing>()
}
