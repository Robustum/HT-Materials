package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.util.HTColor
import java.awt.Color

abstract class HTMaterialComposition {
    abstract fun componentMap(): Map<HTElement, Int>

    abstract fun color(): Color

    abstract fun formula(): String

    abstract fun molar(): Double

    data object Empty : HTMaterialComposition() {
        override fun componentMap(): Map<HTElement, Int> = mapOf()

        override fun color(): Color = HTColor.WHITE

        override fun formula(): String = ""

        override fun molar(): Double = 0.0
    }
}
