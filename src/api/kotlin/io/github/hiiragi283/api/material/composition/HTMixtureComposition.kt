package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.element.HTElement
import java.awt.Color
import java.util.function.Supplier

class HTMixtureComposition(
    private val elements: Collection<HTElement>,
    private val color: Supplier<Color> = Supplier { ColorConvertible.average(elements.map(HTElement::color)) },
    private val formula: Supplier<String> = Supplier { "" },
) : HTMaterialComposition() {
    constructor(vararg element: HTElement) : this(element.toList())

    override fun componentMap(): Map<HTElement, Int> = elements.associateWith { 1 }

    override fun color(): Color = color.get()

    override fun formula(): String = formula.get()

    override fun molar(): Double = 0.0
}
