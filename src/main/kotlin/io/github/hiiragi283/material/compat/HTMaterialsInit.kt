package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.collection.DefaultedMap
import io.github.hiiragi283.api.extention.averageColor
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.content.*
import io.github.hiiragi283.api.material.element.HTElements
import io.github.hiiragi283.api.recipe.HTReplaceManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.HTColor
import io.github.hiiragi283.api.util.addAll
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.item.Items
import java.awt.Color

internal object HTMaterialsInit : HTMaterialsAddon {
    override val modId: String = HTMaterialsAPI.MOD_ID
    override val priority: Int = -100

    override fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {
        // Block
        registry.addAll(
            HTShapeKeys.BLOCK,
            HTShapeKeys.ORE,
        )
        // Item
        registry.addAll(
            HTShapeKeys.DUST,
            HTShapeKeys.GEAR,
            HTShapeKeys.GEM,
            HTShapeKeys.INGOT,
            HTShapeKeys.NUGGET,
            HTShapeKeys.PLATE,
            HTShapeKeys.ROD,
        )
    }

    override fun registerMaterialKey(registry: ImmutableSet.Builder<HTMaterialKey>) {
        // 1st Period
        registry.addAll(
            HTMaterialKeys.HYDROGEN,
            HTMaterialKeys.HELIUM,
        )
        // 2nd Period
        registry.addAll(
            HTMaterialKeys.LITHIUM,
            HTMaterialKeys.BERYLLIUM,
            HTMaterialKeys.CARBON,
            HTMaterialKeys.NITROGEN,
            HTMaterialKeys.OXYGEN,
            HTMaterialKeys.FLUORINE,
        )
        // 3rd Period
        registry.addAll(
            HTMaterialKeys.SODIUM,
            HTMaterialKeys.MAGNESIUM,
            HTMaterialKeys.ALUMINUM,
            HTMaterialKeys.SILICON,
            HTMaterialKeys.PHOSPHORUS,
            HTMaterialKeys.SULFUR,
            HTMaterialKeys.CHLORINE,
        )
        // 4th Period
        registry.addAll(
            HTMaterialKeys.POTASSIUM,
            HTMaterialKeys.CALCIUM,
            HTMaterialKeys.TITANIUM,
            HTMaterialKeys.CHROMIUM,
            HTMaterialKeys.MANGANESE,
            HTMaterialKeys.IRON,
            HTMaterialKeys.COBALT,
            HTMaterialKeys.NICKEL,
            HTMaterialKeys.COPPER,
            HTMaterialKeys.ZINC,
        )
        // 5th Period
        registry.addAll(
            HTMaterialKeys.SILVER,
            HTMaterialKeys.TIN,
        )
        // 6th Period
        registry.addAll(
            HTMaterialKeys.TUNGSTEN,
            HTMaterialKeys.IRIDIUM,
            HTMaterialKeys.PLATINUM,
            HTMaterialKeys.GOLD,
            HTMaterialKeys.MERCURY,
            HTMaterialKeys.LEAD,
        )
        // 7th Period
        registry.addAll(
            HTMaterialKeys.URANIUM,
            HTMaterialKeys.PLUTONIUM,
        )
        // Vanilla - Fluids
        registry.addAll(
            HTMaterialKeys.WATER,
            HTMaterialKeys.LAVA,
        )
        // Vanilla - Gems
        registry.addAll(
            HTMaterialKeys.AMETHYST,
            HTMaterialKeys.DIAMOND,
            HTMaterialKeys.ENDER_PEARL,
            HTMaterialKeys.EMERALD,
            HTMaterialKeys.FLINT,
            HTMaterialKeys.LAPIS,
            HTMaterialKeys.QUARTZ,
        )
        // Vanilla - Metals
        registry.addAll(
            HTMaterialKeys.NETHERITE,
        )
        // Vanilla - Solids
        registry.addAll(
            HTMaterialKeys.BRICK,
            HTMaterialKeys.CHARCOAL,
            HTMaterialKeys.CLAY,
            HTMaterialKeys.COAL,
            HTMaterialKeys.GLASS,
            HTMaterialKeys.GLOWSTONE,
            HTMaterialKeys.NETHER_BRICK,
            HTMaterialKeys.REDSTONE,
        )
        // Vanilla - Stones
        registry.addAll(
            HTMaterialKeys.STONE,
            HTMaterialKeys.GRANITE,
            HTMaterialKeys.DIORITE,
            HTMaterialKeys.ANDESITE,
            HTMaterialKeys.DEEPSLATE,
            HTMaterialKeys.CALCITE,
            HTMaterialKeys.TUFF,
            HTMaterialKeys.OBSIDIAN,
            HTMaterialKeys.NETHERRACK,
            HTMaterialKeys.BASALT,
            HTMaterialKeys.END_STONE,
        )
        // Vanilla - Woods
        registry.addAll(
            HTMaterialKeys.WOOD,
        )
        // Common - Fluids
        // Common - Gems
        registry.addAll(
            HTMaterialKeys.CINNABAR,
            HTMaterialKeys.COKE,
            HTMaterialKeys.OLIVINE,
            HTMaterialKeys.PERIDOT,
            HTMaterialKeys.RUBY,
            HTMaterialKeys.SALT,
            HTMaterialKeys.SAPPHIRE,
        )
        // Common - Metals
        registry.addAll(
            HTMaterialKeys.BRASS,
            HTMaterialKeys.BRONZE,
            HTMaterialKeys.ELECTRUM,
            HTMaterialKeys.INVAR,
            HTMaterialKeys.STAINLESS_STEEL,
            HTMaterialKeys.STEEl,
        )
        // Common - Solids
        registry.addAll(
            HTMaterialKeys.ASHES,
            HTMaterialKeys.BAUXITE,
            HTMaterialKeys.RUBBER,
        )
        // Common - Stones
        registry.addAll(
            HTMaterialKeys.MARBLE,
        )
        // Common - Woods
    }

    override fun modifyMaterialComposition(registry: MutableMap<HTMaterialKey, HTMaterialComposition>) {
        // 1st Period
        registry[HTMaterialKeys.HYDROGEN] = HTMaterialComposition.molecular(HTElements.H to 2)
        registry[HTMaterialKeys.HELIUM] = HTMaterialComposition.molecular(HTElements.He to 1)
        // 2nd Period
        registry[HTMaterialKeys.LITHIUM] = HTMaterialComposition.molecular(HTElements.Li to 1)
        registry[HTMaterialKeys.BERYLLIUM] = HTMaterialComposition.molecular(HTElements.Be to 1)
        registry[HTMaterialKeys.CARBON] = HTMaterialComposition.molecular(HTElements.C to 1)
        registry[HTMaterialKeys.NITROGEN] = HTMaterialComposition.molecular(HTElements.N to 2)
        registry[HTMaterialKeys.OXYGEN] = HTMaterialComposition.molecular(HTElements.O to 2)
        registry[HTMaterialKeys.FLUORINE] = HTMaterialComposition.molecular(HTElements.F to 2)
        // 3rd Period
        registry[HTMaterialKeys.SODIUM] = HTMaterialComposition.molecular(HTElements.Na to 1)
        registry[HTMaterialKeys.MAGNESIUM] = HTMaterialComposition.molecular(HTElements.Mg to 1)
        registry[HTMaterialKeys.ALUMINUM] = HTMaterialComposition.molecular(HTElements.Al to 1)
        registry[HTMaterialKeys.SILICON] = HTMaterialComposition.molecular(HTElements.Si to 1)
        registry[HTMaterialKeys.PHOSPHORUS] = HTMaterialComposition.molecular(HTElements.P to 1)
        registry[HTMaterialKeys.SULFUR] = HTMaterialComposition.molecular(HTElements.S to 8)
        registry[HTMaterialKeys.CHLORINE] = HTMaterialComposition.molecular(HTElements.Cl to 2)
        // 4th Period
        registry[HTMaterialKeys.POTASSIUM] = HTMaterialComposition.molecular(HTElements.K to 1)
        registry[HTMaterialKeys.CALCIUM] = HTMaterialComposition.molecular(HTElements.Ca to 1)
        registry[HTMaterialKeys.TITANIUM] = HTMaterialComposition.molecular(HTElements.Ti to 1)
        registry[HTMaterialKeys.CHROMIUM] = HTMaterialComposition.molecular(HTElements.Cr to 1)
        registry[HTMaterialKeys.MANGANESE] = HTMaterialComposition.molecular(HTElements.Mn to 1)
        registry[HTMaterialKeys.IRON] = HTMaterialComposition.molecular(HTElements.Fe to 1)
        registry[HTMaterialKeys.COBALT] = HTMaterialComposition.molecular(HTElements.Co to 1)
        registry[HTMaterialKeys.NICKEL] = HTMaterialComposition.molecular(HTElements.Ni to 1)
        registry[HTMaterialKeys.COPPER] = HTMaterialComposition.molecular(HTElements.Cu to 1)
        registry[HTMaterialKeys.ZINC] = HTMaterialComposition.molecular(HTElements.Zn to 1)
        // 5th Period
        registry[HTMaterialKeys.SILVER] = HTMaterialComposition.molecular(HTElements.Ag to 1)
        registry[HTMaterialKeys.TIN] = HTMaterialComposition.molecular(HTElements.Sn to 1)
        // 6th Period
        registry[HTMaterialKeys.TUNGSTEN] = HTMaterialComposition.molecular(HTElements.W to 1)
        registry[HTMaterialKeys.IRIDIUM] = HTMaterialComposition.molecular(HTElements.Ir to 1)
        registry[HTMaterialKeys.PLATINUM] = HTMaterialComposition.molecular(HTElements.Pt to 1)
        registry[HTMaterialKeys.GOLD] = HTMaterialComposition.molecular(HTElements.Au to 1)
        registry[HTMaterialKeys.MERCURY] = HTMaterialComposition.molecular(HTElements.Hg to 1)
        registry[HTMaterialKeys.LEAD] = HTMaterialComposition.molecular(HTElements.Pb to 1)
        // 7th Period
        registry[HTMaterialKeys.URANIUM] = HTMaterialComposition.molecular(HTElements.U to 1)
        registry[HTMaterialKeys.PLUTONIUM] = HTMaterialComposition.molecular(HTElements.Pu to 1)
        // Vanilla - Fluids
        registry[HTMaterialKeys.WATER] = HTMaterialComposition.molecular(mapOf(HTElements.H to 2, HTElements.O to 1)) {
            color = HTColor.BLUE
        }
        registry[HTMaterialKeys.WATER] = HTMaterialComposition.molecular(mapOf(HTElements.SiO2 to 1)) {
            color = averageColor(HTColor.DARK_RED, HTColor.GOLD)
        }
        // Vanilla - Gems
        registry[HTMaterialKeys.AMETHYST] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.BLUE, HTColor.LIGHT_PURPLE)
        }
        registry[HTMaterialKeys.DIAMOND] = HTMaterialComposition.molecular(HTElements.C to 1) {
            color = HTColor.AQUA
        }
        registry[HTMaterialKeys.ENDER_PEARL] = HTMaterialComposition.molecular {
            color = averageColor(HTColor.DARK_GREEN, HTColor.BLUE)
        }
        registry[HTMaterialKeys.EMERALD] = HTMaterialComposition.molecular(
            HTElements.Be to 3,
            HTElements.Al to 2,
            HTElements.Si to 6,
            HTElements.O to 18,
        ) { color = HTColor.GREEN }
        registry[HTMaterialKeys.FLINT] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.BLACK to 3, HTColor.BLUE to 1)
        }
        registry[HTMaterialKeys.LAPIS] = HTMaterialComposition.molecular {
            color = HTColor.BLUE
        }
        registry[HTMaterialKeys.QUARTZ] = HTMaterialComposition.molecular(HTElements.SiO2 to 1)
        // Vanilla - Metals
        registry[HTMaterialKeys.NETHERITE] = HTMaterialComposition.molecular(HTElements.Nr to 1)
        // Vanilla - Solids
        registry[HTMaterialKeys.BRICK] = HTMaterialComposition.molecular {
            color = averageColor(HTColor.DARK_RED to 2, HTColor.GOLD to 1, HTColor.DARK_GRAY to 2)
        }
        registry[HTMaterialKeys.CHARCOAL] = HTMaterialComposition.molecular(HTElements.C to 1) {
            color = averageColor(HTColor.BLACK to 7, HTColor.YELLOW to 1)
        }
        registry[HTMaterialKeys.CLAY] = HTMaterialComposition.molecular {
            color = Color(0xa4a8b8)
        }
        registry[HTMaterialKeys.COAL] = HTMaterialComposition.molecular(HTElements.C to 1)
        registry[HTMaterialKeys.GLASS] = HTMaterialComposition.molecular(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.GLOWSTONE] = HTMaterialComposition.molecular {
            color = averageColor(HTColor.GOLD to 1, HTColor.YELLOW to 2)
        }
        registry[HTMaterialKeys.NETHER_BRICK] = HTMaterialComposition.molecular {
            color = averageColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.WHITE to 1)
        }
        registry[HTMaterialKeys.REDSTONE] = HTMaterialComposition.molecular {
            color = HTColor.DARK_RED
        }
        // Vanilla - Stones
        registry[HTMaterialKeys.STONE] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = HTColor.DARK_GRAY
        }
        registry[HTMaterialKeys.GRANITE] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.DARK_RED to 1, HTColor.GRAY to 4, HTColor.RED to 1)
        }
        registry[HTMaterialKeys.DIORITE] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = HTColor.GRAY
        }
        registry[HTMaterialKeys.ANDESITE] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.DARK_GRAY to 7, HTColor.YELLOW to 1)
        }
        registry[HTMaterialKeys.DEEPSLATE] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.BLACK, HTColor.DARK_GRAY)
        }
        registry[HTMaterialKeys.CALCITE] = HTMaterialComposition.molecular(HTElements.Ca to 1, HTElements.CO3 to 1)
        registry[HTMaterialKeys.TUFF] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = Color(0x4d5d53)
        }
        registry[HTMaterialKeys.OBSIDIAN] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(
                HTColor.BLACK to 4,
                HTColor.DARK_BLUE to 2,
                HTColor.DARK_RED to 1,
                HTColor.WHITE to 1,
            )
        }
        registry[HTMaterialKeys.NETHERRACK] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.RED to 3)
        }
        registry[HTMaterialKeys.BASALT] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.BLACK, HTColor.GRAY)
        }
        registry[HTMaterialKeys.END_STONE] = HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
            color = averageColor(HTColor.YELLOW to 1, HTColor.WHITE to 3)
        }
        // Vanilla - Woods
        registry[HTMaterialKeys.WOOD] = HTMaterialComposition.mixture(HTElements.C, HTElements.H, HTElements.O) {
            color = averageColor(HTColor.DARK_GRAY to 2, HTColor.RED to 1, HTColor.YELLOW to 1)
            formula = "(C, H, O)"
        }
        // Common - Fluids
        // Common - Gems
        registry[HTMaterialKeys.CINNABAR] = HTMaterialComposition.molecular(HTElements.Hg to 1, HTElements.S to 1) {
            color = HTColor.RED
        }
        registry[HTMaterialKeys.COKE] = HTMaterialComposition.molecular(HTElements.C to 1) {
            color = HTColor.DARK_GRAY
        }
        registry[HTMaterialKeys.OLIVINE] = HTMaterialComposition.molecular {
            color = averageColor(HTColor.DARK_GREEN, HTColor.GREEN)
        }
        registry[HTMaterialKeys.PERIDOT] = HTMaterialComposition.molecular {
            color = averageColor(HTColor.GREEN, HTColor.WHITE)
        }
        registry[HTMaterialKeys.RUBY] = HTMaterialComposition.molecular(HTElements.Al2O3 to 1) {
            color = HTColor.RED
        }
        registry[HTMaterialKeys.SALT] = HTMaterialComposition.molecular(HTElements.Na to 1, HTElements.Cl to 1)
        registry[HTMaterialKeys.SAPPHIRE] = HTMaterialComposition.molecular(HTElements.Al2O3 to 1) {
            color = HTColor.BLUE
        }
        // Common - Metals
        registry[HTMaterialKeys.BRASS] = HTMaterialComposition.molecular(HTElements.Cu to 3, HTElements.Zn to 1) {
            color = HTColor.GOLD
        }
        registry[HTMaterialKeys.BRONZE] = HTMaterialComposition.molecular(HTElements.Cu to 3, HTElements.Sn to 1)
        registry[HTMaterialKeys.ELECTRUM] = HTMaterialComposition.molecular(HTElements.Ag to 1, HTElements.Au to 1) {
            color = averageColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
        }
        registry[HTMaterialKeys.INVAR] = HTMaterialComposition.molecular(HTElements.Fe to 2, HTElements.Ni to 1) {
            color = averageColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
        }
        registry[HTMaterialKeys.STAINLESS_STEEL] = HTMaterialComposition.molecular(
            HTElements.Fe to 6,
            HTElements.Cr to 1,
            HTElements.Mn to 1,
            HTElements.Ni to 1,
        ) { color = averageColor(HTColor.GRAY, HTColor.WHITE) }
        registry[HTMaterialKeys.STEEl] = HTMaterialComposition.mixture(HTElements.Fe, HTElements.C) {
            color = HTColor.DARK_GRAY
            formula = "(Fe, C)"
        }
        // Common - Solids
        registry[HTMaterialKeys.ASHES] = HTMaterialComposition.molecular {
            color = HTColor.DARK_GRAY
        }
        registry[HTMaterialKeys.BAUXITE] = HTMaterialComposition.hydrate(
            HTMaterialComposition.molecular(HTElements.Al2O3 to 1),
            2,
        ) { color = averageColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1) }
        registry[HTMaterialKeys.RUBBER] = HTMaterialComposition.polymer(HTElements.C to 5, HTElements.H to 6) {
            color = averageColor(HTColor.BLACK, HTColor.DARK_GRAY)
            formula = "CC(=C)C=C"
        }
        // Common - Stones
        registry[HTMaterialKeys.MARBLE] = HTMaterialComposition.molecular(HTElements.Ca to 1, HTElements.CO3 to 1)
        // Common - Woods
    }

    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap.Builder>) {
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.HELIUM)
            .add(HTSimpleFluidContent())
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.CARBON)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.PLATE))
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
            .add(HTSimpleFluidContent())
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.ALUMINUM)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.SILICON)
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.PHOSPHORUS)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.SULFUR)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
            .add(HTSimpleFluidContent())
        // 4th Period
        registry.getOrCreate(HTMaterialKeys.TITANIUM)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 3))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.IRON)
            .addMetalComponents()
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.INGOT)
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.NUGGET)
        registry.getOrCreate(HTMaterialKeys.NICKEL)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.COPPER)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.ZINC)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addMetalComponents()
        // 5th Period
        registry.getOrCreate(HTMaterialKeys.SILVER)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.TIN)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addMetalComponents()
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.TUNGSTEN)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 3))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.IRIDIUM)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 3))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.PLATINUM)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 3))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.GOLD)
            .addMetalComponents()
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.INGOT)
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.NUGGET)
        registry.getOrCreate(HTMaterialKeys.MERCURY)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.LEAD)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addMetalComponents()
        // 7th Period
        registry.getOrCreate(HTMaterialKeys.URANIUM)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.PLUTONIUM)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        // Vanilla - Fluids
        // Vanilla - Gems
        registry.getOrCreate(HTMaterialKeys.AMETHYST)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.PLATE))
        registry.getOrCreate(HTMaterialKeys.DIAMOND)
            .addGemComponents()
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.GEM)
        registry.getOrCreate(HTMaterialKeys.ENDER_PEARL)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.EMERALD)
            .addGemComponents()
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.GEM)
        registry.getOrCreate(HTMaterialKeys.FLINT)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.LAPIS)
            .addGemComponents()
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.GEM)
        registry.getOrCreate(HTMaterialKeys.QUARTZ)
            .addGemComponents()
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.GEM)
        // Vanilla - Metals
        registry.getOrCreate(HTMaterialKeys.NETHERITE)
            .addMetalComponents()
            .remove(HTMaterialContent.Type.ITEM, HTShapeKeys.INGOT)
        // Vanilla - Solids
        registry.getOrCreate(HTMaterialKeys.BRICK)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.PLATE))
            .add(HTSimpleItemContent(HTShapeKeys.ROD))
        registry.getOrCreate(HTMaterialKeys.CHARCOAL)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.CLAY).add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.COAL).add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.GLASS)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.PLATE))
            .add(HTSimpleItemContent(HTShapeKeys.ROD))
        registry.getOrCreate(HTMaterialKeys.GLOWSTONE)
        registry.getOrCreate(HTMaterialKeys.NETHER_BRICK)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.PLATE))
            .add(HTSimpleItemContent(HTShapeKeys.ROD))
        registry.getOrCreate(HTMaterialKeys.REDSTONE)
        // Vanilla - Stones
        registry.getOrCreate(HTMaterialKeys.STONE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.GEAR))
            .add(HTSimpleItemContent(HTShapeKeys.PLATE))
            .add(HTSimpleItemContent(HTShapeKeys.ROD))
        registry.getOrCreate(HTMaterialKeys.GRANITE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.DIORITE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.ANDESITE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.DEEPSLATE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.CALCITE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.TUFF)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.OBSIDIAN)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.NETHERRACK)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.BASALT)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.END_STONE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        // Vanilla - Woods
        registry.getOrCreate(HTMaterialKeys.WOOD)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.GEAR))
            .add(HTSimpleItemContent(HTShapeKeys.PLATE))
        // Common - Fluids
        // Common - Gems
        registry.getOrCreate(HTMaterialKeys.CINNABAR)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.GEM))
        registry.getOrCreate(HTMaterialKeys.COKE)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES))
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
            .add(HTSimpleItemContent(HTShapeKeys.GEM))
        registry.getOrCreate(HTMaterialKeys.OLIVINE)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addGemComponents()
        registry.getOrCreate(HTMaterialKeys.PERIDOT)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addGemComponents()
        registry.getOrCreate(HTMaterialKeys.RUBY)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 3))
            .addGemComponents()
        registry.getOrCreate(HTMaterialKeys.SALT)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addGemComponents()
        registry.getOrCreate(HTMaterialKeys.SAPPHIRE)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 3))
            .addGemComponents()
        // Common - Metals
        registry.getOrCreate(HTMaterialKeys.BRASS)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.BRONZE)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 1))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.ELECTRUM)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.INVAR)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.STAINLESS_STEEL)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        registry.getOrCreate(HTMaterialKeys.STEEl)
            .add(HTStorageBlockContent(harvestTool = FabricToolTags::PICKAXES, harvestLevel = 2))
            .addMetalComponents()
        // Common - Solids
        registry.getOrCreate(HTMaterialKeys.ASHES)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.BAUXITE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        registry.getOrCreate(HTMaterialKeys.RUBBER)
            .add(HTStorageBlockContent())
            .addMetalComponents()
        // Common - Stones
        registry.getOrCreate(HTMaterialKeys.MARBLE)
            .add(HTSimpleItemContent(HTShapeKeys.DUST))
        // Common - Woods
    }

    override fun modifyMaterialType(registry: MutableMap<HTMaterialKey, HTMaterialType>) {
        // 1st Period
        // 2nd Period
        registry[HTMaterialKeys.LITHIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.BERYLLIUM] = HTMaterialType.Metal
        // 3rd Period
        registry[HTMaterialKeys.SODIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.MAGNESIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.ALUMINUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.SILICON] = HTMaterialType.Metal
        // 4th Period
        registry[HTMaterialKeys.POTASSIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.CALCIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.TITANIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.CHROMIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.MANGANESE] = HTMaterialType.Metal
        registry[HTMaterialKeys.IRON] = HTMaterialType.Metal
        registry[HTMaterialKeys.COBALT] = HTMaterialType.Metal
        registry[HTMaterialKeys.NICKEL] = HTMaterialType.Metal
        registry[HTMaterialKeys.COPPER] = HTMaterialType.Metal
        registry[HTMaterialKeys.ZINC] = HTMaterialType.Metal
        // 5th Period
        registry[HTMaterialKeys.SILICON] = HTMaterialType.Metal
        registry[HTMaterialKeys.TIN] = HTMaterialType.Metal
        // 6th Period
        registry[HTMaterialKeys.TUNGSTEN] = HTMaterialType.Metal
        registry[HTMaterialKeys.IRIDIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.PLATINUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.GOLD] = HTMaterialType.Metal
        registry[HTMaterialKeys.MERCURY] = HTMaterialType.Metal
        registry[HTMaterialKeys.LEAD] = HTMaterialType.Metal
        // 7th Period
        registry[HTMaterialKeys.URANIUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.PLUTONIUM] = HTMaterialType.Metal
        // Vanilla - Fluids
        registry[HTMaterialKeys.WATER]
        registry[HTMaterialKeys.LAVA]
        // Vanilla - Gems
        registry[HTMaterialKeys.AMETHYST] = HTMaterialType.Gem.AMETHYST
        registry[HTMaterialKeys.DIAMOND] = HTMaterialType.Gem.DIAMOND
        registry[HTMaterialKeys.ENDER_PEARL]
        registry[HTMaterialKeys.EMERALD] = HTMaterialType.Gem.EMERALD
        registry[HTMaterialKeys.LAPIS] = HTMaterialType.Gem.LAPIS
        registry[HTMaterialKeys.QUARTZ] = HTMaterialType.Gem.QUARTZ
        // Vanilla - Metals
        registry[HTMaterialKeys.NETHERITE] = HTMaterialType.Metal
        // Vanilla - Solids
        registry[HTMaterialKeys.BRICK]
        registry[HTMaterialKeys.CHARCOAL]
        registry[HTMaterialKeys.CLAY]
        registry[HTMaterialKeys.COAL]
        registry[HTMaterialKeys.GLASS]
        registry[HTMaterialKeys.GLOWSTONE]
        registry[HTMaterialKeys.NETHER_BRICK]
        registry[HTMaterialKeys.REDSTONE]
        // Vanilla - Stones
        registry[HTMaterialKeys.STONE] = HTMaterialType.Stone
        registry[HTMaterialKeys.GRANITE] = HTMaterialType.Stone
        registry[HTMaterialKeys.DIORITE] = HTMaterialType.Stone
        registry[HTMaterialKeys.ANDESITE] = HTMaterialType.Stone
        registry[HTMaterialKeys.DEEPSLATE] = HTMaterialType.Stone
        registry[HTMaterialKeys.CALCITE] = HTMaterialType.Stone
        registry[HTMaterialKeys.TUFF] = HTMaterialType.Stone
        registry[HTMaterialKeys.OBSIDIAN] = HTMaterialType.Stone
        registry[HTMaterialKeys.NETHERRACK] = HTMaterialType.Stone
        registry[HTMaterialKeys.BASALT] = HTMaterialType.Stone
        registry[HTMaterialKeys.END_STONE] = HTMaterialType.Stone
        // Vanilla - Wood
        registry[HTMaterialKeys.WOOD] = HTMaterialType.Wood
        // Common - Fluids
        // Common - Gems
        registry[HTMaterialKeys.CINNABAR] = HTMaterialType.Gem.EMERALD
        registry[HTMaterialKeys.COKE] = HTMaterialType.Gem.COAL
        registry[HTMaterialKeys.OLIVINE] = HTMaterialType.Gem.EMERALD
        registry[HTMaterialKeys.PERIDOT] = HTMaterialType.Gem.RUBY
        registry[HTMaterialKeys.RUBY] = HTMaterialType.Gem.RUBY
        registry[HTMaterialKeys.SALT] = HTMaterialType.Gem.CUBIC
        registry[HTMaterialKeys.SAPPHIRE] = HTMaterialType.Gem.RUBY
        // Common - Metals
        registry[HTMaterialKeys.BRASS] = HTMaterialType.Metal
        registry[HTMaterialKeys.BRONZE] = HTMaterialType.Metal
        registry[HTMaterialKeys.ELECTRUM] = HTMaterialType.Metal
        registry[HTMaterialKeys.INVAR] = HTMaterialType.Metal
        registry[HTMaterialKeys.STAINLESS_STEEL] = HTMaterialType.Metal
        registry[HTMaterialKeys.STEEl] = HTMaterialType.Metal
        // Common - Solids
        registry[HTMaterialKeys.ASHES]
        registry[HTMaterialKeys.BAUXITE]
        registry[HTMaterialKeys.RUBBER]
        // Common - Stones
        registry[HTMaterialKeys.MARBLE] = HTMaterialType.Stone
        // Common - Woods
    }

    override fun postInitialize(envType: EnvType) {
        HTReplaceManager.setReplaceCondition(Items.OAK_PLANKS, inputReplace = false, outputReplace = false)
        HTReplaceManager.setReplaceCondition(Items.BIRCH_PLANKS, inputReplace = false, outputReplace = false)
        HTReplaceManager.setReplaceCondition(Items.SPRUCE_PLANKS, inputReplace = false, outputReplace = false)
        HTReplaceManager.setReplaceCondition(Items.JUNGLE_PLANKS, inputReplace = false, outputReplace = false)
        HTReplaceManager.setReplaceCondition(Items.ACACIA_PLANKS, inputReplace = false, outputReplace = false)
        HTReplaceManager.setReplaceCondition(Items.DARK_OAK_PLANKS, inputReplace = false, outputReplace = false)
        HTReplaceManager.setReplaceCondition(Items.CRIMSON_PLANKS, inputReplace = false, outputReplace = false)
        HTReplaceManager.setReplaceCondition(Items.WARPED_PLANKS, inputReplace = false, outputReplace = false)
    }
}
