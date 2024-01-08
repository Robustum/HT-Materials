package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.ColorConvertible
import io.github.hiiragi283.material.api.material.FormulaConvertible
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.MolarMassConvertible
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlags
import io.github.hiiragi283.material.api.material.property.*
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.util.HTColor
import java.awt.Color

object HTVanillaMaterials : HTMaterialsAddon {

    //    Fluids    //

    @JvmField
    val WATER = HTMaterialKey("water")

    @JvmField
    val LAVA = HTMaterialKey("lava")

    //    Gems    //

    @JvmField
    val AMETHYST = HTMaterialKey("amethyst")

    @JvmField
    val DIAMOND = HTMaterialKey("diamond")

    @JvmField
    val ENDER_PEARL = HTMaterialKey("ender_pearl")

    @JvmField
    val EMERALD = HTMaterialKey("emerald")

    @JvmField
    val FLINT = HTMaterialKey("flint")

    @JvmField
    val LAPIS = HTMaterialKey("lapis")

    @JvmField
    val QUARTZ = HTMaterialKey("quartz")

    //    Metals    //

    @JvmField
    val NETHERITE = HTMaterialKey("netherite")

    //    Solids    //

    @JvmField
    val BRICK = HTMaterialKey("brick")

    @JvmField
    val CHARCOAL = HTMaterialKey("charcoal")

    @JvmField
    val CLAY = HTMaterialKey("clay")

    @JvmField
    val COAL = HTMaterialKey("coal")

    @JvmField
    val GLASS = HTMaterialKey("glass")

    @JvmField
    val GLOWSTONE = HTMaterialKey("glowstone")

    @JvmField
    val NETHER_BRICK = HTMaterialKey("nether_brick")

    @JvmField
    val REDSTONE = HTMaterialKey("redstone")

    //    Stones    //

    @JvmField
    val STONE = HTMaterialKey("stone")

    @JvmField
    val GRANITE = HTMaterialKey("granite")

    @JvmField
    val DIORITE = HTMaterialKey("diorite")

    @JvmField
    val ANDESITE = HTMaterialKey("andesite")

    @JvmField
    val DEEPSLATE = HTMaterialKey("deepslate")

    @JvmField
    val CALCITE = HTMaterialKey("calcite")

    @JvmField
    val TUFF = HTMaterialKey("tuff")

    @JvmField
    val OBSIDIAN = HTMaterialKey("obsidian")

    @JvmField
    val NETHERRACK = HTMaterialKey("netherrack")

    @JvmField
    val BASALT = HTMaterialKey("basalt")

    @JvmField
    val END_STONE = HTMaterialKey("stone")

    //    Woods    //

    @JvmField
    val WOOD = HTMaterialKey("wood")

    //    Register    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -90

    override fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialKey>) {
        //Fluids
        registry.addAll(
            WATER,
            LAVA
        )
        //Gems
        registry.addAll(
            AMETHYST,
            DIAMOND,
            ENDER_PEARL,
            EMERALD,
            FLINT,
            LAPIS,
            QUARTZ
        )
        //Metals
        registry.addAll(
            NETHERITE
        )
        //Solids
        registry.addAll(
            BRICK,
            CHARCOAL,
            CLAY,
            COAL,
            GLASS,
            GLOWSTONE,
            NETHER_BRICK,
            REDSTONE
        )
        //Stones
        registry.addAll(
            STONE,
            GRANITE,
            DIORITE,
            ANDESITE,
            DEEPSLATE,
            CALCITE,
            TUFF,
            OBSIDIAN,
            NETHERRACK,
            BASALT,
            END_STONE
        )
        //Woods
        registry.addAll(
            WOOD
        )
    }

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {
        //Fluids
        registry.getOrCreate(WATER)
            .add(HTCompoundProperty(HTElementMaterials.HYDROGEN to 2, HTElementMaterials.OXYGEN to 1))
        registry.getOrCreate(LAVA)
            .add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
        //Gems
        registry.getOrCreate(AMETHYST).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTGemProperty.AMETHYST)
        }
        registry.getOrCreate(DIAMOND).apply {
            add(HTCompoundProperty(HTElementMaterials.CARBON to 1))
            add(HTGemProperty.DIAMOND)
        }
        registry.getOrCreate(ENDER_PEARL)
        registry.getOrCreate(EMERALD).apply {
            add(
                HTCompoundProperty(
                    HTElementMaterials.BERYLLIUM to 3,
                    HTElementMaterials.ALUMINUM to 2,
                    HTElementMaterials.SILICON to 6,
                    HTElementMaterials.OXYGEN to 18
                )
            )
            add(HTGemProperty.EMERALD)
        }
        registry.getOrCreate(FLINT).apply {
            add(HTCompoundProperty(HTElementMaterials.CARBON to 1))
        }
        registry.getOrCreate(LAPIS).add(HTGemProperty.LAPIS)
        registry.getOrCreate(QUARTZ).apply {
            add(HTCompoundProperty(HTElementMaterials.CARBON to 1))
            add(HTGemProperty.DIAMOND)
        }
        //Metals
        registry.getOrCreate(NETHERITE).add(HTMetalProperty)
        //Solids
        registry.getOrCreate(BRICK)
        registry.getOrCreate(CHARCOAL).add(HTCompoundProperty(HTElementMaterials.CARBON to 1))
        registry.getOrCreate(CLAY)
        registry.getOrCreate(COAL).add(HTCompoundProperty(HTElementMaterials.CARBON to 1))
        registry.getOrCreate(GLASS).add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
        registry.getOrCreate(GLOWSTONE)
        registry.getOrCreate(NETHER_BRICK)
        registry.getOrCreate(REDSTONE)
        //Stones
        registry.getOrCreate(STONE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(GRANITE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(DIORITE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(ANDESITE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(DEEPSLATE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(CALCITE).apply {
            add(HTCompoundProperty(HTElementMaterials.CALCIUM to 1, *HTAtomicGroups.CARBONATE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(TUFF).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(OBSIDIAN).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(NETHERRACK).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(BASALT).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTStoneProperty)
        }
        registry.getOrCreate(END_STONE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.SILICON_OXIDE))
            add(HTGemProperty.DIAMOND)
        }
        //Woods
        registry.getOrCreate(WOOD).apply {
            add(HTMixtureProperty(HTElementMaterials.CARBON, HTElementMaterials.HYDROGEN, HTElementMaterials.OXYGEN))
            add(HTWoodProperty)
        }
    }

    override fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {
        //Fluids
        //Gems
        registry.getOrCreate(AMETHYST).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_PLATE)
        }
        registry.getOrCreate(DIAMOND).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(ENDER_PEARL).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(EMERALD).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(FLINT).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(LAPIS).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(QUARTZ).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        //Metals
        registry.getOrCreate(NETHERITE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_NUGGET)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        //Solids
        registry.getOrCreate(BRICK).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(CHARCOAL).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(CLAY).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(COAL).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(GLASS).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(GLOWSTONE)
        registry.getOrCreate(NETHER_BRICK).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(REDSTONE)
        //Stones
        registry.getOrCreate(STONE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(GRANITE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(DIORITE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(ANDESITE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(DEEPSLATE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(CALCITE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(TUFF).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(OBSIDIAN).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(NETHERRACK).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(BASALT).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(END_STONE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        //Woods
        registry.getOrCreate(WOOD).apply {
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_PLATE)
        }
    }

    override fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {
        //Fluids
        registry[WATER] = ColorConvertible { HTColor.BLUE }
        registry[LAVA] = ColorConvertible.ofColor(HTColor.DARK_RED, HTColor.GOLD)
        //Gems
        registry[AMETHYST] = ColorConvertible.ofColor(HTColor.BLUE, HTColor.LIGHT_PURPLE)
        registry[DIAMOND] = ColorConvertible { HTColor.AQUA }
        registry[ENDER_PEARL] = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.BLUE)
        registry[EMERALD] = ColorConvertible { HTColor.GREEN }
        registry[FLINT] = ColorConvertible.ofColor(HTColor.BLACK to 3, HTColor.BLUE to 1)
        registry[LAPIS] = ColorConvertible { HTColor.BLUE }
        registry[QUARTZ] = ColorConvertible { HTColor.WHITE }
        //Metals
        registry[NETHERITE] = ColorConvertible.ofColor(
            HTColor.BLACK to 5,
            HTColor.DARK_BLUE to 1,
            HTColor.DARK_RED to 1,
            HTColor.YELLOW to 1
        )
        //Solids
        registry[BRICK] = ColorConvertible.ofColor(HTColor.DARK_RED to 2, HTColor.GOLD to 1, HTColor.DARK_GRAY to 2)
        registry[CHARCOAL] = ColorConvertible.ofColor(HTColor.BLACK to 7, HTColor.YELLOW to 1)
        registry[CLAY] = ColorConvertible { Color(0xa4a8b8) }
        registry[COAL]
        registry[GLASS] = ColorConvertible { HTColor.WHITE }
        registry[GLOWSTONE] = ColorConvertible.ofColor(HTColor.GOLD to 1, HTColor.YELLOW to 2)
        registry[NETHER_BRICK] = ColorConvertible.ofColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.WHITE to 1)
        registry[REDSTONE] = ColorConvertible { HTColor.DARK_RED }
        //Stones
        registry[STONE] = ColorConvertible { HTColor.DARK_GRAY }
        registry[GRANITE] = ColorConvertible.ofColor(HTColor.DARK_RED to 1, HTColor.GRAY to 4, HTColor.RED to 1)
        registry[DIORITE] = ColorConvertible { HTColor.GRAY }
        registry[ANDESITE] = ColorConvertible.ofColor(HTColor.DARK_GRAY to 7, HTColor.YELLOW to 1)
        registry[DEEPSLATE] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        registry[CALCITE]
        registry[TUFF] = ColorConvertible { Color(0x4d5d53) }
        registry[OBSIDIAN] = ColorConvertible.ofColor(
            HTColor.BLACK to 4,
            HTColor.DARK_BLUE to 2,
            HTColor.DARK_RED to 1,
            HTColor.WHITE to 1
        )
        registry[NETHERRACK] = ColorConvertible.ofColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.RED to 3)
        registry[BASALT] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.GRAY)
        registry[END_STONE] = ColorConvertible.ofColor(HTColor.YELLOW to 1, HTColor.WHITE to 3)
        //Woods
        registry[WOOD] = ColorConvertible.ofColor(HTColor.DARK_GRAY to 2, HTColor.RED to 1, HTColor.YELLOW to 1)
    }

    override fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {
        //Fluids
        registry[WATER]
        registry[LAVA]
        //Gems
        registry[AMETHYST]
        registry[DIAMOND]
        registry[ENDER_PEARL]
        registry[EMERALD]
        registry[FLINT]
        registry[LAPIS]
        registry[QUARTZ]
        //Metals
        registry[NETHERITE] = FormulaConvertible { "Nr" }
        //Solids
        registry[BRICK]
        registry[CHARCOAL]
        registry[CLAY]
        registry[COAL]
        registry[GLASS]
        registry[GLOWSTONE]
        registry[NETHER_BRICK]
        registry[REDSTONE]
        //Stones
        registry[STONE]
        registry[GRANITE]
        registry[DIORITE]
        registry[ANDESITE]
        registry[DEEPSLATE]
        registry[CALCITE]
        registry[TUFF]
        registry[OBSIDIAN]
        registry[NETHERRACK]
        registry[BASALT]
        registry[END_STONE]
        //Wood
        registry[WOOD] = FormulaConvertible { "C, H, O" }
    }

    override fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {
        //Fluids
        registry[WATER]
        registry[LAVA]
        //Gems
        registry[AMETHYST]
        registry[DIAMOND]
        registry[ENDER_PEARL]
        registry[EMERALD]
        registry[FLINT]
        registry[LAPIS]
        registry[QUARTZ]
        //Metals
        registry[NETHERITE]
        //Solids
        registry[BRICK]
        registry[CHARCOAL]
        registry[CLAY]
        registry[COAL]
        registry[GLASS]
        registry[GLOWSTONE]
        registry[NETHER_BRICK]
        registry[REDSTONE]
        //Stones
        registry[STONE]
        registry[GRANITE]
        registry[DIORITE]
        registry[ANDESITE]
        registry[DEEPSLATE]
        registry[CALCITE]
        registry[TUFF]
        registry[OBSIDIAN]
        registry[NETHERRACK]
        registry[BASALT]
        registry[END_STONE]
        //Wood
        registry[WOOD]
    }

}