package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.util.HTColor
import java.awt.Color
import java.util.function.Supplier

class HTHydrateComposition(
    private val composition: HTMolecularComposition,
    private val waterCount: Int,
    private val color: Supplier<Color> = Supplier { HTColor.WHITE },
) : HTMaterialComposition() {
    override fun componentMap(): Map<HTElement, Int> = buildMap {
        putAll(composition.componentMap())
    }

    override fun color(): Color = color.get()

    override fun formula(): String = "${composition.formula()}-${waterCount}H₂O"

    override fun molar(): Double = composition.molar() + waterCount * 18.0
}
