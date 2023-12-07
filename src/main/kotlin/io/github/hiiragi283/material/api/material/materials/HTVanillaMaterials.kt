package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTGemProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.common.util.HTColor
import net.minecraft.fluid.Fluids
import java.awt.Color

object HTVanillaMaterials {

    //    Fluids    //

    @JvmField
    val WATER = HTMaterial.create("water") {
        modifyInfo {
            color = ColorConvertible { HTColor.BLUE }
            formula = FormulaConvertible.of(HTElementMaterials.HYDROGEN to 2, HTElementMaterials.OXYGEN to 1)
            molarMass = MolarMassConvertible.of(HTElementMaterials.HYDROGEN to 2, HTElementMaterials.OXYGEN to 1)
        }
        modifyProperties {
            setFluid { it.fluid = Fluids.WATER }
        }
    }

    @JvmField
    val LAVA = HTMaterial.create("lava") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_RED, HTColor.GOLD)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyProperties {
            setFluid { it.fluid = Fluids.LAVA }
        }
    }

    //    Gems    //

    /*@JvmField
    val AMETHYST = HTMaterial.create("amethyst") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLUE, HTColor.LIGHT_PURPLE)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            ingotPerBlock = 4
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties {
            modifyProperties { setGem(HTGemProperty.Type.AMETHYST) }
            setHarvestLevel(0)
        }
    }*/

    @JvmField
    val DIAMOND = HTMaterial.create("diamond") {
        modifyInfo {
            color = ColorConvertible { HTColor.AQUA }
            formula = HTElementMaterials.CARBON
            molarMass = HTElementMaterials.CARBON
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
            modifyProperties { setGem(HTGemProperty.Type.DIAMOND) }
            setHarvestLevel(3)
        }
    }

    @JvmField
    val ENDER_PEARL = HTMaterial.create("ender_pearl") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.BLUE)
            ingotPerBlock = 4
        }
    }

    @JvmField
    val EMERALD = HTMaterial.create("emerald") {
        modifyInfo {
            color = ColorConvertible { HTColor.GREEN }
            formula = FormulaConvertible.of(
                HTElementMaterials.BERYLLIUM to 3,
                HTElementMaterials.ALUMINUM to 2,
                HTElementMaterials.SILICON to 6,
                HTElementMaterials.OXYGEN to 18
            )
            molarMass = MolarMassConvertible.of(
                HTElementMaterials.BERYLLIUM to 3,
                HTElementMaterials.ALUMINUM to 2,
                HTElementMaterials.SILICON to 6,
                HTElementMaterials.OXYGEN to 18
            )
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
            modifyProperties { setGem(HTGemProperty.Type.EMERALD) }
            setHarvestLevel(2)
        }
    }

    @JvmField
    val FLINT = HTMaterial.create("flint") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK to 3, HTColor.BLUE to 1)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val LAPIS = HTMaterial.create("lapis") {
        modifyInfo {
            color = ColorConvertible { HTColor.BLUE }
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
            modifyProperties { setGem(HTGemProperty.Type.LAPIS) }
            setHarvestLevel(1)
        }
    }

    @JvmField
    val PRISMARINE = HTMaterial.create("prismarine") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GREEN, HTColor.AQUA, HTColor.WHITE)
        }
        modifyProperties {
            setHarvestLevel(0)
        }
    }

    @JvmField
    val QUARTZ = HTMaterial.create("quartz") {
        modifyInfo {
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            ingotPerBlock = 4
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
            modifyProperties { setGem(HTGemProperty.Type.QUARTZ) }
            setHarvestLevel(0)
        }
    }

    //    Metals    //

    @JvmField
    val NETHERITE = HTMaterial.create("netherite") {
        modifyInfo {
            color = ColorConvertible.ofColor(
                HTColor.BLACK to 5,
                HTColor.DARK_BLUE to 1,
                HTColor.DARK_RED to 1,
                HTColor.YELLOW to 1
            )
            formula = FormulaConvertible { "Nr" }
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
            setHarvestLevel(3)
        }
    }

    //    Solids    //

    @JvmField
    val BRICK = HTMaterial.create("brick") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_RED to 2, HTColor.GOLD to 1, HTColor.DARK_GRAY to 2)
            ingotPerBlock = 4
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val CHARCOAL = HTMaterial.create("charcoal") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK to 7, HTColor.YELLOW to 1)
            formula = HTElementMaterials.CARBON
            molarMass = HTElementMaterials.CARBON
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val CLAY = HTMaterial.create("clay") {
        modifyInfo {
            color = ColorConvertible { Color(0xa4a8b8) }
            ingotPerBlock = 4
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val COAL = HTMaterial.create("coal") {
        modifyInfo {
            color = HTElementMaterials.CARBON
            formula = HTElementMaterials.CARBON
            molarMass = HTElementMaterials.CARBON
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val CORAL = HTMaterial.create("coral") {
        modifyInfo {
            color = ColorConvertible { HTColor.DARK_GRAY }
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val GLASS = HTMaterial.create("glass") {
        modifyInfo {
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            ingotPerBlock = 1
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val GLOWSTONE = HTMaterial.create("glowstone") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.GOLD to 1, HTColor.YELLOW to 2)
            ingotPerBlock = 4
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val NETHER_BRICK = HTMaterial.create("nether_brick") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.WHITE to 1)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            ingotPerBlock = 4
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val REDSTONE = HTMaterial.create("redstone") {
        modifyInfo {
            color = ColorConvertible { HTColor.DARK_RED }
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    //    Stones    //

    @JvmField
    val STONE = HTMaterial.create("stone") {
        modifyInfo {
            color = ColorConvertible { HTColor.DARK_GRAY }
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val GRANITE = HTMaterial.create("granite") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_RED to 1, HTColor.GRAY to 4, HTColor.RED to 1)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val DIORITE = HTMaterial.create("diorite") {
        modifyInfo {
            ColorConvertible { HTColor.GRAY }
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val ANDESITE = HTMaterial.create("andesite") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_GRAY to 7, HTColor.YELLOW to 1)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    /*@JvmField
    val DEEPSLATE = HTMaterial.create("deepslate") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val CALCITE = HTMaterial.create("calcite") {
        modifyInfo {
            formula = FormulaConvertible.of(
                HTElementMaterials.CALCIUM to 1,
                FormulaConvertible.of(*HTAtomicGroups.CARBONATE) to 1
            )
            molarMass = MolarMassConvertible.of(
                HTElementMaterials.CALCIUM to 1,
                MolarMassConvertible.of(*HTAtomicGroups.CARBONATE) to 1
            )
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setStone)
    }*/

    /*@JvmField
    val TUFF = HTMaterial.create("tuff") {
        modifyInfo {
            color = ColorConvertible { Color(0x4d5d53) }
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setStone)
    }*/

    /*@JvmField
    val DRIPSTONE = HTMaterial.create("dripstone") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_RED to 1, HTColor.GOLD to 1, HTColor.DARK_GRAY to 5)
            formula = FormulaConvertible.of(CALCITE to 1)
            molarMass = MolarMassConvertible.of(CALCITE to 1)
        }
        modifyFlags {
            addAll(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setStone)
    }*/

    @JvmField
    val OBSIDIAN = HTMaterial.create("obsidian") {
        modifyInfo {
            color = ColorConvertible.ofColor(
                HTColor.BLACK to 4,
                HTColor.DARK_BLUE to 2,
                HTColor.DARK_RED to 1,
                HTColor.WHITE to 1
            )
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val NETHERRACK = HTMaterial.create("netherrack") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.RED to 3)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val BASALT = HTMaterial.create("basalt") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.BLACK, HTColor.GRAY)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val END_STONE = HTMaterial.create("end_stone") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.YELLOW to 1, HTColor.WHITE to 3)
            formula = FormulaConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
            molarMass = MolarMassConvertible.of(*HTAtomicGroups.SILICON_OXIDE)
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    //    Woods    //

    @JvmField
    val WOOD = HTMaterial.create("wood") {
        modifyInfo {
            color = ColorConvertible.ofColor(HTColor.DARK_GRAY to 2, HTColor.RED to 1, HTColor.YELLOW to 1)
            formula = FormulaConvertible { "C, H, O" }
        }
        modifyFlags {
            addAll(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR
            )
        }
        modifyProperties(HTMaterialProperties::setWood)
    }

}