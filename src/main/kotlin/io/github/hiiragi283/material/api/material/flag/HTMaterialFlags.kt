package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.property.HTPropertyKey

object HTMaterialFlags {

    @JvmField
    val GENERATE_BLOCk: HTMaterialFlag = HTMaterialFlag.create("generate_block")

    @JvmField
    val GENERATE_DUST: HTMaterialFlag = HTMaterialFlag.create("generate_dust")

    @JvmField
    val GENERATE_GEAR: HTMaterialFlag = HTMaterialFlag.create("generate_gear")

    @JvmField
    val GENERATE_GEM: HTMaterialFlag = HTMaterialFlag.create("generate_gem") {
        requiredProperties.add(HTPropertyKey.GEM)
    }

    @JvmField
    val GENERATE_INGOT: HTMaterialFlag = HTMaterialFlag.create("generate_ingot")

    @JvmField
    val GENERATE_NUGGET: HTMaterialFlag = HTMaterialFlag.create("generate_nugget") {
        requiredProperties.add(HTPropertyKey.METAL)
    }

    @JvmField
    val GENERATE_PLATE: HTMaterialFlag = HTMaterialFlag.create("generate_plate")

    @JvmField
    val GENERATE_ROD: HTMaterialFlag = HTMaterialFlag.create("generate_rod")

}