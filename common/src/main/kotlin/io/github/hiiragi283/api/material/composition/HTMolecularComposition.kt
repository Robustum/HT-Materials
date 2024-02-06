package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.FormulaConvertible
import io.github.hiiragi283.api.material.MolarMassConvertible
import io.github.hiiragi283.api.material.element.HTMaterialInfoProvider
import java.awt.Color

open class HTMolecularComposition(private val map: Map<HTMaterialInfoProvider<*>, Int>) : HTMaterialComposition() {
    constructor(vararg pair: Pair<HTMaterialInfoProvider<*>, Int>) : this(mapOf(*pair))

    override fun componentMap(): Map<HTMaterialInfoProvider<*>, Int> = map

    override fun color(): Color = ColorConvertible.average(map.mapKeys { it.key.color })

    override fun formula(): String = FormulaConvertible.format(map.mapKeys { it.key.formula })

    override fun molar(): Double = MolarMassConvertible.calculate(map.mapKeys { it.key.molar })
}
