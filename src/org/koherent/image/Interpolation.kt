package org.koherent.image

sealed class Interpolation {
    object NearestNeighbor: Interpolation()
    object Bilinear: Interpolation()
    object Bicubic: Interpolation()
}
