package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.*
import java.awt.Color

class HTCompoundProperty(map: Map<HTMaterialKey, Int>) : HTComponentProperty<HTCompoundProperty>,
    Map<HTMaterialKey, Int> by map {

    constructor(vararg pairs: Pair<HTMaterialKey, Int>) : this(pairs.toMap())

    override val key: HTPropertyKey<HTCompoundProperty> = HTPropertyKey.COMPOUND

    override fun asColor(): Color =
        ColorConvertible.average(this.mapKeys { (key: HTMaterialKey, _) -> HTMaterial.getMaterial(key).color })

    override fun asFormula(): String =
        FormulaConvertible.format(this.mapKeys { (key: HTMaterialKey, _) -> HTMaterial.getMaterial(key).formula })

    override fun asMolarMass(): Double =
        MolarMassConvertible.calculate(this.mapKeys { (key: HTMaterialKey, _) -> HTMaterial.getMaterial(key).molar })

}