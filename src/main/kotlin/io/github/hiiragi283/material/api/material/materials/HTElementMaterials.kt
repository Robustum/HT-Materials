package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterialBuilder
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.formula.FormulaConvertible
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import net.minecraft.block.Blocks

@Suppress("unused")
object HTElementMaterials {

    //    1st Period    //

    @JvmField
    val HYDROGEN = HTMaterialBuilder.createFluid("hydrogen") {
        modifyInfo {
            setColor(Blocks.BLUE_CONCRETE)
            formula = FormulaConvertible.of("H")
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    @JvmField
    val HELIUM = HTMaterialBuilder.createFluid("helium") {
        modifyInfo {
            setColor(Blocks.YELLOW_CONCRETE)
            formula = FormulaConvertible.of("He")
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    //    2nd Period    //

    @JvmField
    val LITHIUM = HTMaterialBuilder.createMetal("lithium") {
        modifyInfo { formula = FormulaConvertible.of("Li") }
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
            formula = FormulaConvertible.of("Be")
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
            formula = FormulaConvertible.of("C")
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
            formula = FormulaConvertible.of("N")
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    @JvmField
    val OXYGEN = HTMaterialBuilder.createFluid("oxygen") {
        modifyInfo { formula = FormulaConvertible.of("O") }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }


    @JvmField
    val FLUORINE = HTMaterialBuilder.createFluid("fluorine") {
        modifyInfo {
            setColor(Blocks.EMERALD_BLOCK)
            formula = FormulaConvertible.of("F")
        }
        modifyProperties { get(HTPropertyKey.FLUID)?.attribute?.isGas = true }
    }

    //    3rd Period    //

    @JvmField
    val ALUMINUM = HTMaterialBuilder.createMetal("aluminum") {
        modifyInfo { formula = FormulaConvertible.of("Al") }
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
            formula = FormulaConvertible.of("Si")
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
    val CALCIUM = HTMaterialBuilder.createMetal("calcium") {
        modifyInfo { formula = FormulaConvertible.of("Ca") }
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
    val TITANIUM = HTMaterialBuilder.createMetal("titanium") {
        modifyInfo {
            setColor(Blocks.PINK_CONCRETE)
            formula = FormulaConvertible.of("Ti")
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
            formula = FormulaConvertible.of("Cr")
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
            formula = FormulaConvertible.of("Mn")
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
        modifyInfo { formula = FormulaConvertible.of("Fe") }
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
            formula = FormulaConvertible.of("Ni")
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
            formula = FormulaConvertible.of("Cu")
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
            formula = FormulaConvertible.of("Zn")
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
            formula = FormulaConvertible.of("Ag")
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
            formula = FormulaConvertible.of("Cd")
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
            formula = FormulaConvertible.of("Sn")
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
        modifyInfo { formula = FormulaConvertible.of("Sb") }
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
            formula = FormulaConvertible.of("W")
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
        modifyInfo { formula = FormulaConvertible.of("Ir") }
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
        modifyInfo { formula = FormulaConvertible.of("Pt") }
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
            formula = FormulaConvertible.of("Au")
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
            formula = FormulaConvertible.of("Pb")
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
            formula = FormulaConvertible.of("U")
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
            formula = FormulaConvertible.of("Pu")
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