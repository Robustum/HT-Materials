package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialBuilder
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTMetalProperty
import net.minecraft.block.Blocks

@Suppress("unused")
object HTVanillaMaterials {

    //    Stones    //

    @JvmField
    val STONE = HTMaterialBuilder.createStone("stone") {
        modifyInfo {
            color = Blocks.STONE.defaultMapColor.color
        }
    }

    @JvmField
    val GRANITE = HTMaterialBuilder.createStone("granite") {
        modifyInfo {
            color = Blocks.GRANITE.defaultMapColor.color
        }
    }

    @JvmField
    val DIORITE = HTMaterialBuilder.createStone("diorite") {
        modifyInfo {
            color = Blocks.DIORITE.defaultMapColor.color
        }
    }

    @JvmField
    val ANDESITE = HTMaterialBuilder.createStone("andesite") {
        modifyInfo {
            color = Blocks.ANDESITE.defaultMapColor.color
        }
    }

    @JvmField
    val DEEPSLATE = HTMaterialBuilder.createStone("deepslate") {
        modifyInfo {
            color = Blocks.DEEPSLATE.defaultMapColor.color
        }
    }

    @JvmField
    val CALCITE = HTMaterialBuilder.createStone("calcite") {
        modifyInfo {
            color = Blocks.CALCITE.defaultMapColor.color
        }
    }

    @JvmField
    val TUFF = HTMaterialBuilder.createStone("tuff") {
        modifyInfo {
            color = Blocks.TUFF.defaultMapColor.color
        }
    }

    @JvmField
    val DRIPSTONE = HTMaterialBuilder.createStone("dripstone") {
        modifyInfo {
            color = Blocks.DRIPSTONE_BLOCK.defaultMapColor.color
        }
    }

    //    Woods    //

    @JvmField
    val WOOD = HTMaterialBuilder.createWood("wood") {
        modifyInfo {
            color = Blocks.OAK_PLANKS.defaultMapColor.color
        }
    }

    //    Minerals    //

    @JvmField
    val IRON = HTMaterial.createMaterial("iron") {
        modifyInfo {
            color = Blocks.IRON_BLOCK.defaultMapColor.color
            formula = "Fe"
        }
        modifyProperties { addSafety(HTMetalProperty()) }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    @JvmField
    val GOLD = HTMaterial.createMaterial("gold") {
        modifyInfo {
            color = Blocks.GOLD_BLOCK.defaultMapColor.color
            formula = "Au"
        }
        modifyProperties { addSafety(HTMetalProperty()) }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

}