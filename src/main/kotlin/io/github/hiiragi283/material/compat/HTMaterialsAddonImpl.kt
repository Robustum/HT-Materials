package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.*
import io.github.hiiragi283.api.material.composition.HTHydrateComposition
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.composition.HTMixtureComposition
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.material.element.HTElements
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.HTColor
import io.github.hiiragi283.api.util.addAll
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.material.impl.material.content.HTSimpleFluidContent
import io.github.hiiragi283.material.impl.material.content.HTSimpleItemContent
import io.github.hiiragi283.material.impl.material.content.HTStorageBlockContent
import io.github.hiiragi283.material.impl.material.property.HTCompoundProperty
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import java.awt.Color

object HTMaterialsAddonImpl : HTMaterialsAddon {
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
        registry[HTMaterialKeys.HYDROGEN] = HTMaterialComposition.create(HTElements.H to 2)
        registry[HTMaterialKeys.HELIUM] = HTMaterialComposition.create(HTElements.He to 1)
        // 2nd Period
        registry[HTMaterialKeys.LITHIUM] = HTMaterialComposition.create(HTElements.Li to 1)
        registry[HTMaterialKeys.BERYLLIUM] = HTMaterialComposition.create(HTElements.Be to 1)
        registry[HTMaterialKeys.CARBON] = HTMaterialComposition.create(HTElements.C to 1)
        registry[HTMaterialKeys.NITROGEN] = HTMaterialComposition.create(HTElements.N to 2)
        registry[HTMaterialKeys.OXYGEN] = HTMaterialComposition.create(HTElements.O to 2)
        registry[HTMaterialKeys.FLUORINE] = HTMaterialComposition.create(HTElements.F to 2)
        // 3rd Period
        registry[HTMaterialKeys.SODIUM] = HTMaterialComposition.create(HTElements.Na to 1)
        registry[HTMaterialKeys.MAGNESIUM] = HTMaterialComposition.create(HTElements.Mg to 1)
        registry[HTMaterialKeys.ALUMINUM] = HTMaterialComposition.create(HTElements.Al to 1)
        registry[HTMaterialKeys.SILICON] = HTMaterialComposition.create(HTElements.Si to 1)
        registry[HTMaterialKeys.PHOSPHORUS] = HTMaterialComposition.create(HTElements.P to 1)
        registry[HTMaterialKeys.SULFUR] = HTMaterialComposition.create(HTElements.S to 8)
        registry[HTMaterialKeys.CHLORINE] = HTMaterialComposition.create(HTElements.Cl to 2)
        // 4th Period
        registry[HTMaterialKeys.POTASSIUM] = HTMaterialComposition.create(HTElements.K to 1)
        registry[HTMaterialKeys.CALCIUM] = HTMaterialComposition.create(HTElements.Ca to 1)
        registry[HTMaterialKeys.TITANIUM] = HTMaterialComposition.create(HTElements.Ti to 1)
        registry[HTMaterialKeys.CHROMIUM] = HTMaterialComposition.create(HTElements.Cr to 1)
        registry[HTMaterialKeys.MANGANESE] = HTMaterialComposition.create(HTElements.Mn to 1)
        registry[HTMaterialKeys.IRON] = HTMaterialComposition.create(HTElements.Fe to 1)
        registry[HTMaterialKeys.COBALT] = HTMaterialComposition.create(HTElements.Co to 1)
        registry[HTMaterialKeys.NICKEL] = HTMaterialComposition.create(HTElements.Ni to 1)
        registry[HTMaterialKeys.COPPER] = HTMaterialComposition.create(HTElements.Cu to 1)
        registry[HTMaterialKeys.ZINC] = HTMaterialComposition.create(HTElements.Zn to 1)

        // Vanilla - Fluids
        registry[HTMaterialKeys.WATER] = HTMaterialComposition.build {
            map[HTElements.H] = 2
            map[HTElements.O] = 1
            color = HTColor::BLUE
        }
        registry[HTMaterialKeys.WATER] = HTMaterialComposition.build {
            map[HTElements.SiO2] = 1
            color = { ColorConvertible.average(HTColor.DARK_RED, HTColor.GOLD) }
        }
        // Vanilla - Gems
        registry[HTMaterialKeys.AMETHYST] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.DIAMOND] = HTMaterialComposition.create(HTElements.C to 1)
        registry[HTMaterialKeys.ENDER_PEARL]
        registry[HTMaterialKeys.EMERALD] = HTMaterialComposition.create(
            HTElements.Be to 3,
            HTElements.Al to 2,
            HTElements.Si to 6,
            HTElements.O to 18,
        )
        registry[HTMaterialKeys.FLINT] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.LAPIS]
        registry[HTMaterialKeys.QUARTZ] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        // Vanilla - Metals
        registry[HTMaterialKeys.NETHERITE]
        // Vanilla - Solids
        registry[HTMaterialKeys.BRICK]
        registry[HTMaterialKeys.CHARCOAL] = HTMaterialComposition.create(HTElements.C to 1)
        registry[HTMaterialKeys.CLAY]
        registry[HTMaterialKeys.COAL] = HTMaterialComposition.create(HTElements.C to 1)
        registry[HTMaterialKeys.GLASS] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.GLOWSTONE]
        registry[HTMaterialKeys.NETHER_BRICK]
        registry[HTMaterialKeys.REDSTONE]
        // Vanilla - Stones
        registry[HTMaterialKeys.STONE] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.GRANITE] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.DIORITE] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.ANDESITE] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.DEEPSLATE] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.CALCITE] = HTMaterialComposition.create(HTElements.Ca to 1, HTElements.CO3 to 1)
        registry[HTMaterialKeys.TUFF] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.OBSIDIAN] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.NETHERRACK] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.BASALT] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        registry[HTMaterialKeys.END_STONE] = HTMaterialComposition.create(HTElements.SiO2 to 1)
        // Vanilla - Woods
        registry[HTMaterialKeys.WOOD] = HTMixtureComposition(HTElements.C, HTElements.H, HTElements.O)
        // Common - Fluids
        // Common - Gems
        registry[HTMaterialKeys.CINNABAR]
        registry[HTMaterialKeys.COKE] = HTMaterialComposition.create(HTElements.C to 1)
        registry[HTMaterialKeys.OLIVINE]
        registry[HTMaterialKeys.PERIDOT]
        registry[HTMaterialKeys.RUBY] = HTMaterialComposition.create(HTElements.Al2O3 to 1)
        registry[HTMaterialKeys.SALT] = HTMaterialComposition.create(HTElements.Na to 1, HTElements.Cl to 1)
        registry[HTMaterialKeys.SAPPHIRE] = HTMaterialComposition.create(HTElements.Al2O3 to 1)
        // Common - Metals
        registry[HTMaterialKeys.STEEl] = HTMixtureComposition(HTElements.Fe, HTElements.C)
        // Common - Solids
        registry[HTMaterialKeys.ASHES]
        registry[HTMaterialKeys.BAUXITE] = HTHydrateComposition(HTMaterialComposition.create(HTElements.Al2O3 to 1), 2)
        registry[HTMaterialKeys.RUBBER] = HTMaterialComposition.override(
            HTMaterialComposition.create(HTElements.C to 5, HTElements.H to 6),
        ) { formula = { "CC(=C)C=C" } }
        // Common - Stones
        registry[HTMaterialKeys.MARBLE] = HTMaterialComposition.create(HTElements.Ca to 1, HTElements.CO3 to 1)
        // Common - Woods
    }

    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN).apply {
            add(HTSimpleFluidContent())
        }
        registry.getOrCreate(HTMaterialKeys.HELIUM).apply {
            add(HTSimpleFluidContent())
        }
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.CARBON).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
        }
        registry.getOrCreate(HTMaterialKeys.NITROGEN).apply {
            add(HTSimpleFluidContent())
        }
        registry.getOrCreate(HTMaterialKeys.OXYGEN).apply {
            add(HTSimpleFluidContent())
        }
        registry.getOrCreate(HTMaterialKeys.FLUORINE).apply {
            add(HTSimpleFluidContent())
        }
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.ALUMINUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.SILICON).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.PHOSPHORUS).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.SULFUR).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.CHLORINE).apply {
            add(HTSimpleFluidContent())
        }
        // 4th Period
        registry.getOrCreate(HTMaterialKeys.TITANIUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 3,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.IRON).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.NICKEL).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.COPPER).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )

            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.ZINC).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // 5th Period
        registry.getOrCreate(HTMaterialKeys.SILVER).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.TIN).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.TUNGSTEN).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 3,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.IRIDIUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 3,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.PLATINUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 3,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.GOLD).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.MERCURY).apply {
            add(HTSimpleFluidContent())
        }
        registry.getOrCreate(HTMaterialKeys.LEAD).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // 7th Period
        registry.getOrCreate(HTMaterialKeys.URANIUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.PLUTONIUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // Vanilla - Fluids
        // Vanilla - Gems
        registry.getOrCreate(HTMaterialKeys.AMETHYST).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
        }
        registry.getOrCreate(HTMaterialKeys.DIAMOND).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.ENDER_PEARL).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.EMERALD).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.FLINT).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.LAPIS).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.QUARTZ).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // Vanilla - Metals
        registry.getOrCreate(HTMaterialKeys.NETHERITE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // Vanilla - Solids
        registry.getOrCreate(HTMaterialKeys.BRICK).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.CHARCOAL).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.CLAY).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.COAL).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.GLASS).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.GLOWSTONE)
        registry.getOrCreate(HTMaterialKeys.NETHER_BRICK).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.REDSTONE)
        // Vanilla - Stones
        registry.getOrCreate(HTMaterialKeys.STONE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.GRANITE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.DIORITE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.ANDESITE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.DEEPSLATE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.CALCITE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.TUFF).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.OBSIDIAN).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.NETHERRACK).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.BASALT).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.END_STONE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        // Vanilla - Woods
        registry.getOrCreate(HTMaterialKeys.WOOD).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
        }
        // Common - Fluids
        // Common - Gems
        registry.getOrCreate(HTMaterialKeys.CINNABAR).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
        }
        registry.getOrCreate(HTMaterialKeys.COKE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
        }
        registry.getOrCreate(HTMaterialKeys.OLIVINE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
        }
        registry.getOrCreate(HTMaterialKeys.PERIDOT).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.RUBY).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.SALT).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::SHOVELS,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.SAPPHIRE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // Common - Metals
        registry.getOrCreate(HTMaterialKeys.BRASS).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.BRONZE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.ELECTRUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.INVAR).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.STAINLESS_STEEL).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        registry.getOrCreate(HTMaterialKeys.STEEl).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // Common - Solids
        registry.getOrCreate(HTMaterialKeys.ASHES).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.BAUXITE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        registry.getOrCreate(HTMaterialKeys.RUBBER).apply {
            add(HTStorageBlockContent())
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }
        // Common - Stones
        registry.getOrCreate(HTMaterialKeys.MARBLE).apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
        }
        // Common - Woods
    }

    override fun modifyMaterialProperty(registry: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {
        // Common - Gems
        registry.getOrCreate(HTMaterialKeys.CINNABAR).apply {
            add(HTCompoundProperty(HTMaterialKeys.MERCURY to 1, HTMaterialKeys.SULFUR to 1))
        }
        // Common - Metals
        registry.getOrCreate(HTMaterialKeys.BRASS).apply {
            add(HTCompoundProperty(HTMaterialKeys.COPPER to 3, HTMaterialKeys.ZINC to 1))
        }
        registry.getOrCreate(HTMaterialKeys.BRONZE).apply {
            add(HTCompoundProperty(HTMaterialKeys.COPPER to 3, HTMaterialKeys.TIN to 1))
        }
        registry.getOrCreate(HTMaterialKeys.ELECTRUM).apply {
            add(HTCompoundProperty(HTMaterialKeys.SILVER to 1, HTMaterialKeys.GOLD to 1))
        }
        registry.getOrCreate(HTMaterialKeys.INVAR).apply {
            add(HTCompoundProperty(HTMaterialKeys.IRON to 2, HTMaterialKeys.NICKEL to 1))
        }
        registry.getOrCreate(HTMaterialKeys.STAINLESS_STEEL).apply {
            add(
                HTCompoundProperty(
                    HTMaterialKeys.IRON to 6,
                    HTMaterialKeys.CHROMIUM to 1,
                    HTMaterialKeys.MANGANESE to 1,
                    HTMaterialKeys.NICKEL to 1,
                ),
            )
        }
    }

    override fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {
        // 5th Period
        registry[HTMaterialKeys.SILVER] = ColorConvertible.ofColor(HTColor.AQUA to 1, HTColor.WHITE to 3)
        registry[HTMaterialKeys.TIN] = ColorConvertible.ofColor(HTColor.BLUE to 1, HTColor.AQUA to 1, HTColor.WHITE to 3)
        // 6th Period
        registry[HTMaterialKeys.TUNGSTEN] = ColorConvertible.ofColor(HTColor.BLACK to 2, HTColor.DARK_GRAY to 1)
        registry[HTMaterialKeys.IRIDIUM] = ColorConvertible { HTColor.WHITE }
        registry[HTMaterialKeys.PLATINUM] = ColorConvertible { Color(0x87cefa) }
        registry[HTMaterialKeys.GOLD] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW)
        registry[HTMaterialKeys.MERCURY] = ColorConvertible { HTColor.WHITE }
        registry[HTMaterialKeys.LEAD] = ColorConvertible.ofColor(HTColor.DARK_BLUE, HTColor.DARK_GRAY, HTColor.WHITE)
        // 7th Period
        registry[HTMaterialKeys.URANIUM] = ColorConvertible { HTColor.GREEN }
        registry[HTMaterialKeys.PLUTONIUM] = ColorConvertible { HTColor.RED }
        // Vanilla - Gems
        registry[HTMaterialKeys.AMETHYST] = ColorConvertible.ofColor(HTColor.BLUE, HTColor.LIGHT_PURPLE)
        registry[HTMaterialKeys.DIAMOND] = ColorConvertible { HTColor.AQUA }
        registry[HTMaterialKeys.ENDER_PEARL] = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.BLUE)
        registry[HTMaterialKeys.EMERALD] = ColorConvertible { HTColor.GREEN }
        registry[HTMaterialKeys.FLINT] = ColorConvertible.ofColor(HTColor.BLACK to 3, HTColor.BLUE to 1)
        registry[HTMaterialKeys.LAPIS] = ColorConvertible { HTColor.BLUE }
        registry[HTMaterialKeys.QUARTZ] = ColorConvertible { HTColor.WHITE }
        // Vanilla - Metals
        registry[HTMaterialKeys.NETHERITE] = ColorConvertible.ofColor(
            HTColor.BLACK to 5,
            HTColor.DARK_BLUE to 1,
            HTColor.DARK_RED to 1,
            HTColor.YELLOW to 1,
        )
        // Vanilla - Solids
        registry[HTMaterialKeys.BRICK] = ColorConvertible.ofColor(HTColor.DARK_RED to 2, HTColor.GOLD to 1, HTColor.DARK_GRAY to 2)
        registry[HTMaterialKeys.CHARCOAL] = ColorConvertible.ofColor(HTColor.BLACK to 7, HTColor.YELLOW to 1)
        registry[HTMaterialKeys.CLAY] = ColorConvertible { Color(0xa4a8b8) }
        registry[HTMaterialKeys.COAL]
        registry[HTMaterialKeys.GLASS] = ColorConvertible { HTColor.WHITE }
        registry[HTMaterialKeys.GLOWSTONE] = ColorConvertible.ofColor(HTColor.GOLD to 1, HTColor.YELLOW to 2)
        registry[HTMaterialKeys.NETHER_BRICK] = ColorConvertible.ofColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.WHITE to 1)
        registry[HTMaterialKeys.REDSTONE] = ColorConvertible { HTColor.DARK_RED }
        // Vanilla - Stones
        registry[HTMaterialKeys.STONE] = ColorConvertible { HTColor.DARK_GRAY }
        registry[HTMaterialKeys.GRANITE] = ColorConvertible.ofColor(HTColor.DARK_RED to 1, HTColor.GRAY to 4, HTColor.RED to 1)
        registry[HTMaterialKeys.DIORITE] = ColorConvertible { HTColor.GRAY }
        registry[HTMaterialKeys.ANDESITE] = ColorConvertible.ofColor(HTColor.DARK_GRAY to 7, HTColor.YELLOW to 1)
        registry[HTMaterialKeys.DEEPSLATE] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        registry[HTMaterialKeys.CALCITE]
        registry[HTMaterialKeys.TUFF] = ColorConvertible { Color(0x4d5d53) }
        registry[HTMaterialKeys.OBSIDIAN] = ColorConvertible.ofColor(
            HTColor.BLACK to 4,
            HTColor.DARK_BLUE to 2,
            HTColor.DARK_RED to 1,
            HTColor.WHITE to 1,
        )
        registry[HTMaterialKeys.NETHERRACK] = ColorConvertible.ofColor(HTColor.BLACK to 4, HTColor.DARK_RED to 1, HTColor.RED to 3)
        registry[HTMaterialKeys.BASALT] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.GRAY)
        registry[HTMaterialKeys.END_STONE] = ColorConvertible.ofColor(HTColor.YELLOW to 1, HTColor.WHITE to 3)
        // Vanilla - Woods
        registry[HTMaterialKeys.WOOD] = ColorConvertible.ofColor(HTColor.DARK_GRAY to 2, HTColor.RED to 1, HTColor.YELLOW to 1)
        // Common - Fluids
        // Common - Gems
        registry[HTMaterialKeys.CINNABAR] = ColorConvertible { HTColor.RED }
        registry[HTMaterialKeys.COKE] = ColorConvertible { HTColor.DARK_GRAY }
        registry[HTMaterialKeys.OLIVINE] = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.GREEN)
        registry[HTMaterialKeys.PERIDOT] = ColorConvertible.ofColor(HTColor.GREEN, HTColor.WHITE)
        registry[HTMaterialKeys.RUBY] = ColorConvertible { HTColor.RED }
        registry[HTMaterialKeys.SALT] = ColorConvertible { HTColor.WHITE }
        registry[HTMaterialKeys.SAPPHIRE] = ColorConvertible { HTColor.BLUE }
        // Common - Metals
        registry[HTMaterialKeys.BRASS] = ColorConvertible { HTColor.GOLD }
        registry[HTMaterialKeys.BRONZE]
        registry[HTMaterialKeys.ELECTRUM] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
        registry[HTMaterialKeys.INVAR] = ColorConvertible.ofColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
        registry[HTMaterialKeys.STAINLESS_STEEL] = ColorConvertible.ofColor(HTColor.GRAY, HTColor.WHITE)
        registry[HTMaterialKeys.STEEl] = ColorConvertible { HTColor.DARK_GRAY }
        // Common - Solids
        registry[HTMaterialKeys.ASHES] = ColorConvertible { HTColor.DARK_GRAY }
        registry[HTMaterialKeys.BAUXITE] = ColorConvertible.ofColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1)
        registry[HTMaterialKeys.RUBBER] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        // Common - Stones
        registry[HTMaterialKeys.MARBLE] = ColorConvertible { HTColor.WHITE }
        // Common - Woods
    }

    override fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {
        // 5th Period
        registry[HTMaterialKeys.SILVER] = FormulaConvertible { "Ag" }
        registry[HTMaterialKeys.TIN] = FormulaConvertible { "Sn" }
        // 6th Period
        registry[HTMaterialKeys.TUNGSTEN] = FormulaConvertible { "W" }
        registry[HTMaterialKeys.IRIDIUM] = FormulaConvertible { "Ir" }
        registry[HTMaterialKeys.PLATINUM] = FormulaConvertible { "Pt" }
        registry[HTMaterialKeys.GOLD] = FormulaConvertible { "Au" }
        registry[HTMaterialKeys.MERCURY] = FormulaConvertible { "Hg" }
        registry[HTMaterialKeys.LEAD] = FormulaConvertible { "Pb" }
        // 7th Period
        registry[HTMaterialKeys.URANIUM] = FormulaConvertible { "U" }
        registry[HTMaterialKeys.PLUTONIUM] = FormulaConvertible { "Pu" }
        // Vanilla - Fluids
        registry[HTMaterialKeys.WATER]
        registry[HTMaterialKeys.LAVA]
        // Vanilla - Gems
        registry[HTMaterialKeys.AMETHYST]
        registry[HTMaterialKeys.DIAMOND]
        registry[HTMaterialKeys.ENDER_PEARL]
        registry[HTMaterialKeys.EMERALD]
        registry[HTMaterialKeys.FLINT]
        registry[HTMaterialKeys.LAPIS]
        registry[HTMaterialKeys.QUARTZ]
        // Vanilla - Metals
        registry[HTMaterialKeys.NETHERITE] = FormulaConvertible { "Nr" }
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
        registry[HTMaterialKeys.STONE]
        registry[HTMaterialKeys.GRANITE]
        registry[HTMaterialKeys.DIORITE]
        registry[HTMaterialKeys.ANDESITE]
        registry[HTMaterialKeys.DEEPSLATE]
        registry[HTMaterialKeys.CALCITE]
        registry[HTMaterialKeys.TUFF]
        registry[HTMaterialKeys.OBSIDIAN]
        registry[HTMaterialKeys.NETHERRACK]
        registry[HTMaterialKeys.BASALT]
        registry[HTMaterialKeys.END_STONE]
        // Vanilla - Wood
        registry[HTMaterialKeys.WOOD] = FormulaConvertible { "C, H, O" }
        // Common - Fluids
        // Common - Gems
        registry[HTMaterialKeys.CINNABAR]
        registry[HTMaterialKeys.COKE]
        registry[HTMaterialKeys.OLIVINE]
        registry[HTMaterialKeys.PERIDOT]
        registry[HTMaterialKeys.RUBY]
        registry[HTMaterialKeys.SALT]
        registry[HTMaterialKeys.SAPPHIRE]
        // Common - Metals
        registry[HTMaterialKeys.BRASS]
        registry[HTMaterialKeys.BRONZE]
        registry[HTMaterialKeys.ELECTRUM]
        registry[HTMaterialKeys.INVAR]
        registry[HTMaterialKeys.STAINLESS_STEEL]
        registry[HTMaterialKeys.STEEl] = FormulaConvertible { "Fe, C" }
        // Common - Solids
        registry[HTMaterialKeys.ASHES]
        registry[HTMaterialKeys.BAUXITE]
        registry[HTMaterialKeys.RUBBER]
        // Common - Stones
        registry[HTMaterialKeys.MARBLE]
        // Common - Woods
    }

    override fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {
        // 5th Period
        registry[HTMaterialKeys.SILVER] = MolarMassConvertible { 107.9 }
        registry[HTMaterialKeys.TIN] = MolarMassConvertible { 118.7 }
        // 6th Period
        registry[HTMaterialKeys.TUNGSTEN] = MolarMassConvertible { 183.8 }
        registry[HTMaterialKeys.IRIDIUM] = MolarMassConvertible { 192.2 }
        registry[HTMaterialKeys.PLATINUM] = MolarMassConvertible { 195.1 }
        registry[HTMaterialKeys.GOLD] = MolarMassConvertible { 197.0 }
        registry[HTMaterialKeys.MERCURY] = MolarMassConvertible { 200.6 }
        registry[HTMaterialKeys.LEAD] = MolarMassConvertible { 207.2 }
        // 7th Period
        registry[HTMaterialKeys.URANIUM] = MolarMassConvertible { 238.0 }
        registry[HTMaterialKeys.PLUTONIUM] = MolarMassConvertible { 244.1 }
        // Vanilla - Fluids
        registry[HTMaterialKeys.WATER]
        registry[HTMaterialKeys.LAVA]
        // Vanilla - Gems
        registry[HTMaterialKeys.AMETHYST]
        registry[HTMaterialKeys.DIAMOND]
        registry[HTMaterialKeys.ENDER_PEARL]
        registry[HTMaterialKeys.EMERALD]
        registry[HTMaterialKeys.FLINT]
        registry[HTMaterialKeys.LAPIS]
        registry[HTMaterialKeys.QUARTZ]
        // Vanilla - Metals
        registry[HTMaterialKeys.NETHERITE]
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
        registry[HTMaterialKeys.STONE]
        registry[HTMaterialKeys.GRANITE]
        registry[HTMaterialKeys.DIORITE]
        registry[HTMaterialKeys.ANDESITE]
        registry[HTMaterialKeys.DEEPSLATE]
        registry[HTMaterialKeys.CALCITE]
        registry[HTMaterialKeys.TUFF]
        registry[HTMaterialKeys.OBSIDIAN]
        registry[HTMaterialKeys.NETHERRACK]
        registry[HTMaterialKeys.BASALT]
        registry[HTMaterialKeys.END_STONE]
        // Vanilla - Wood
        registry[HTMaterialKeys.WOOD]
        // Common - Fluids
        // Common - Gems
        // Common - Metals
        // Common - Solids
        // Common - Stones
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
}
