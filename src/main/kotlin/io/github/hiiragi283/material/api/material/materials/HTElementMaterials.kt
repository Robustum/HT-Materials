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
        modifyInfo {
            setColor(Blocks.BLUE_CONCRETE)
            formula = "H"
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    @JvmField
    val HELIUM = HTMaterialBuilder.createFluid("helium") {
        modifyInfo {
            setColor(Blocks.YELLOW_CONCRETE)
            formula = "He"
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    //    2nd Period    //

    @JvmField
    val LITHIUM = HTMaterialBuilder.createMetal("lithium") {
        modifyInfo { formula = "Li" }
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

    @JvmField
    val BERYLLIUM = HTMaterialBuilder.createMetal("beryllium") {
        modifyInfo {
            setColor(Blocks.SLIME_BLOCK)
            formula = "Be"
        }
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

    @JvmField
    val CARBON = HTMaterialBuilder.createSolid("carbon") {
        modifyInfo {
            setColor(Blocks.COAL_BLOCK)
            formula = "C"
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    @JvmField
    val NITROGEN = HTMaterialBuilder.createFluid("nitrogen") {
        modifyInfo {
            setColor(Blocks.ICE)
            formula = "N"
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    @JvmField
    val OXYGEN = HTMaterialBuilder.createFluid("oxygen") {
        modifyInfo {
            formula = "O"
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }


    @JvmField
    val FLUORINE = HTMaterialBuilder.createFluid("fluorine") {
        modifyInfo {
            setColor(Blocks.EMERALD_BLOCK)
            formula = "F"
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    //    3rd Period    //

    @JvmField
    val ALUMINUM = HTMaterialBuilder.createMetal("aluminum") {
        modifyInfo { formula = "Al" }
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

    @JvmField
    val SILICON = HTMaterialBuilder.createMetal("silicon") {
        modifyInfo {
            setColor(Blocks.BLUE_TERRACOTTA)
            formula = "Si"
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_INGOT,
                HTMaterialFlag.GENERATE_NUGGET,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    //    4th Period    //

    @JvmField
    val TITANIUM = HTMaterialBuilder.createMetal("titanium") {
        modifyInfo {
            setColor(Blocks.PINK_CONCRETE)
            formula = "Ti"
        }
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

    @JvmField
    val CHROMIUM = HTMaterialBuilder.createMetal("chromium") {
        modifyInfo {
            color = 0xffc0cb
            formula = "Cr"
        }
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

    @JvmField
    val MANGANESE = HTMaterialBuilder.createMetal("manganese") {
        modifyInfo {
            setColor(Blocks.GRAVEL)
            formula = "Mn"
        }
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

    @JvmField
    val IRON = HTMaterialBuilder.createMetal("iron") {
        modifyInfo { formula = "Fe" }
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
    val NICKEL = HTMaterialBuilder.createMetal("nickel") {
        modifyInfo {
            setColor(Blocks.END_STONE)
            formula = "Ni"
        }
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

    @JvmField
    val COPPER = HTMaterialBuilder.createMetal("copper") {
        modifyInfo {
            setColor(Blocks.COPPER_BLOCK)
            formula = "Cu"
        }
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = 0 }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_NUGGET,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    @JvmField
    val ZINC = HTMaterialBuilder.createMetal("zinc") {
        modifyInfo {
            setColor(Blocks.GLOW_LICHEN)
            formula = "Zn"
        }
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

    //    5th Period    //

    @JvmField
    val SILVER = HTMaterialBuilder.createMetal("silver") {
        modifyInfo {
            setColor(Blocks.ICE)
            formula = "Ag"
        }
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

    @JvmField
    val CADMIUM = HTMaterialBuilder.createMetal("cadmium") {
        modifyInfo {
            setColor(Blocks.OAK_PLANKS)
            formula = "Cd"
        }
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

    @JvmField
    val TIN = HTMaterialBuilder.createMetal("tin") {
        modifyInfo {
            setColor(Blocks.CLAY)
            formula = "Sn"
        }
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

    @JvmField
    val ANTIMONY = HTMaterialBuilder.createMetal("antimony") {
        modifyInfo { formula = "Sb" }
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

    //    6th Period    //

    @JvmField
    val TUNGSTEN = HTMaterialBuilder.createMetal("tungsten") {
        modifyInfo {
            setColor(Blocks.IRON_BLOCK)
            formula = "T"
        }
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

    @JvmField
    val IRIDIUM = HTMaterialBuilder.createMetal("iridium") {
        modifyInfo { formula = "Ir" }
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

    @JvmField
    val PLATINUM = HTMaterialBuilder.createMetal("platinum") {
        modifyInfo { formula = "Pt" }
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

    @JvmField
    val GOLD = HTMaterialBuilder.createMetal("gold") {
        modifyInfo {
            color = Blocks.GOLD_BLOCK.defaultMapColor.color
            formula = "Au"
        }
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = 0 }
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
    val LEAD = HTMaterialBuilder.createMetal("lead") {
        modifyInfo {
            setColor(Blocks.BLUE_TERRACOTTA)
            formula = "Pb"
        }
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

    //    7th Period    //

    @JvmField
    val URANIUM = HTMaterialBuilder.createMetal("uranium") {
        modifyInfo {
            setColor(Blocks.LIME_CONCRETE)
            formula = "U"
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_INGOT,
                HTMaterialFlag.GENERATE_NUGGET,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    @JvmField
    val PLUTONIUM = HTMaterialBuilder.createMetal("plutonium") {
        modifyInfo {
            setColor(Blocks.MAGENTA_CONCRETE)
            formula = "Pu"
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_INGOT,
                HTMaterialFlag.GENERATE_NUGGET,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }


}