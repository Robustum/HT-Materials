package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTGemProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.common.util.HTColor

object HTCommonMaterials {

    //    Fluids    //

    //    Gems    //

    @JvmField
    val CINNABAR = HTMaterial.create("cinnabar") {
        modifyInfo {
            color = ColorConvertible { HTColor.RED }
            formula = FormulaConvertible.of(HTElementMaterials.MERCURY to 1, HTElementMaterials.SULFUR to 1)
            molarMass = MolarMassConvertible.of(HTElementMaterials.MERCURY to 1, HTElementMaterials.SULFUR to 1)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEM
            )
        }
        modifyProperties { setGem(HTGemProperty.Type.EMERALD) }
    }

    @JvmField
    val COKE = HTMaterial.create("coke") {
        modifyInfo {
            color = ColorConvertible { HTColor.DARK_GRAY }
            formula = HTElementMaterials.CARBON
            molarMass = HTElementMaterials.CARBON
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_GEM,
                HTMaterialFlag.GENERATE_DUST,
            )
        }
        modifyProperties { setGem(HTGemProperty.Type.COAL) }
    }

    @JvmField
    val OLIVINE = HTMaterial.create("olivine") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.GREEN)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEM
            )
        }
        modifyProperties { setGem(HTGemProperty.Type.EMERALD) }
    }

    @JvmField
    val PERIDOT = HTMaterial.create("peridot") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GREEN, HTColor.WHITE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEM
            )
        }
        modifyProperties { setGem(HTGemProperty.Type.RUBY) }
    }

    @JvmField
    val RUBY = HTMaterial.create("ruby") {
        modifyInfo {
            color = ColorConvertible { HTColor.RED }
            formula = FormulaConvertible.of(*HTAtomicGroups.ALUMINUM_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.ALUMINUM_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEM,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties { setGem(HTGemProperty.Type.RUBY) }
    }

    @JvmField
    val SALT = HTMaterial.create("salt") {
        modifyInfo {
            formula = FormulaConvertible.of(HTElementMaterials.SODIUM to 1, HTElementMaterials.CHLORINE to 1)
            molarMass = MolarMassConvertible.of(HTElementMaterials.SODIUM to 1, HTElementMaterials.CHLORINE to 1)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEM,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties { setGem(HTGemProperty.Type.CUBIC) }
    }

    @JvmField
    val SAPPHIRE = HTMaterial.create("sapphire") {
        modifyInfo {
            color = ColorConvertible { HTColor.BLUE }
            formula = FormulaConvertible.of(*HTAtomicGroups.ALUMINUM_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.ALUMINUM_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEM,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties { setGem(HTGemProperty.Type.RUBY) }
    }

    //    Metal    //

    @JvmField
    val BRASS = HTMaterial.create("brass") {
        modifyInfo {
            color = ColorConvertible { HTColor.GOLD }
            formula = FormulaConvertible.of(HTElementMaterials.COPPER to 3, HTElementMaterials.ZINC to 1)
            molarMass = MolarMassConvertible.of(HTElementMaterials.COPPER to 3, HTElementMaterials.ZINC to 1)
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
    val BRONZE = HTMaterial.create("bronze") {
        modifyInfo {
            color = ColorConvertible.of(HTElementMaterials.COPPER to 3, HTElementMaterials.TIN to 1)
            formula = FormulaConvertible.of(HTElementMaterials.COPPER to 3, HTElementMaterials.TIN to 1)
            molarMass = MolarMassConvertible.of(HTElementMaterials.COPPER to 3, HTElementMaterials.TIN to 1)
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
    val ELECTRUM = HTMaterial.create("electrum") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
            formula = FormulaConvertible.of(HTElementMaterials.SILVER to 1, HTElementMaterials.GOLD to 1)
            molarMass = MolarMassConvertible.of(HTElementMaterials.SILVER to 1, HTElementMaterials.GOLD to 1)
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
    val INVAR = HTMaterial.create("invar") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
            formula = FormulaConvertible.of(HTElementMaterials.IRON to 1, HTElementMaterials.NICKEL to 2)
            molarMass = MolarMassConvertible.of(HTElementMaterials.IRON to 1, HTElementMaterials.NICKEL to 2)
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
    val STAINLESS_STEEL = HTMaterial.create("stainless_steel") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GRAY, HTColor.WHITE)
            formula = FormulaConvertible.of(
                HTElementMaterials.IRON to 6,
                HTElementMaterials.CHROMIUM to 1,
                HTElementMaterials.MANGANESE to 1,
                HTElementMaterials.NICKEL to 1
            )
            molarMass = MolarMassConvertible.of(
                HTElementMaterials.IRON to 6,
                HTElementMaterials.CHROMIUM to 1,
                HTElementMaterials.MANGANESE to 1,
                HTElementMaterials.NICKEL to 1
            )
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
    val STEEL = HTMaterial.create("steel") {
        modifyInfo {
            color = ColorConvertible { HTColor.DARK_GRAY }
            formula = FormulaConvertible { "Fe, C" }
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
    val TUNGSTEN_STEEL = HTMaterial.create("tungstensteel") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_BLUE to 1, HTColor.DARK_GRAY to 3)
            formula = FormulaConvertible.of(HTElementMaterials.IRON to 1, HTElementMaterials.TUNGSTEN to 1)
            molarMass = MolarMassConvertible.of(HTElementMaterials.IRON to 1, HTElementMaterials.TUNGSTEN to 1)
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

    //    Solids    //

    @JvmField
    val ASHES = HTMaterial.create("ashes") {
        modifyInfo {
            color = ColorConvertible { HTColor.DARK_GRAY }
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
            )
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val BAUXITE = HTMaterial.create("bauxite") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1)
            //formula = FormulaConvertible.of(FormulaConvertible.of(*HTAtomicGroups.ALUMINUM_OXIDE) to 1)
            //molarMass
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
            )
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val RUBBER = HTMaterial.create("rubber") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_BLOCk,
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_INGOT,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    //    Stones    //

    @JvmField
    val MARBLE = HTMaterial.create("marble") {
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST
            )
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    //    Woods    //

}