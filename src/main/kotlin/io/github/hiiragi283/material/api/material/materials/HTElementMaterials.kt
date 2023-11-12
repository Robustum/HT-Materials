package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterialBuilder
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.block.Blocks

@Suppress("UnstableApiUsage", "unused")
object HTElementMaterials {

    //    1st Period    //

    @JvmField
    val HYDROGEN = HTMaterialBuilder.createFluid("hydrogen") {
        modifyProperties { getAs(HTPropertyKey.FLUID)?.defaultAmount = FluidConstants.BLOCK }
    }

    @JvmField
    val HELIUM = HTMaterialBuilder.createFluid("helium") {
        modifyProperties { getAs(HTPropertyKey.FLUID)?.defaultAmount = FluidConstants.BLOCK }
    }

    //    2nd Period    //

    @JvmField
    val LITHIUM = HTMaterialBuilder.createMetal("lithium") {
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_INGOT,
                HTMaterialFlag.GENERATE_NUGGET,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    //    4th Period    //

    @JvmField
    val IRON = HTMaterialBuilder.createMetal("iron") {
        modifyInfo {
            color = Blocks.IRON_BLOCK.defaultMapColor.color
            formula = "Fe"
        }
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
    val COPPER = HTMaterialBuilder.createMetal("copper") {
        modifyInfo {
            color = Blocks.COPPER_BLOCK.defaultMapColor.color
            formula = "Cu"
        }
        modifyProperties { getAs(HTPropertyKey.SOLID)?.harvestLevel = null }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    //    5th Period    //

    //    6th Period    //

    @JvmField
    val GOLD = HTMaterialBuilder.createMetal("gold") {
        modifyInfo {
            color = Blocks.GOLD_BLOCK.defaultMapColor.color
            formula = "Au"
        }
        modifyProperties {
            getAs(HTPropertyKey.SOLID)?.harvestLevel = null
        }
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