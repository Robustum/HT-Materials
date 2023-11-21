package io.github.hiiragi283.material.api.material.formula

import io.github.hiiragi283.material.api.material.materials.HTElementMaterials

object HTCommonFormula {

    //SiO2
    @JvmField
    val SILICON_DIOXIDE = FormulaConvertible.of(HTElementMaterials.SILICON to 1, HTElementMaterials.OXYGEN to 2)

    //CO3
    @JvmField
    val CARBONATE = FormulaConvertible.of(HTElementMaterials.CARBON to 1, HTElementMaterials.OXYGEN to 3)

    //NO3
    @JvmField
    val NITRATE = FormulaConvertible.of(HTElementMaterials.NITROGEN to 1, HTElementMaterials.OXYGEN to 3)

}