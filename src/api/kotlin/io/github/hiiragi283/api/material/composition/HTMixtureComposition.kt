package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.element.HTMaterialInfoProvider
import java.awt.Color

open class HTMixtureComposition(private val elements: Collection<HTMaterialInfoProvider<*>>) : HTMaterialComposition() {
    constructor(vararg providers: HTMaterialInfoProvider<*>) : this(providers.toList())

    override fun componentMap(): Map<HTMaterialInfoProvider<*>, Int> = elements.associateWith { 1 }

    override fun color(): Color = ColorConvertible.average(elements.map(HTMaterialInfoProvider<*>::color))

    override fun formula(): String = ""

    override fun molar(): Double = 0.0
}
