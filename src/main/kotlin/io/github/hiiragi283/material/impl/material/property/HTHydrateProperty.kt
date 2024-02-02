package io.github.hiiragi283.material.impl.material.property

import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.property.HTComponentProperty
import io.github.hiiragi283.api.material.property.HTPropertyKey
import java.awt.Color

class HTHydrateProperty(val parent: HTMaterialKey, val waterWeight: Int) : HTComponentProperty<HTHydrateProperty> {
    companion object {
        @JvmField
        val KEY: HTPropertyKey<HTHydrateProperty> = HTPropertyKey.create("hydrate")
    }

    override val key: HTPropertyKey<HTHydrateProperty> = KEY

    override fun asColor(): Color = Color.WHITE

    override fun asFormula(): String = "${parent.getMaterial().formula}-${waterWeight}H\u2082O"

    override fun asMolarMass(): Double = parent.getMaterial().molar + waterWeight * 18.0
}
