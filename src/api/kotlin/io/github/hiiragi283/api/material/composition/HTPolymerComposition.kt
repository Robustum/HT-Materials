package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.element.HTMaterialInfoProvider
import java.awt.Color

open class HTPolymerComposition(private val monomar: HTMaterialComposition) : HTMaterialComposition() {
    override fun componentMap(): Map<HTMaterialInfoProvider<*>, Int> = monomar.componentMap()

    override fun color(): Color = monomar.color()

    override fun formula(): String = "(${monomar.formula()})n"

    override fun molar(): Double = 0.0
}
