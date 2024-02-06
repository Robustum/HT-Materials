package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.element.HTElements
import io.github.hiiragi283.api.material.element.HTMaterialInfoProvider
import io.github.hiiragi283.api.util.HTColor
import java.awt.Color

open class HTHydrateComposition(private val composition: HTMaterialComposition, private val waterCount: Int) : HTMaterialComposition() {
    override fun componentMap(): Map<HTMaterialInfoProvider<*>, Int> = buildMap {
        putAll(composition.componentMap())
        put(HTElements.H, getOrDefault(HTElements.H, 0) + 2)
        put(HTElements.O, getOrDefault(HTElements.O, 0) + 1)
    }

    override fun color(): Color = HTColor.WHITE

    override fun formula(): String = "${composition.formula()}-${waterCount}H₂O"

    override fun molar(): Double = composition.molar() + waterCount * 18.0
}
