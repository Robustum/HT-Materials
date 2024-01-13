package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.HTMaterialKey
import java.awt.Color

class HTMixtureProperty(iterable: Iterable<HTMaterialKey>) :
    HTComponentProperty<HTMixtureProperty>,
    Iterable<HTMaterialKey> by iterable {
    constructor(vararg keys: HTMaterialKey) : this(keys.toList())

    override val key: HTPropertyKey<HTMixtureProperty> = HTPropertyKey.MIXTURE

    override fun asColor(): Color = ColorConvertible.average(this.map { it.getMaterial().color })

    override fun asFormula(): String = ""

    override fun asMolarMass(): Double = 0.0
}
