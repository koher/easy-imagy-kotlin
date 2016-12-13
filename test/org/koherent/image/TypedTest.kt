package org.koherent.image

import org.testng.Assert.*
import org.testng.annotations.Test

class TypedTest {
    @Test
    fun convolutedIii() {
        val image = Image<Int>(3, 3, arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
        val kernel = Image<Int>(3, 3, arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
        val result = image.convoluted(kernel, Extrapolation.Constant(0))
        assertEquals(result, Image<Int>(3, 3, arrayOf(94, 154, 106, 186, 285, 186, 106, 154, 94)))
    }
}