package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterialBuilder
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.tag.BlockTags

@Suppress("unused", "UnstableApiUsage")
object HTVanillaMaterials {

    //    Fluids    //

    @JvmField
    val WATER = HTMaterialBuilder.createFluid("water") {
        modifyProperties {
            getAs(HTPropertyKey.FLUID)?.let { property ->
                property.fluid = Fluids.WATER
                property.defaultAmount = FluidConstants.BLOCK
            }
        }
    }

    @JvmField
    val LAVA = HTMaterialBuilder.createFluid("lava") {
        modifyProperties {
            getAs(HTPropertyKey.FLUID)?.let { property ->
                property.fluid = Fluids.LAVA
                property.defaultAmount = FluidConstants.BLOCK
            }
        }
    }

    //    Minerals    //

    val DIAMOND = HTMaterialBuilder.createGem("diamond") {
        modifyInfo {
            color = Blocks.DIAMOND_BLOCK.defaultMapColor.color
            formula = "C"
        }
        modifyProperties { getAs(HTPropertyKey.SOLID)?.harvestLevel = BlockTags.NEEDS_DIAMOND_TOOL }
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
    val NETHERITE = HTMaterialBuilder.createMetal("netherite") {
        modifyInfo {
            color = Blocks.NETHERITE_BLOCK.defaultMapColor.color
            formula = "Nr"
        }
        modifyProperties { getAs(HTPropertyKey.SOLID)?.harvestLevel = BlockTags.NEEDS_DIAMOND_TOOL }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    //    Woods    //

    @JvmField
    val WOOD = HTMaterialBuilder.createWood("wood") {
        modifyInfo {
            color = Blocks.OAK_PLANKS.defaultMapColor.color
        }
    }


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

}