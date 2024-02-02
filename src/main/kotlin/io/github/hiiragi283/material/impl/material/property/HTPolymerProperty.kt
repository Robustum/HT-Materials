package io.github.hiiragi283.material.impl.material.property

import io.github.hiiragi283.api.material.property.HTComponentProperty
import io.github.hiiragi283.api.material.property.HTPropertyKey
import java.awt.Color

class HTPolymerProperty(val monomar: String) : HTComponentProperty<HTPolymerProperty> {
    companion object {
        @JvmField
        val KEY: HTPropertyKey<HTPolymerProperty> = HTPropertyKey.create("polymer")
    }

    override val key: HTPropertyKey<HTPolymerProperty> = KEY

    override fun asColor(): Color = Color.WHITE

    override fun asFormula(): String = "($monomar)n"

    override fun asMolarMass(): Double = 0.0
}
