package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterialBuilder
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import net.minecraft.block.Blocks

@Suppress("unused")
object HTElementMaterials {

    //    1st Period    //

    @JvmField
    val HYDROGEN = HTMaterialBuilder.createFluid("hydrogen") {
        modifyInfo { color = Blocks.BLUE_CONCRETE.defaultMapColor.color }
    }

    @JvmField
    val HELIUM = HTMaterialBuilder.createFluid("helium") {
        modifyInfo { color = Blocks.YELLOW_CONCRETE.defaultMapColor.color }
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
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = null }
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
            get(HTPropertyKey.SOLID)?.harvestLevel = null
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