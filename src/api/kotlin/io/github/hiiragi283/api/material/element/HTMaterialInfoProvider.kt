package io.github.hiiragi283.api.material.element

import java.awt.Color

interface HTMaterialInfoProvider<T : HTMaterialInfoProvider<T>> {
    val color: Color
    val formula: String
    val molar: Double

    fun bracket(): T
}
