package io.github.hiiragi283.material.impl.material.property

import io.github.hiiragi283.api.material.*
import io.github.hiiragi283.api.material.property.HTComponentProperty
import io.github.hiiragi283.api.material.property.HTPropertyKey
import java.awt.Color

class HTCompoundProperty(map: Map<HTMaterialKey, Int>) : HTComponentProperty<HTCompoundProperty>, Map<HTMaterialKey, Int> by map {
    companion object {
        @JvmField
        val KEY: HTPropertyKey<HTCompoundProperty> = HTPropertyKey.create("component")
    }

    constructor(vararg pairs: Pair<HTMaterialKey, Int>) : this(pairs.toMap())

    override val key: HTPropertyKey<HTCompoundProperty> = KEY

    override fun asColor(): Color = ColorConvertible.average(this.mapKeys { (key: HTMaterialKey, _) -> key.getMaterial().color })

    override fun asFormula(): String = FormulaConvertible.format(this.mapKeys { (key: HTMaterialKey, _) -> key.getMaterial().formula })

    override fun asMolarMass(): Double = MolarMassConvertible.calculate(this.mapKeys { (key: HTMaterialKey, _) -> key.getMaterial().molar })
}
