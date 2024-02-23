package io.github.hiiragi283.api.material.element

import io.github.hiiragi283.api.extension.averageColor
import io.github.hiiragi283.api.extension.calculateMolar
import io.github.hiiragi283.api.extension.formatFormula
import java.awt.Color

@Suppress("DataClassPrivateConstructor")
data class HTElement private constructor(
    val color: Color,
    val formula: String,
    val molar: Double,
) {
    fun bracket() = copy(formula = "($formula)")

    companion object {
        @JvmStatic
        fun of(color: Color, formula: String, molar: Double): HTElement = HTElement(color, formula, molar)

        @JvmStatic
        fun group(vararg pairs: Pair<HTElement, Int>): HTElement = group(mapOf(*pairs))

        @JvmStatic
        fun group(
            map: Map<HTElement, Int>,
            color: Color = averageColor(map.mapKeys { it.key.color }),
            formula: String = formatFormula(map.mapKeys { it.key.formula }),
            molar: Double = calculateMolar(map.mapKeys { it.key.molar }),
        ): HTElement = HTElement(color, formula, molar)
    }
}
