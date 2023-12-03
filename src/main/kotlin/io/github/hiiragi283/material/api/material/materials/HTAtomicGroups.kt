package io.github.hiiragi283.material.api.material.materials

object HTAtomicGroups {

    //CO3
    @JvmField
    val CARBONATE = arrayOf(HTElementMaterials.CARBON to 1, HTElementMaterials.OXYGEN to 3)

    //NO3
    @JvmField
    val NITRATE = arrayOf(HTElementMaterials.NITROGEN to 1, HTElementMaterials.OXYGEN to 3)

    //Al2O3
    @JvmField
    val ALUMINUM_OXIDE = arrayOf(HTElementMaterials.ALUMINUM to 2, HTElementMaterials.OXYGEN to 3)

    //SiO2
    @JvmField
    val SILICON_OXIDE = arrayOf(HTElementMaterials.SILICON to 1, HTElementMaterials.OXYGEN to 2)

}