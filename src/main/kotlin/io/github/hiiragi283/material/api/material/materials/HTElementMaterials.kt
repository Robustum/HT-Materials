package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.formula.FormulaConvertible
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.material.property.HTSolidProperty
import net.minecraft.block.Blocks

@Suppress("unused")
object HTElementMaterials {

    //    1st Period    //

    @JvmField
    val HYDROGEN = HTMaterial.create("hydrogen") {
        modifyInfo {
            setColor(Blocks.BLUE_CONCRETE)
            formula = FormulaConvertible { "H" }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    @JvmField
    val HELIUM = HTMaterial.create("helium") {
        modifyInfo {
            setColor(Blocks.YELLOW_CONCRETE)
            formula = FormulaConvertible { "He" }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    //    2nd Period    //

    @JvmField
    val LITHIUM = HTMaterial.create("lithium") {
        modifyInfo {
            formula = FormulaConvertible { "Li" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val BERYLLIUM = HTMaterial.create("beryllium") {
        modifyInfo {
            setColor(Blocks.SLIME_BLOCK)
            formula = FormulaConvertible { "Be" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val CARBON = HTMaterial.create("carbon") {
        modifyInfo {
            setColor(Blocks.COAL_BLOCK)
            formula = FormulaConvertible { "C" }
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties { this += HTSolidProperty.createSolid() }
    }

    @JvmField
    val NITROGEN = HTMaterial.create("nitrogen") {
        modifyInfo {
            setColor(Blocks.ICE)
            formula = FormulaConvertible { "N" }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    @JvmField
    val OXYGEN = HTMaterial.create("oxygen") {
        modifyInfo {
            formula = FormulaConvertible { "O" }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }


    @JvmField
    val FLUORINE = HTMaterial.create("fluorine") {
        modifyInfo {
            setColor(Blocks.EMERALD_BLOCK)
            formula = FormulaConvertible { "F" }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    //    3rd Period    //

    @JvmField
    val ALUMINUM = HTMaterial.create("aluminum") {
        modifyInfo {
            formula = FormulaConvertible { "Al" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val SILICON = HTMaterial.create("silicon") {
        modifyInfo {
            setColor(Blocks.BLUE_TERRACOTTA)
            formula = FormulaConvertible { "Si" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    //    4th Period    //

    @JvmField
    val CALCIUM = HTMaterial.create("calcium") {
        modifyInfo {
            formula = FormulaConvertible { "Ca" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val TITANIUM = HTMaterial.create("titanium") {
        modifyInfo {
            setColor(Blocks.PINK_CONCRETE)
            formula = FormulaConvertible { "Ti" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val CHROMIUM = HTMaterial.create("chromium") {
        modifyInfo {
            color = 0xffc0cb
            formula = FormulaConvertible { "Cr" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val MANGANESE = HTMaterial.create("manganese") {
        modifyInfo {
            setColor(Blocks.GRAVEL)
            formula = FormulaConvertible { "Mn" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val IRON = HTMaterial.create("iron") {
        modifyInfo {
            formula = FormulaConvertible { "Fe" }
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val NICKEL = HTMaterial.create("nickel") {
        modifyInfo {
            setColor(Blocks.END_STONE)
            formula = FormulaConvertible { "Ni" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val COPPER = HTMaterial.create("copper") {
        modifyInfo {
            setColor(Blocks.COPPER_BLOCK)
            formula = FormulaConvertible { "Cu" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val ZINC = HTMaterial.create("zinc") {
        modifyInfo {
            setColor(Blocks.GLOW_LICHEN)
            formula = FormulaConvertible { "Zn" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    //    5th Period    //

    @JvmField
    val SILVER = HTMaterial.create("silver") {
        modifyInfo {
            setColor(Blocks.ICE)
            formula = FormulaConvertible { "Ag" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val CADMIUM = HTMaterial.create("cadmium") {
        modifyInfo {
            setColor(Blocks.OAK_PLANKS)
            formula = FormulaConvertible { "Cd" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val TIN = HTMaterial.create("tin") {
        modifyInfo {
            setColor(Blocks.CLAY)
            formula = FormulaConvertible { "Sn" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val ANTIMONY = HTMaterial.create("antimony") {
        modifyInfo {
            formula = FormulaConvertible { "Sb" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    //    6th Period    //

    @JvmField
    val TUNGSTEN = HTMaterial.create("tungsten") {
        modifyInfo {
            color = 0x778899
            formula = FormulaConvertible { "W" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val IRIDIUM = HTMaterial.create("iridium") {
        modifyInfo { formula = FormulaConvertible { "Ir" } }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val PLATINUM = HTMaterial.create("platinum") {
        modifyInfo {
            color = 0x87cefa
            formula = FormulaConvertible { "Pt" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val GOLD = HTMaterial.create("gold") {
        modifyInfo {
            color = Blocks.GOLD_BLOCK.defaultMapColor.color
            formula = FormulaConvertible { "Au" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val LEAD = HTMaterial.create("lead") {
        modifyInfo {
            setColor(Blocks.BLUE_TERRACOTTA)
            formula = FormulaConvertible { "Pb" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    //    7th Period    //

    @JvmField
    val URANIUM = HTMaterial.create("uranium") {
        modifyInfo {
            setColor(Blocks.LIME_CONCRETE)
            formula = FormulaConvertible { "U" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val PLUTONIUM = HTMaterial.create("plutonium") {
        modifyInfo {
            setColor(Blocks.MAGENTA_CONCRETE)
            formula = FormulaConvertible { "Pu" }
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
        modifyProperties(HTMaterialProperties::setMetal)
    }


}