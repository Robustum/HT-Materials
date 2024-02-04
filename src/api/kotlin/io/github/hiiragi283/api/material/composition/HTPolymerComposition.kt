package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.element.HTElement
import java.awt.Color
import java.util.function.Supplier

class HTPolymerComposition(
    private val monomar: HTMaterialComposition,
    private val color: Supplier<Color> = Supplier { monomar.color() },
) : HTMaterialComposition() {
    override fun componentMap(): Map<HTElement, Int> = monomar.componentMap()

    override fun color(): Color = color.get()

    override fun formula(): String = "(${monomar.formula()})n"

    override fun molar(): Double = 0.0
}
