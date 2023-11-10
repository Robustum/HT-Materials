package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialBuilder
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTMetalProperty
import io.github.hiiragi283.material.common.commonId
import net.minecraft.block.Blocks

@Suppress("unused")
object HTVanillaMaterials {

    //    Stones    //

    @JvmField
    val STONE = HTMaterialBuilder.createStone(commonId("stone")) {
        modifyInfo {
            color = Blocks.STONE.defaultMapColor.color
        }
    }

    @JvmField
    val GRANITE = HTMaterialBuilder.createStone(commonId("granite")) {
        modifyInfo {
            color = Blocks.GRANITE.defaultMapColor.color
        }
    }

    @JvmField
    val DIORITE = HTMaterialBuilder.createStone(commonId("diorite")) {
        modifyInfo {
            color = Blocks.DIORITE.defaultMapColor.color
        }
    }

    @JvmField
    val ANDESITE = HTMaterialBuilder.createStone(commonId("andesite")) {
        modifyInfo {
            color = Blocks.ANDESITE.defaultMapColor.color
        }
    }

    @JvmField
    val DEEPSLATE = HTMaterialBuilder.createStone(commonId("deepslate")) {
        modifyInfo {
            color = Blocks.DEEPSLATE.defaultMapColor.color
        }
    }

    @JvmField
    val CALCITE = HTMaterialBuilder.createStone(commonId("calcite")) {
        modifyInfo {
            color = Blocks.CALCITE.defaultMapColor.color
        }
    }

    @JvmField
    val TUFF = HTMaterialBuilder.createStone(commonId("tuff")) {
        modifyInfo {
            color = Blocks.TUFF.defaultMapColor.color
        }
    }

    @JvmField
    val DRIPSTONE = HTMaterialBuilder.createStone(commonId("dripstone")) {
        modifyInfo {
            color = Blocks.DRIPSTONE_BLOCK.defaultMapColor.color
        }
    }

    //    Woods    //

    @JvmField
    val WOOD = HTMaterialBuilder.createWood(commonId("wood")) {
        modifyInfo {
            color = Blocks.OAK_PLANKS.defaultMapColor.color
        }
    }

    //    Minerals    //

    @JvmField
    val IRON = HTMaterial.createMaterial(commonId("iron")) {
        modifyInfo { formula = "Fe" }
        modifyProperties { addSafety(HTMetalProperty()) }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_INGOT) }
    }

}