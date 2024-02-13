package io.github.hiiragi283.api.material.element

import io.github.hiiragi283.api.extention.averageColor
import io.github.hiiragi283.api.extention.calculateMolar
import io.github.hiiragi283.api.extention.formatFormula
import java.awt.Color

sealed interface HTElement<T : HTElement<T>> {
    val color: Color
    val formula: String
    val molar: Double

    fun bracket(): T

    companion object {
        @JvmStatic
        fun of(color: Color, formula: String, molar: Double): HTElement<*> = Impl(color, formula, molar)

        @JvmStatic
        fun group(vararg pairs: Pair<HTElement<*>, Int>): HTElement<*> = group(mapOf(*pairs))

        @JvmStatic
        fun group(
            map: Map<HTElement<*>, Int>,
            color: Color = averageColor(map.mapKeys { it.key.color }),
            formula: String = formatFormula(map.mapKeys { it.key.formula }),
            molar: Double = calculateMolar(map.mapKeys { it.key.molar }),
        ): HTElement<*> = Impl(color, formula, molar)
    }

    private data class Impl(
        override val color: Color,
        override val formula: String,
        override val molar: Double,
    ) : HTElement<Impl> {
        override fun bracket(): Impl = copy(formula = "($formula)")
    }
}
