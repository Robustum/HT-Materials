package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterialBuilder
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids

@Suppress("unused")
object HTVanillaMaterials {

    //    Fluids    //

    @JvmField
    val WATER = HTMaterialBuilder.createFluid("water") {
        modifyInfo {
            setColor(Blocks.WATER)
            formula
        }
        modifyProperties {
            get(HTPropertyKey.FLUID)?.apply { this.fluid = Fluids.WATER }
        }
    }

    @JvmField
    val LAVA = HTMaterialBuilder.createFluid("lava") {
        modifyInfo {
            setColor(Blocks.LAVA)
            formula
        }
        modifyProperties {
            get(HTPropertyKey.FLUID)?.apply { this.fluid = Fluids.LAVA }
        }
    }

    //    Gems    //

    @JvmField
    val AMETHYST = HTMaterialBuilder.createGem("amethyst") {
        modifyInfo {
            setColor(Blocks.AMETHYST_BLOCK)
            formula
        }
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = 0 }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_DUST,
                HTMaterialFlag.GENERATE_PLATE
            )
        }
    }

    @JvmField
    val DIAMOND = HTMaterialBuilder.createGem("diamond") {
        modifyInfo {
            setColor(Blocks.DIAMOND_BLOCK)
            formula = "C"
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
    }

    @JvmField
    val ENDER_PEARL = HTMaterialBuilder.createGem("ender_pearl")

    @JvmField
    val EMERALD = HTMaterialBuilder.createGem("emerald") {
        modifyInfo {
            setColor(Blocks.EMERALD_BLOCK)
            formula
        }
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = 2 }
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
    val LAPIS = HTMaterialBuilder.createGem("lapis") {
        modifyInfo {
            setColor(Blocks.LAPIS_BLOCK)
            formula
        }
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = 1 }
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
    val PRISMARINE = HTMaterialBuilder.createGem("prismarine") {
        modifyInfo {
            setColor(Blocks.PRISMARINE)
            formula
        }
        modifyProperties { get(HTPropertyKey.SOLID)?.harvestLevel = 0 }
        modifyFlags {
            addFlags(
                HTMaterialFlag.GENERATE_GEAR,
                HTMaterialFlag.GENERATE_PLATE,
                HTMaterialFlag.GENERATE_ROD
            )
        }
    }

    @JvmField
    val QUARTZ = HTMaterialBuilder.createGem("quartz") {
        modifyInfo {
            setColor(Blocks.QUARTZ_BLOCK)
            formula
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

    //    Metals    //

    @JvmField
    val NETHERITE = HTMaterialBuilder.createMetal("netherite") {
        modifyInfo {
            setColor(Blocks.DEEPSLATE)
            formula = "Nr"
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
    }

    //    Solids    //

    @JvmField
    val BRICK = HTMaterialBuilder.createSolid("brick") {
        modifyInfo {
            setColor(Blocks.BRICKS)
            formula
            ingotPerBlock = 4
        }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
    }

    @JvmField
    val CHARCOAL = HTMaterialBuilder.createSolid("charcoal") {
        modifyInfo {
            setColor(Blocks.COAL_BLOCK)
            formula = "C"
        }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
    }

    @JvmField
    val CLAY = HTMaterialBuilder.createSolid("clay") {
        modifyInfo {
            setColor(Blocks.CLAY)
            formula
            ingotPerBlock = 4
        }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
    }

    @JvmField
    val COAL = HTMaterialBuilder.createSolid("coal") {
        modifyInfo {
            setColor(Blocks.COAL_BLOCK)
            formula = "C"
        }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
    }

    @JvmField
    val CORAL = HTMaterialBuilder.createSolid("coral") {
        modifyInfo {
            setColor(Blocks.DEAD_FIRE_CORAL)
            formula
        }
        //modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
    }

    @JvmField
    val GLASS = HTMaterialBuilder.createSolid("glass") {
        modifyInfo {
            setColor(Blocks.GLASS)
            formula
            ingotPerBlock = 1
        }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
    }

    @JvmField
    val GLOWSTONE = HTMaterialBuilder.createSolid("glowstone") {
        modifyInfo {
            setColor(Blocks.GLOWSTONE)
            formula
            ingotPerBlock = 4
        }
    }

    @JvmField
    val NETHER_BRICK = HTMaterialBuilder.createSolid("nether_brick") {
        modifyInfo {
            setColor(Blocks.NETHER_BRICKS)
            formula
            ingotPerBlock = 4
        }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
    }

    @JvmField
    val REDSTONE = HTMaterialBuilder.createSolid("redstone") {
        modifyInfo {
            setColor(Blocks.REDSTONE_BLOCK)
            formula
        }
    }

    //    Stones    //

    @JvmField
    val STONE = HTMaterialBuilder.createStone("stone") {
        modifyInfo { setColor(Blocks.STONE) }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_GEAR) }
    }

    @JvmField
    val GRANITE = HTMaterialBuilder.createStone("granite") {
        modifyInfo { setColor(Blocks.GRANITE) }
    }

    @JvmField
    val DIORITE = HTMaterialBuilder.createStone("diorite") {
        modifyInfo { setColor(Blocks.DIORITE) }
    }

    @JvmField
    val ANDESITE = HTMaterialBuilder.createStone("andesite") {
        modifyInfo { setColor(Blocks.ANDESITE) }
    }

    @JvmField
    val DEEPSLATE = HTMaterialBuilder.createStone("deepslate") {
        modifyInfo { setColor(Blocks.DEEPSLATE) }
    }

    @JvmField
    val CALCITE = HTMaterialBuilder.createStone("calcite") {
        modifyInfo { setColor(Blocks.CALCITE) }
    }

    @JvmField
    val TUFF = HTMaterialBuilder.createStone("tuff") {
        modifyInfo { setColor(Blocks.TUFF) }
    }

    @JvmField
    val DRIPSTONE = HTMaterialBuilder.createStone("dripstone") {
        modifyInfo { setColor(Blocks.DRIPSTONE_BLOCK) }
    }

    @JvmField
    val OBSIDIAN = HTMaterialBuilder.createStone("obsidian") {
        modifyInfo { setColor(Blocks.OBSIDIAN) }
    }

    @JvmField
    val NETHERRACK = HTMaterialBuilder.createStone("netherrack") {
        modifyInfo { setColor(Blocks.NETHERRACK) }
    }

    @JvmField
    val BASALT = HTMaterialBuilder.createStone("basalt") {
        modifyInfo { setColor(Blocks.BASALT) }
    }

    @JvmField
    val END_STONE = HTMaterialBuilder.createStone("end_stone") {
        modifyInfo { setColor(Blocks.END_STONE) }
    }

    //    Woods    //

    @JvmField
    val WOOD = HTMaterialBuilder.createWood("wood") {
        modifyInfo { setColor(Blocks.OAK_PLANKS) }
        modifyFlags { addFlags(HTMaterialFlag.GENERATE_GEAR) }
    }

}