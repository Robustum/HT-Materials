package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.common.util.HTColor
import java.awt.Color

object HTElementMaterials {

    //    1st Period    //

    @JvmField
    val HYDROGEN = HTMaterial.create("hydrogen") {
        modifyInfo {
            color = ColorConvertible { HTColor.BLUE }
            formula = FormulaConvertible { "H" }
            molarMass = MolarMassConvertible { 1.0 }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    @JvmField
    val HELIUM = HTMaterial.create("helium") {
        modifyInfo {
            color = ColorConvertible { HTColor.YELLOW }
            formula = FormulaConvertible { "He" }
            molarMass = MolarMassConvertible { 4.0 }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    //    2nd Period    //

    @JvmField
    val LITHIUM = HTMaterial.create("lithium") {
        modifyInfo {
            color = ColorConvertible { HTColor.GRAY }
            formula = FormulaConvertible { "Li" }
            molarMass = MolarMassConvertible { 6.9 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val BERYLLIUM = HTMaterial.create("beryllium") {
        modifyInfo {
            color = ColorConvertible { HTColor.DARK_GREEN }
            formula = FormulaConvertible { "Be" }
            molarMass = MolarMassConvertible { 9.0 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val CARBON = HTMaterial.create("carbon") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
            formula = FormulaConvertible { "C" }
            molarMass = MolarMassConvertible { 12.0 }
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val NITROGEN = HTMaterial.create("nitrogen") {
        modifyInfo {
            color = ColorConvertible { HTColor.AQUA }
            formula = FormulaConvertible { "N" }
            molarMass = MolarMassConvertible { 14.0 }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    @JvmField
    val OXYGEN = HTMaterial.create("oxygen") {
        modifyInfo {
            formula = FormulaConvertible { "O" }
            molarMass = MolarMassConvertible { 16.0 }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    @JvmField
    val FLUORINE = HTMaterial.create("fluorine") {
        modifyInfo {
            color = ColorConvertible { HTColor.GREEN }
            formula = FormulaConvertible { "F" }
            molarMass = MolarMassConvertible { 19.0 }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    //    3rd Period    //

    @JvmField
    val SODIUM = HTMaterial.create("sodium") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_BLUE to 1, HTColor.BLUE to 4)
            formula = FormulaConvertible { "Na" }
            molarMass = MolarMassConvertible { 23.0 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val MAGNESIUM = HTMaterial.create("magnesium") {
        modifyInfo {
            color = ColorConvertible { HTColor.GRAY }
            formula = FormulaConvertible { "Mg" }
            molarMass = MolarMassConvertible { 24.3 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val ALUMINUM = HTMaterial.create("aluminum") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLUE to 1, HTColor.WHITE to 5)
            formula = FormulaConvertible { "Al" }
            molarMass = MolarMassConvertible { 27.0 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible.ofColor(HTColor.BLACK to 2, HTColor.GRAY to 1, HTColor.BLUE to 1)
            formula = FormulaConvertible { "Si" }
            molarMass = MolarMassConvertible { 28.1 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val PHOSPHORUS = HTMaterial.create("phosphorus") {
        modifyInfo {
            color = ColorConvertible { HTColor.YELLOW }
            formula = FormulaConvertible { "P" }
            molarMass = MolarMassConvertible { 31.0 }
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val SULFUR = HTMaterial.create("sulfur") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW)
            formula = FormulaConvertible { "S" }
            molarMass = MolarMassConvertible { 32.1 }
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val CHLORINE = HTMaterial.create("chlorine") {
        modifyInfo {
            color = ColorConvertible { HTColor.YELLOW }
            formula = FormulaConvertible { "Cl" }
            molarMass = MolarMassConvertible { 35.5 }
        }
        modifyProperties {
            setFluid { it.attribute.isGas = true }
        }
    }

    //    4th Period    //

    @JvmField
    val POTASSIUM = HTMaterial.create("potassium") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_BLUE to 2, HTColor.BLUE to 3)
            formula = FormulaConvertible { "K" }
            molarMass = MolarMassConvertible { 39.1 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val CALCIUM = HTMaterial.create("calcium") {
        modifyInfo {
            color = ColorConvertible { HTColor.GRAY }
            formula = FormulaConvertible { "Ca" }
            molarMass = MolarMassConvertible { 40.1 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val TITANIUM = HTMaterial.create("titanium") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GOLD to 1, HTColor.WHITE to 2)
            formula = FormulaConvertible { "Ti" }
            molarMass = MolarMassConvertible { 47.9 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible { HTColor.GREEN }
            formula = FormulaConvertible { "Cr" }
            molarMass = MolarMassConvertible { 52.0 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val MANGANESE = HTMaterial.create("manganese") {
        modifyInfo {
            color = ColorConvertible { HTColor.GRAY }
            formula = FormulaConvertible { "Mn" }
            molarMass = MolarMassConvertible { 54.9 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val IRON = HTMaterial.create("iron") {
        modifyInfo {
            formula = FormulaConvertible { "Fe" }
            molarMass = MolarMassConvertible { 55.8 }
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val COBALT = HTMaterial.create("cobalt") {
        modifyInfo {
            color = ColorConvertible { HTColor.BLUE }
            formula = FormulaConvertible { "Co" }
            molarMass = MolarMassConvertible { 58.9 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val NICKEL = HTMaterial.create("nickel") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GOLD to 2, HTColor.GREEN to 1, HTColor.WHITE to 1)
            formula = FormulaConvertible { "Ni" }
            molarMass = MolarMassConvertible { 58.7 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible.ofColor(HTColor.GOLD, HTColor.RED, HTColor.WHITE)
            formula = FormulaConvertible { "Cu" }
            molarMass = MolarMassConvertible { 63.5 }
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_NUGGET,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties {
            setMetal()
            setHarvestLevel(0)
        }
    }

    @JvmField
    val ZINC = HTMaterial.create("zinc") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GREEN to 1, HTColor.WHITE to 2)
            formula = FormulaConvertible { "Zn" }
            molarMass = MolarMassConvertible { 65.4 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible.ofColor(HTColor.AQUA to 1, HTColor.WHITE to 3)
            formula = FormulaConvertible { "Ag" }
            molarMass = MolarMassConvertible { 107.9 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible.ofColor(HTColor.BLUE to 1, HTColor.AQUA to 1, HTColor.WHITE to 3)
            formula = FormulaConvertible { "Sn" }
            molarMass = MolarMassConvertible { 118.7 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible.ofColor(HTColor.BLACK to 2, HTColor.DARK_GRAY to 1)
            formula = FormulaConvertible { "W" }
            molarMass = MolarMassConvertible { 183.8 }
        }
        modifyFlags {
            addAll(
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
        modifyInfo {
            formula = FormulaConvertible { "Ir" }
            molarMass = MolarMassConvertible { 192.2 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible { Color(0x87cefa) }
            formula = FormulaConvertible { "Pt" }
            molarMass = MolarMassConvertible { 195.1 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW)
            formula = FormulaConvertible { "Au" }
            molarMass = MolarMassConvertible { 197.0 }
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties {
            setMetal()
            setHarvestLevel(0)
        }
    }

    @JvmField
    val MERCURY = HTMaterial.create("mercury") {
        modifyInfo {
            formula = FormulaConvertible { "Hg" }
            molarMass = MolarMassConvertible { 200.6 }
        }
        modifyProperties {
            setFluid {}
        }
    }

    @JvmField
    val LEAD = HTMaterial.create("lead") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_BLUE, HTColor.DARK_GRAY, HTColor.WHITE)
            formula = FormulaConvertible { "Pb" }
            molarMass = MolarMassConvertible { 207.2 }
        }
        modifyFlags {
            addAll(
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
            color = ColorConvertible { HTColor.GREEN }
            formula = FormulaConvertible { "U" }
            molarMass = MolarMassConvertible { 238.0 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }

    @JvmField
    val PLUTONIUM = HTMaterial.create("plutonium") {
        modifyInfo {
            color = ColorConvertible { HTColor.RED }
            formula = FormulaConvertible { "Pu" }
            molarMass = MolarMassConvertible { 244.1 }
        }
        modifyProperties(HTMaterialProperties::setMetal)
    }


}