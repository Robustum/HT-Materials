package io.github.hiiragi283.material.impl.material.property

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.property.HTComponentProperty
import io.github.hiiragi283.api.material.property.HTPropertyKey
import java.awt.Color

class HTMixtureProperty(iterable: Iterable<HTMaterialKey>) :
    HTComponentProperty<HTMixtureProperty>,
    Iterable<HTMaterialKey> by iterable {
    companion object {
        @JvmField
        val KEY: HTPropertyKey<HTMixtureProperty> = HTPropertyKey.create("mixture")
    }

    constructor(vararg keys: HTMaterialKey) : this(keys.toList())

    override val key: HTPropertyKey<HTMixtureProperty> = KEY

    override fun asColor(): Color = ColorConvertible.average(this.map { it.getMaterial().color })

    override fun asFormula(): String = ""

    override fun asMolarMass(): Double = 0.0
}
