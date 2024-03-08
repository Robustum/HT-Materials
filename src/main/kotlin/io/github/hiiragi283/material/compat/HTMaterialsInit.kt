package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.HTColor
import io.github.hiiragi283.api.extension.averageColor
import io.github.hiiragi283.api.fluid.HTFluidRegistry
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElements
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartRegistry
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapes
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import java.awt.Color

internal object HTMaterialsInit : HTMaterialsAddon {
    override val modId: String = HTMaterialsAPI.MOD_ID
    override val priority: Int = -100

    override fun registerShape(shapeHelper: HTMaterialsAddon.ShapeHelper) {
        // Block
        shapeHelper.addShape(HTShapes.BLOCK)
        shapeHelper.addShape(HTShapes.BRICKS)
        shapeHelper.addShape(HTShapes.LOG)
        shapeHelper.addShape(HTShapes.ORE)
        shapeHelper.addShape(HTShapes.PLANKS)
        // Item
        shapeHelper.addShape(HTShapes.DUST)
        shapeHelper.addShape(HTShapes.GEAR)
        shapeHelper.addShape(HTShapes.GEM)
        shapeHelper.addShape(HTShapes.INGOT)
        shapeHelper.addShape(HTShapes.NUGGET)
        shapeHelper.addShape(HTShapes.PLATE)
        shapeHelper.addShape(HTShapes.ROD)
    }

    override fun registerMaterial(materialHelper: HTMaterialsAddon.MaterialHelper) {
        // 1st Period
        materialHelper.addSimpleMaterial(HTMaterialKeys.HYDROGEN, HTElements.H to 2)
        materialHelper.addSimpleMaterial(HTMaterialKeys.HELIUM, HTElements.He to 1)
        // 2nd Period
        materialHelper.addMetalMaterial(HTMaterialKeys.LITHIUM, HTElements.Li)
        materialHelper.addMetalMaterial(HTMaterialKeys.BERYLLIUM, HTElements.Be)
        materialHelper.addSimpleMaterial(HTMaterialKeys.CARBON, HTElements.C to 1)
        materialHelper.addSimpleMaterial(HTMaterialKeys.NITROGEN, HTElements.N to 2)
        materialHelper.addSimpleMaterial(HTMaterialKeys.OXYGEN, HTElements.O to 2)
        materialHelper.addSimpleMaterial(HTMaterialKeys.FLUORINE, HTElements.F to 2)
        // 3rd Period
        materialHelper.addMetalMaterial(HTMaterialKeys.SODIUM, HTElements.Na)
        materialHelper.addMetalMaterial(HTMaterialKeys.MAGNESIUM, HTElements.Mg)
        materialHelper.addMetalMaterial(HTMaterialKeys.ALUMINUM, HTElements.Al)
        materialHelper.addAlternativeName(HTMaterialKeys.ALUMINUM, "aluminium")
        materialHelper.addMetalMaterial(HTMaterialKeys.SILICON, HTElements.Si)
        materialHelper.addSimpleMaterial(HTMaterialKeys.PHOSPHORUS, HTElements.P to 1)
        materialHelper.addSimpleMaterial(HTMaterialKeys.SULFUR, HTElements.S to 8)
        materialHelper.addSimpleMaterial(HTMaterialKeys.CHLORINE, HTElements.Cl to 2)
        // 4th Period
        materialHelper.addMetalMaterial(HTMaterialKeys.POTASSIUM, HTElements.K)
        materialHelper.addMetalMaterial(HTMaterialKeys.CALCIUM, HTElements.Ca)
        materialHelper.addMetalMaterial(HTMaterialKeys.TITANIUM, HTElements.Ti)
        materialHelper.addMetalMaterial(HTMaterialKeys.CHROMIUM, HTElements.Cr)
        materialHelper.addAlternativeName(HTMaterialKeys.CHROMIUM, "chrome")
        materialHelper.addMetalMaterial(HTMaterialKeys.MANGANESE, HTElements.Mn)
        materialHelper.addMetalMaterial(HTMaterialKeys.IRON, HTElements.Fe)
        materialHelper.addMetalMaterial(HTMaterialKeys.COBALT, HTElements.Co)
        materialHelper.addMetalMaterial(HTMaterialKeys.NICKEL, HTElements.Ni)
        materialHelper.addMetalMaterial(HTMaterialKeys.COPPER, HTElements.Cu)
        materialHelper.addMetalMaterial(HTMaterialKeys.ZINC, HTElements.Zn)
        // 5th Period
        materialHelper.addMetalMaterial(HTMaterialKeys.SILVER, HTElements.Ag)
        materialHelper.addMetalMaterial(HTMaterialKeys.TIN, HTElements.Sn)
        // 6th Period
        materialHelper.addMetalMaterial(HTMaterialKeys.TUNGSTEN, HTElements.W)
        materialHelper.addMetalMaterial(HTMaterialKeys.IRIDIUM, HTElements.Ir)
        materialHelper.addMetalMaterial(HTMaterialKeys.PLATINUM, HTElements.Pt)
        materialHelper.addMetalMaterial(HTMaterialKeys.GOLD, HTElements.Au)
        materialHelper.addMetalMaterial(HTMaterialKeys.MERCURY, HTElements.Hg)
        materialHelper.addMetalMaterial(HTMaterialKeys.LEAD, HTElements.Pb)
        // 7th Period
        materialHelper.addMetalMaterial(HTMaterialKeys.URANIUM, HTElements.U)
        materialHelper.addMetalMaterial(HTMaterialKeys.PLUTONIUM, HTElements.Pu)
        // Vanilla - Fluids
        materialHelper.addMaterialKey(HTMaterialKeys.WATER)
        materialHelper.setComposition(
            HTMaterialKeys.WATER,
            HTMaterialComposition.molecular(mapOf(HTElements.H to 2, HTElements.O to 1)) {
                color = HTColor.BLUE
            },
        )

        materialHelper.addMaterialKey(HTMaterialKeys.LAVA)
        materialHelper.setComposition(
            HTMaterialKeys.WATER,
            HTMaterialComposition.molecular(mapOf(HTElements.SiO2 to 1)) {
                color = averageColor(HTColor.DARK_RED, HTColor.GOLD)
            },
        )
        // Vanilla - Gems
        materialHelper.addMaterialKey(HTMaterialKeys.AMETHYST)
        materialHelper.setComposition(
            HTMaterialKeys.AMETHYST,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLUE, HTColor.LIGHT_PURPLE)
            },
        )
        materialHelper.setType(HTMaterialKeys.AMETHYST, HTMaterialType.Gem.AMETHYST)

        materialHelper.addMaterialKey(HTMaterialKeys.DIAMOND)
        materialHelper.setComposition(
            HTMaterialKeys.DIAMOND,
            HTMaterialComposition.molecular(HTElements.C to 1) {
                color = HTColor.AQUA
            },
        )
        materialHelper.setType(HTMaterialKeys.DIAMOND, HTMaterialType.Gem.DIAMOND)

        materialHelper.addMaterialKey(HTMaterialKeys.ENDER_PEARL)
        materialHelper.setComposition(
            HTMaterialKeys.ENDER_PEARL,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.DARK_GREEN, HTColor.BLUE)
            },
        )
        materialHelper.setType(HTMaterialKeys.ENDER_PEARL, HTMaterialType.Gem.EMERALD)

        materialHelper.addMaterialKey(HTMaterialKeys.EMERALD)
        materialHelper.setComposition(
            HTMaterialKeys.EMERALD,
            HTMaterialComposition.molecular(
                HTElements.Be to 3,
                HTElements.Al to 2,
                HTElements.Si to 6,
                HTElements.O to 18,
            ) { color = HTColor.GREEN },
        )
        materialHelper.setType(HTMaterialKeys.EMERALD, HTMaterialType.Gem.EMERALD)

        materialHelper.addMaterialKey(HTMaterialKeys.FLINT)
        materialHelper.setComposition(
            HTMaterialKeys.FLINT,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK to 3, HTColor.BLUE to 1)
            },
        )
        materialHelper.setType(HTMaterialKeys.FLINT, HTMaterialType.Gem.FLINT)

        materialHelper.addMaterialKey(HTMaterialKeys.LAPIS)
        materialHelper.setComposition(
            HTMaterialKeys.LAPIS,
            HTMaterialComposition.molecular {
                color = HTColor.BLUE
            },
        )
        materialHelper.setType(HTMaterialKeys.LAPIS, HTMaterialType.Gem.LAPIS)

        materialHelper.addMaterialKey(HTMaterialKeys.QUARTZ)
        materialHelper.setComposition(
            HTMaterialKeys.QUARTZ,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1),
        )
        materialHelper.setType(HTMaterialKeys.QUARTZ, HTMaterialType.Gem.QUARTZ)
        // Vanilla - Metal
        materialHelper.addMetalMaterial(HTMaterialKeys.NETHERITE, HTElements.Nr)
        // Vanilla - Solids
        materialHelper.addMaterialKey(HTMaterialKeys.BRICK)
        materialHelper.setComposition(
            HTMaterialKeys.BRICK,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.DARK_RED to 2, HTColor.GOLD to 1, HTColor.DARK_GRAY to 2)
            },
        )

        materialHelper.addMaterialKey(HTMaterialKeys.CHARCOAL)
        materialHelper.setComposition(
            HTMaterialKeys.CHARCOAL,
            HTMaterialComposition.molecular(HTElements.C to 1) {
                color = averageColor(HTColor.BLACK to 7, HTColor.YELLOW to 1)
            },
        )

        materialHelper.addMaterialKey(HTMaterialKeys.CLAY)
        materialHelper.setComposition(
            HTMaterialKeys.CLAY,
            HTMaterialComposition.molecular {
                color = Color(0xa4a8b8)
            },
        )

        materialHelper.addSimpleMaterial(HTMaterialKeys.COAL, HTElements.C to 1)

        materialHelper.addSimpleMaterial(HTMaterialKeys.GLASS, HTElements.SiO2 to 1)

        materialHelper.addMaterialKey(HTMaterialKeys.GLOWSTONE)
        materialHelper.setComposition(
            HTMaterialKeys.GLOWSTONE,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.GOLD to 1, HTColor.YELLOW to 2)
            },
        )

        materialHelper.addMaterialKey(HTMaterialKeys.NETHER_BRICK)
        materialHelper.setComposition(
            HTMaterialKeys.NETHER_BRICK,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.WHITE to 1)
            },
        )

        materialHelper.addMaterialKey(HTMaterialKeys.REDSTONE)
        materialHelper.setComposition(
            HTMaterialKeys.REDSTONE,
            HTMaterialComposition.molecular {
                color = HTColor.DARK_RED
            },
        )
        // Vanilla - Stones
        materialHelper.addStoneMaterial(
            HTMaterialKeys.STONE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = HTColor.DARK_GRAY
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.GRANITE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.DARK_RED to 1, HTColor.GRAY to 4, HTColor.RED to 1)
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.DIORITE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = HTColor.GRAY
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.ANDESITE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.DARK_GRAY to 7, HTColor.YELLOW to 1)
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.DEEPSLATE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK, HTColor.DARK_GRAY)
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.CALCITE,
            HTMaterialComposition.molecular(HTElements.Ca to 1, HTElements.CO3 to 1),
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.TUFF,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = Color(0x4d5d53)
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.OBSIDIAN,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(
                    HTColor.BLACK to 4,
                    HTColor.DARK_BLUE to 2,
                    HTColor.DARK_RED to 1,
                    HTColor.WHITE to 1,
                )
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.NETHERRACK,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.RED to 3)
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.BASALT,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.BLACK, HTColor.GRAY)
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.END_STONE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = averageColor(HTColor.YELLOW to 1, HTColor.WHITE to 3)
            },
        )

        materialHelper.addStoneMaterial(
            HTMaterialKeys.BLACKSTONE,
            HTMaterialComposition.molecular(HTElements.SiO2 to 1) {
                color = HTColor.BLACK
            },
        )
        // Vanilla - Woods
        materialHelper.addMaterialKey(HTMaterialKeys.WOOD)
        materialHelper.setComposition(
            HTMaterialKeys.WOOD,
            HTMaterialComposition.mixture(HTElements.C, HTElements.H, HTElements.O) {
                color = averageColor(HTColor.DARK_GRAY to 2, HTColor.RED to 1, HTColor.YELLOW to 1)
                formula = "(C, H, O)"
            },
        )
        materialHelper.setType(HTMaterialKeys.WOOD, HTMaterialType.Wood)
        // Common - Fluids
        // Common - Gems
        materialHelper.addMaterialKey(HTMaterialKeys.CINNABAR)
        materialHelper.setComposition(
            HTMaterialKeys.CINNABAR,
            HTMaterialComposition.molecular(HTElements.Hg to 1, HTElements.S to 1) {
                color = HTColor.RED
            },
        )
        materialHelper.setType(HTMaterialKeys.CINNABAR, HTMaterialType.Gem.EMERALD)

        materialHelper.addMaterialKey(HTMaterialKeys.COKE)
        materialHelper.setComposition(
            HTMaterialKeys.COKE,
            HTMaterialComposition.molecular(HTElements.C to 1) {
                color = HTColor.DARK_GRAY
            },
        )
        materialHelper.setType(HTMaterialKeys.COKE, HTMaterialType.Gem.COAL)

        materialHelper.addMaterialKey(HTMaterialKeys.OLIVINE)
        materialHelper.setComposition(
            HTMaterialKeys.OLIVINE,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.DARK_GREEN, HTColor.GREEN)
            },
        )
        materialHelper.setType(HTMaterialKeys.OLIVINE, HTMaterialType.Gem.EMERALD)

        materialHelper.addMaterialKey(HTMaterialKeys.PERIDOT)
        materialHelper.setComposition(
            HTMaterialKeys.PERIDOT,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.GREEN, HTColor.WHITE)
            },
        )
        materialHelper.setType(HTMaterialKeys.PERIDOT, HTMaterialType.Gem.RUBY)

        materialHelper.addMaterialKey(HTMaterialKeys.RUBY)
        materialHelper.setComposition(
            HTMaterialKeys.RUBY,
            HTMaterialComposition.molecular(HTElements.Al2O3 to 1) {
                color = HTColor.RED
            },
        )
        materialHelper.setType(HTMaterialKeys.RUBY, HTMaterialType.Gem.RUBY)

        materialHelper.addMaterialKey(HTMaterialKeys.SALT)
        materialHelper.setComposition(
            HTMaterialKeys.SALT,
            HTMaterialComposition.molecular(HTElements.Na to 1, HTElements.Cl to 1) {
                color = HTColor.WHITE
            },
        )
        materialHelper.setType(HTMaterialKeys.SALT, HTMaterialType.Gem.CUBIC)

        materialHelper.addMaterialKey(HTMaterialKeys.SAPPHIRE)
        materialHelper.setComposition(
            HTMaterialKeys.SAPPHIRE,
            HTMaterialComposition.molecular(HTElements.Al2O3 to 1) {
                color = HTColor.BLUE
            },
        )
        materialHelper.setType(HTMaterialKeys.SAPPHIRE, HTMaterialType.Gem.RUBY)
        // Common - Metals
        materialHelper.addMetalMaterial(
            HTMaterialKeys.BRASS,
            HTMaterialComposition.molecular(HTElements.Cu to 3, HTElements.Zn to 1) {
                color = HTColor.GOLD
            },
        )

        materialHelper.addMetalMaterial(
            HTMaterialKeys.BRONZE,
            HTMaterialComposition.molecular(HTElements.Cu to 3, HTElements.Sn to 1),
        )

        materialHelper.addMetalMaterial(
            HTMaterialKeys.ELECTRUM,
            HTMaterialComposition.molecular(HTElements.Ag to 1, HTElements.Au to 1) {
                color = averageColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
            },
        )

        materialHelper.addMetalMaterial(
            HTMaterialKeys.INVAR,
            HTMaterialComposition.molecular(HTElements.Fe to 2, HTElements.Ni to 1) {
                color = averageColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
            },
        )

        materialHelper.addMetalMaterial(
            HTMaterialKeys.STAINLESS_STEEL,
            HTMaterialComposition.molecular(
                HTElements.Fe to 6,
                HTElements.Cr to 1,
                HTElements.Mn to 1,
                HTElements.Ni to 1,
            ) { color = averageColor(HTColor.GRAY, HTColor.WHITE) },
        )

        materialHelper.addMetalMaterial(
            HTMaterialKeys.STEEl,
            HTMaterialComposition.mixture(HTElements.Fe, HTElements.C) {
                color = HTColor.DARK_GRAY
                formula = "(Fe, C)"
            },
        )
        // Common - Solids
        materialHelper.addMaterialKey(HTMaterialKeys.ASHES)
        materialHelper.setComposition(
            HTMaterialKeys.ASHES,
            HTMaterialComposition.molecular {
                color = HTColor.DARK_GRAY
            },
        )

        materialHelper.addMaterialKey(HTMaterialKeys.BAUXITE)
        materialHelper.setComposition(
            HTMaterialKeys.BAUXITE,
            HTMaterialComposition.hydrate(
                HTMaterialComposition.molecular(HTElements.Al2O3 to 1),
                2,
            ) { color = averageColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1) },
        )

        materialHelper.addMaterialKey(HTMaterialKeys.RUBBER)
        materialHelper.setComposition(
            HTMaterialKeys.RUBBER,
            HTMaterialComposition.polymer(HTElements.C to 5, HTElements.H to 6) {
                color = averageColor(HTColor.BLACK, HTColor.DARK_GRAY)
                formula = "CC(=C)C=C"
            },
        )
        // Common - Stones
        materialHelper.addStoneMaterial(
            HTMaterialKeys.MARBLE,
            HTMaterialComposition.molecular(HTElements.Ca to 1, HTElements.CO3 to 1),
        )
        // Common - Woods
    }

    override fun registerFluidRegistry(registry: HTFluidRegistry) {
        // Register vanilla fluids
        registry.add(Fluids.WATER, HTMaterialKeys.WATER)
        registry.add(Fluids.LAVA, HTMaterialKeys.LAVA)
        // Register common tags
        HTMaterialsAPI.INSTANCE.materialRegistry().keys.forEach { key: HTMaterialKey ->
            registry.add(TagRegistry.fluid(key.commonId), key)
        }
    }

    override fun registerPartRegistry(registry: HTPartRegistry) {
        // Register vanilla items
        registerVanillaItems(registry)
        // Register part tags
        HTMaterialsAPI.INSTANCE.shapeRegistry().values.forEach { shape: HTShape ->
            HTMaterialsAPI.INSTANCE.materialRegistry().keys.forEach { key: HTMaterialKey ->
                registry.add(HTPart(key, shape).partTag, key, shape)
            }
        }
    }

    private fun registerVanillaItems(registry: HTPartRegistry) {
        // Andesite
        registry.add(Items.ANDESITE, HTMaterialKeys.ANDESITE, HTShapes.BLOCK)
        registry.add(Items.POLISHED_ANDESITE, HTMaterialKeys.ANDESITE, HTShapes.BLOCK)
        // Basalt
        registry.add(Items.BASALT, HTMaterialKeys.BASALT, HTShapes.BLOCK)
        registry.add(Items.POLISHED_BASALT, HTMaterialKeys.BASALT, HTShapes.BLOCK)
        // Blackstone
        registry.add(Items.BLACKSTONE, HTMaterialKeys.BLACKSTONE, HTShapes.BLOCK)
        registry.add(Items.CHISELED_POLISHED_BLACKSTONE, HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS)
        registry.add(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS)
        registry.add(Items.POLISHED_BLACKSTONE, HTMaterialKeys.BLACKSTONE, HTShapes.BLOCK)
        registry.add(Items.POLISHED_BLACKSTONE_BRICKS, HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS)
        // Brick
        registry.add(Items.BRICK, HTMaterialKeys.BRICK, HTShapes.INGOT)
        registry.add(Items.BRICKS, HTMaterialKeys.BRICK, HTShapes.BRICKS)
        // Charcoal
        registry.add(Items.CHARCOAL, HTMaterialKeys.CHARCOAL, HTShapes.GEM)
        // Clay
        registry.add(Items.CLAY, HTMaterialKeys.CLAY, HTShapes.BLOCK)
        registry.add(Items.CLAY_BALL, HTMaterialKeys.CLAY, HTShapes.GEM)
        // Coal
        registry.add(Items.COAL, HTMaterialKeys.COAL, HTShapes.GEM)
        registry.add(Items.COAL_BLOCK, HTMaterialKeys.COAL, HTShapes.BLOCK)
        // Diamond
        registry.add(Items.DIAMOND, HTMaterialKeys.DIAMOND, HTShapes.GEM)
        registry.add(Items.DIAMOND_BLOCK, HTMaterialKeys.DIAMOND, HTShapes.BLOCK)
        registry.add(Items.DIAMOND_ORE, HTMaterialKeys.DIAMOND, HTShapes.ORE)
        // Diorite
        registry.add(Items.DIORITE, HTMaterialKeys.DIORITE, HTShapes.BLOCK)
        registry.add(Items.POLISHED_DIORITE, HTMaterialKeys.DIORITE, HTShapes.BLOCK)
        // Emerald
        registry.add(Items.EMERALD, HTMaterialKeys.EMERALD, HTShapes.GEM)
        registry.add(Items.EMERALD_BLOCK, HTMaterialKeys.EMERALD, HTShapes.BLOCK)
        registry.add(Items.EMERALD_ORE, HTMaterialKeys.EMERALD, HTShapes.ORE)
        // End Stone
        registry.add(Items.END_STONE, HTMaterialKeys.END_STONE, HTShapes.BLOCK)
        registry.add(Items.END_STONE_BRICKS, HTMaterialKeys.END_STONE, HTShapes.BRICKS)
        // Ender Pearl
        registry.add(Items.ENDER_PEARL, HTMaterialKeys.ENDER_PEARL, HTShapes.GEM)
        // Flint
        registry.add(Items.FLINT, HTMaterialKeys.FLINT, HTShapes.GEM)
        // Iron
        registry.add(Items.IRON_BLOCK, HTMaterialKeys.IRON, HTShapes.BLOCK)
        registry.add(Items.IRON_INGOT, HTMaterialKeys.IRON, HTShapes.INGOT)
        registry.add(Items.IRON_NUGGET, HTMaterialKeys.IRON, HTShapes.NUGGET)
        registry.add(Items.IRON_ORE, HTMaterialKeys.IRON, HTShapes.ORE)
        // Glass
        registry.add(Items.GLASS, HTMaterialKeys.GLASS, HTShapes.BLOCK)
        // Glowstone
        registry.add(Items.GLOWSTONE, HTMaterialKeys.GLOWSTONE, HTShapes.BLOCK)
        registry.add(Items.GLOWSTONE_DUST, HTMaterialKeys.GLOWSTONE, HTShapes.DUST)
        // Gold
        registry.add(Items.GOLD_BLOCK, HTMaterialKeys.GOLD, HTShapes.BLOCK)
        registry.add(Items.GOLD_INGOT, HTMaterialKeys.GOLD, HTShapes.INGOT)
        registry.add(Items.GOLD_NUGGET, HTMaterialKeys.GOLD, HTShapes.NUGGET)
        registry.add(Items.GOLD_ORE, HTMaterialKeys.GOLD, HTShapes.ORE)
        // Granite
        registry.add(Items.GRANITE, HTMaterialKeys.GRANITE, HTShapes.BLOCK)
        registry.add(Items.POLISHED_GRANITE, HTMaterialKeys.GRANITE, HTShapes.BLOCK)
        // Lapis
        registry.add(Items.LAPIS_BLOCK, HTMaterialKeys.LAPIS, HTShapes.BLOCK)
        registry.add(Items.LAPIS_LAZULI, HTMaterialKeys.LAPIS, HTShapes.GEM)
        registry.add(Items.LAPIS_ORE, HTMaterialKeys.LAPIS, HTShapes.ORE)
        // Nether Brick
        registry.add(Items.NETHER_BRICKS, HTMaterialKeys.NETHER_BRICK, HTShapes.BRICKS)
        registry.add(Items.NETHER_BRICK, HTMaterialKeys.NETHER_BRICK, HTShapes.INGOT)
        // Netherite
        registry.add(Items.NETHERITE_BLOCK, HTMaterialKeys.NETHERITE, HTShapes.BLOCK)
        registry.add(Items.NETHERITE_INGOT, HTMaterialKeys.NETHERITE, HTShapes.INGOT)
        // Netherrack
        registry.add(Items.NETHERRACK, HTMaterialKeys.NETHERRACK, HTShapes.BLOCK)
        // Obsidian
        registry.add(Items.OBSIDIAN, HTMaterialKeys.OBSIDIAN, HTShapes.BLOCK)
        // Quartz
        registry.add(Items.NETHER_QUARTZ_ORE, HTMaterialKeys.QUARTZ, HTShapes.ORE)
        registry.add(Items.QUARTZ, HTMaterialKeys.QUARTZ, HTShapes.GEM)
        registry.add(Items.QUARTZ_BLOCK, HTMaterialKeys.QUARTZ, HTShapes.BLOCK)
        registry.add(Items.SMOOTH_QUARTZ, HTMaterialKeys.QUARTZ, HTShapes.BLOCK)
        // Redstone
        registry.add(Items.REDSTONE, HTMaterialKeys.REDSTONE, HTShapes.DUST)
        registry.add(Items.REDSTONE_BLOCK, HTMaterialKeys.REDSTONE, HTShapes.BLOCK)
        registry.add(Items.REDSTONE_ORE, HTMaterialKeys.REDSTONE, HTShapes.ORE)
        // Stone
        registry.add(Items.CRACKED_STONE_BRICKS, HTMaterialKeys.STONE, HTShapes.BRICKS)
        registry.add(Items.MOSSY_STONE_BRICKS, HTMaterialKeys.STONE, HTShapes.BRICKS)
        registry.add(Items.SMOOTH_STONE, HTMaterialKeys.STONE, HTShapes.BLOCK)
        registry.add(Items.STONE, HTMaterialKeys.STONE, HTShapes.BLOCK)
        registry.add(Items.STONE_BRICKS, HTMaterialKeys.STONE, HTShapes.BRICKS)
        // Wood
        listOf(
            Items.OAK_LOG,
            Items.BIRCH_LOG,
            Items.SPRUCE_LOG,
            Items.JUNGLE_LOG,
            Items.ACACIA_LOG,
            Items.DARK_OAK_LOG,
            Items.CRIMSON_HYPHAE,
            Items.WARPED_HYPHAE,
        ).forEach { registry.add(it, HTMaterialKeys.WOOD, HTShapes.LOG) }
        listOf(
            Items.OAK_WOOD,
            Items.BIRCH_WOOD,
            Items.SPRUCE_WOOD,
            Items.JUNGLE_WOOD,
            Items.ACACIA_WOOD,
            Items.DARK_OAK_WOOD,
            Items.CRIMSON_STEM,
            Items.WARPED_STEM,
        ).forEach { registry.add(it, HTMaterialKeys.WOOD, HTShapes.LOG) }
        listOf(
            Items.OAK_PLANKS,
            Items.BIRCH_PLANKS,
            Items.SPRUCE_PLANKS,
            Items.JUNGLE_PLANKS,
            Items.ACACIA_PLANKS,
            Items.DARK_OAK_PLANKS,
            Items.CRIMSON_PLANKS,
            Items.WARPED_PLANKS,
        ).forEach { registry.add(it, HTMaterialKeys.WOOD, HTShapes.PLANKS) }
    }
}
