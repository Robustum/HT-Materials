package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.formula.FormulaConvertible
import io.github.hiiragi283.material.api.material.formula.HTCommonFormula
import io.github.hiiragi283.material.api.material.property.HTFluidProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids

@Suppress("unused")
object HTVanillaMaterials {

    //    Fluids    //

    @JvmField
    val WATER = HTMaterial.create("water") {
        modifyInfo {
            setColor(Blocks.WATER)
            formula = FormulaConvertible.of(HTElementMaterials.HYDROGEN to 2, HTElementMaterials.OXYGEN to 1)
        }
        modifyProperties {
            this += HTFluidProperty().apply { this.fluid = Fluids.WATER }
        }
    }

    @JvmField
    val LAVA = HTMaterial.create("lava") {
        modifyInfo {
            setColor(Blocks.LAVA)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyProperties {
            this += HTFluidProperty().apply { this.fluid = Fluids.LAVA }
        }
    }

    //    Gems    //

    @JvmField
    val AMETHYST = HTMaterial.create("amethyst") {
        modifyInfo {
            setColor(Blocks.AMETHYST_BLOCK)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties {
            setGem()
            setHarvestLevel(0)
        }
    }

    @JvmField
    val DIAMOND = HTMaterial.create("diamond") {
        modifyInfo {
            setColor(Blocks.DIAMOND_BLOCK)
            formula = HTElementMaterials.CARBON
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties {
            setGem()
            setHarvestLevel(3)
        }
    }

    @JvmField
    val ENDER_PEARL = HTMaterial.create("ender_pearl") {
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST
            )
        }
        modifyProperties {
            setGem()
        }
    }

    @JvmField
    val EMERALD = HTMaterial.create("emerald") {
        modifyInfo {
            setColor(Blocks.EMERALD_BLOCK)
            formula
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties {
            setGem()
            setHarvestLevel(2)
        }
    }

    @JvmField
    val FLINT = HTMaterial.create("flint")

    @JvmField
    val LAPIS = HTMaterial.create("lapis") {
        modifyInfo {
            setColor(Blocks.LAPIS_BLOCK)
            formula
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties {
            setGem()
            setHarvestLevel(1)
        }
    }

    @JvmField
    val PRISMARINE = HTMaterial.create("prismarine") {
        modifyInfo {
            setColor(Blocks.PRISMARINE)
            formula
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties {
            setGem()
            setHarvestLevel(0)
        }
    }

    @JvmField
    val QUARTZ = HTMaterial.create("quartz") {
        modifyInfo {
            setColor(Blocks.QUARTZ_BLOCK)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
        modifyProperties {
            setGem()
            setHarvestLevel(0)
        }
    }

    //    Metals    //

    @JvmField
    val NETHERITE = HTMaterial.create("netherite") {
        modifyInfo {
            setColor(Blocks.DEEPSLATE)
            formula = FormulaConvertible { "Nr" }
        }
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = 3 }
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

    //    Solids    //

    @JvmField
    val BRICK = HTMaterial.create("brick") {
        modifyInfo {
            setColor(Blocks.BRICKS)
            formula
            ingotPerBlock = 4
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val CHARCOAL = HTMaterial.create("charcoal") {
        modifyInfo {
            setColor(Blocks.COAL_BLOCK)
            formula = HTElementMaterials.CARBON
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val CLAY = HTMaterial.create("clay") {
        modifyInfo {
            setColor(Blocks.CLAY)
            formula
            ingotPerBlock = 4
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val COAL = HTMaterial.create("coal") {
        modifyInfo {
            setColor(Blocks.COAL_BLOCK)
            formula = HTElementMaterials.CARBON
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val CORAL = HTMaterial.create("coral") {
        modifyInfo {
            setColor(Blocks.DEAD_FIRE_CORAL)
            formula
        }
        //modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val GLASS = HTMaterial.create("glass") {
        modifyInfo {
            formula = HTCommonFormula.SILICON_DIOXIDE
            ingotPerBlock = 1
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val GLOWSTONE = HTMaterial.create("glowstone") {
        modifyInfo {
            setColor(Blocks.GLOWSTONE)
            formula
            ingotPerBlock = 4
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val NETHER_BRICK = HTMaterial.create("nether_brick") {
        modifyInfo {
            setColor(Blocks.NETHER_BRICKS)
            formula
            ingotPerBlock = 4
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    @JvmField
    val REDSTONE = HTMaterial.create("redstone") {
        modifyInfo {
            setColor(Blocks.REDSTONE_BLOCK)
            formula
        }
        modifyProperties(HTMaterialProperties::setSolid)
    }

    //    Stones    //

    @JvmField
    val STONE = HTMaterial.create("stone") {
        modifyInfo {
            setColor(Blocks.STONE)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
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
            setColor(Blocks.GRANITE)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val DIORITE = HTMaterial.create("diorite") {
        modifyInfo {
            setColor(Blocks.DIORITE)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val ANDESITE = HTMaterial.create("andesite") {
        modifyInfo {
            setColor(Blocks.ANDESITE)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val DEEPSLATE = HTMaterial.create("deepslate") {
        modifyInfo {
            setColor(Blocks.DEEPSLATE)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val CALCITE = HTMaterial.create("calcite") {
        modifyInfo {
            formula = FormulaConvertible.of(HTElementMaterials.CALCIUM to 1, HTCommonFormula.CARBONATE to 1)
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val TUFF = HTMaterial.create("tuff") {
        modifyInfo {
            color = 0x4d5d53
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val DRIPSTONE = HTMaterial.create("dripstone") {
        modifyInfo {
            setColor(Blocks.DRIPSTONE_BLOCK)
            formula = FormulaConvertible.of(CALCITE to 1)
        }
        modifyFlags {
            addFlags(HTMaterialFlag.GENERATE_DUST)
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val OBSIDIAN = HTMaterial.create("obsidian") {
        modifyInfo {
            color = 0x4b0082
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val NETHERRACK = HTMaterial.create("netherrack") {
        modifyInfo {
            setColor(Blocks.NETHERRACK)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val BASALT = HTMaterial.create("basalt") {
        modifyInfo {
            setColor(Blocks.BASALT)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
        modifyProperties(HTMaterialProperties::setStone)
    }

    @JvmField
    val END_STONE = HTMaterial.create("end_stone") {
        modifyInfo {
            setColor(Blocks.END_STONE)
            formula = HTCommonFormula.SILICON_DIOXIDE
        }
        modifyFlags {
            addFlags(
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
            setColor(Blocks.OAK_PLANKS)
            formula = FormulaConvertible { "C, H, O" }
        }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_GEAR
            )
        }
        modifyProperties(HTMaterialProperties::setWood)
    }

}