package io.github.hiiragi283.api.material.element

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.FormulaConvertible
import io.github.hiiragi283.api.material.MolarMassConvertible
import java.awt.Color

data class HTElementGroup
    @JvmOverloads
    constructor(
        private val map: Map<HTElement, Int>,
        override val color: Color = ColorConvertible.average(map.mapKeys { it.key.color }),
        override val formula: String = FormulaConvertible.format(map.mapKeys { it.key.formula }),
        override val molar: Double = MolarMassConvertible.calculate(map.mapKeys { it.key.molar }),
    ) : HTMaterialInfoProvider<HTElementGroup> {
        constructor(vararg pair: Pair<HTElement, Int>) : this(mapOf(*pair))

        override fun bracket(): HTElementGroup = copy(formula = "($formula)")
    }
