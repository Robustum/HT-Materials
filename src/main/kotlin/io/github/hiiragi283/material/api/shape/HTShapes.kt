package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.registry.HTObjectKeySet

object HTShapes : HTMaterialsAddon {

    //    Block    //

    @JvmField
    val BLOCK = HTShapeKey("block", forgePath = "storage_blocks/%s")

    @JvmField
    val ORE = HTShapeKey("ore")

    //    Item    //

    @JvmField
    val DUST = HTShapeKey("dust")

    @JvmField
    val GEAR = HTShapeKey("gear")

    @JvmField
    val GEM = HTShapeKey("gem")

    @JvmField
    val INGOT = HTShapeKey("ingot")

    @JvmField
    val NUGGET = HTShapeKey("nugget")

    @JvmField
    val PLATE = HTShapeKey("plate")

    @JvmField
    val ROD = HTShapeKey("rod")

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -200

    override fun registerShape(registry: HTObjectKeySet<HTShapeKey>) {
        //Block
        registry.addAll(
            BLOCK,
            ORE
        )
        //Item
        registry.addAll(
            DUST,
            GEAR,
            GEM,
            INGOT,
            NUGGET,
            PLATE,
            ROD
        )
    }

}