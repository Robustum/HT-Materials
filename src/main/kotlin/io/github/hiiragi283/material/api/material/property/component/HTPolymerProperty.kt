package io.github.hiiragi283.material.api.material.property.component

import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import java.awt.Color

class HTPolymerProperty(val monomar: String) : HTComponentProperty<HTPolymerProperty> {
    override val key: HTPropertyKey<HTPolymerProperty> = HTPropertyKey.POLYMER

    override fun asColor(): Color = Color.WHITE

    override fun asFormula(): String = "($monomar)n"

    override fun asMolarMass(): Double = 0.0
}
