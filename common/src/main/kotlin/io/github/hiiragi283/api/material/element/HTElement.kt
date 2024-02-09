package io.github.hiiragi283.api.material.element

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.FormulaConvertible
import io.github.hiiragi283.api.material.MolarMassConvertible
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
            color: Color = ColorConvertible.average(map.mapKeys { it.key.color }),
            formula: String = FormulaConvertible.format(map.mapKeys { it.key.formula }),
            molar: Double = MolarMassConvertible.calculate(map.mapKeys { it.key.molar }),
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
