package io.github.hiiragi283.material.api.material.property.component

import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import java.awt.Color

class HTHydrateProperty(val parent: HTMaterialKey, val waterWeight: Int) : HTComponentProperty<HTHydrateProperty> {
    override val key: HTPropertyKey<HTHydrateProperty> = HTPropertyKey.HYDRATE

    override fun asColor(): Color = Color.WHITE

    override fun asFormula(): String = "${parent.getMaterial().formula}-${waterWeight}H\u2082O"

    override fun asMolarMass(): Double = parent.getMaterial().molar + waterWeight * 18.0
}
