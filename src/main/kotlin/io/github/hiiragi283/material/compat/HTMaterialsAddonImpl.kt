package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.*
import io.github.hiiragi283.api.material.MolarMassConvertible
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
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
import io.github.hiiragi283.material.impl.material.property.HTMixtureProperty
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
    }

    override fun modifyMaterialProperty(registry: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
        registry.getOrCreate(HTMaterialKeys.HELIUM)
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.LITHIUM)
        registry.getOrCreate(HTMaterialKeys.BERYLLIUM)
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.SODIUM)
        registry.getOrCreate(HTMaterialKeys.MAGNESIUM)
        registry.getOrCreate(HTMaterialKeys.ALUMINUM)
        registry.getOrCreate(HTMaterialKeys.SILICON)
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
        // 4th Period
        registry.getOrCreate(HTMaterialKeys.POTASSIUM)
        registry.getOrCreate(HTMaterialKeys.CALCIUM)
        registry.getOrCreate(HTMaterialKeys.TITANIUM)
        registry.getOrCreate(HTMaterialKeys.CHROMIUM)
        registry.getOrCreate(HTMaterialKeys.MANGANESE)
        registry.getOrCreate(HTMaterialKeys.IRON)
        registry.getOrCreate(HTMaterialKeys.COBALT)
        registry.getOrCreate(HTMaterialKeys.NICKEL)
        registry.getOrCreate(HTMaterialKeys.COPPER)
        registry.getOrCreate(HTMaterialKeys.ZINC)
        // 5th Period
        registry.getOrCreate(HTMaterialKeys.SILVER)
        registry.getOrCreate(HTMaterialKeys.TIN)
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.TUNGSTEN)
        registry.getOrCreate(HTMaterialKeys.IRIDIUM)
        registry.getOrCreate(HTMaterialKeys.PLATINUM)
        registry.getOrCreate(HTMaterialKeys.GOLD)
        registry.getOrCreate(HTMaterialKeys.MERCURY)
        registry.getOrCreate(HTMaterialKeys.LEAD)
        // 7th Period
        registry.getOrCreate(HTMaterialKeys.URANIUM)
        registry.getOrCreate(HTMaterialKeys.PLUTONIUM)
        // Vanilla - Fluids
        registry.getOrCreate(HTMaterialKeys.WATER)
            .add(HTCompoundProperty(HTMaterialKeys.HYDROGEN to 2, HTMaterialKeys.OXYGEN to 1))
        registry.getOrCreate(HTMaterialKeys.LAVA)
            .add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        // Vanilla - Gems
        registry.getOrCreate(HTMaterialKeys.AMETHYST).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.DIAMOND).apply {
            add(HTCompoundProperty(HTMaterialKeys.CARBON to 1))
        }
        registry.getOrCreate(HTMaterialKeys.ENDER_PEARL)
        registry.getOrCreate(HTMaterialKeys.EMERALD).apply {
            add(
                HTCompoundProperty(
                    HTMaterialKeys.BERYLLIUM to 3,
                    HTMaterialKeys.ALUMINUM to 2,
                    HTMaterialKeys.SILICON to 6,
                    HTMaterialKeys.OXYGEN to 18,
                ),
            )
        }
        registry.getOrCreate(HTMaterialKeys.FLINT).apply {
            add(HTCompoundProperty(HTMaterialKeys.CARBON to 1))
        }
        registry.getOrCreate(HTMaterialKeys.LAPIS)
        registry.getOrCreate(HTMaterialKeys.QUARTZ).apply {
            add(HTCompoundProperty(HTMaterialKeys.CARBON to 1))
        }
        // Vanilla - Metals
        registry.getOrCreate(HTMaterialKeys.NETHERITE)
        // Vanilla - Solids
        registry.getOrCreate(HTMaterialKeys.BRICK)
        registry.getOrCreate(HTMaterialKeys.CHARCOAL).add(HTCompoundProperty(HTMaterialKeys.CARBON to 1))
        registry.getOrCreate(HTMaterialKeys.CLAY)
        registry.getOrCreate(HTMaterialKeys.COAL).add(HTCompoundProperty(HTMaterialKeys.CARBON to 1))
        registry.getOrCreate(HTMaterialKeys.GLASS).add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        registry.getOrCreate(HTMaterialKeys.GLOWSTONE)
        registry.getOrCreate(HTMaterialKeys.NETHER_BRICK)
        registry.getOrCreate(HTMaterialKeys.REDSTONE)
        // Vanilla - Stones
        registry.getOrCreate(HTMaterialKeys.STONE).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.GRANITE).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.DIORITE).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.ANDESITE).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.DEEPSLATE).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.CALCITE).apply {
            add(HTCompoundProperty(HTMaterialKeys.CALCIUM to 1, *HTMaterialKeys.CARBONATE))
        }
        registry.getOrCreate(HTMaterialKeys.TUFF).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.OBSIDIAN).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.NETHERRACK).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.BASALT).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        registry.getOrCreate(HTMaterialKeys.END_STONE).apply {
            add(HTCompoundProperty(*HTMaterialKeys.SILICON_OXIDE))
        }
        // Vanilla - Woods
        registry.getOrCreate(HTMaterialKeys.WOOD).apply {
            add(HTMixtureProperty(HTMaterialKeys.CARBON, HTMaterialKeys.HYDROGEN, HTMaterialKeys.OXYGEN))
        }
    }

    override fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {
        // 1st Period
        registry[HTMaterialKeys.HYDROGEN] = ColorConvertible { HTColor.BLUE }
        registry[HTMaterialKeys.HELIUM] = ColorConvertible { HTColor.YELLOW }
        // 2nd Period
        registry[HTMaterialKeys.LITHIUM] = ColorConvertible { HTColor.GRAY }
        registry[HTMaterialKeys.BERYLLIUM] = ColorConvertible { HTColor.DARK_GREEN }
        registry[HTMaterialKeys.CARBON] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        registry[HTMaterialKeys.NITROGEN] = ColorConvertible { HTColor.AQUA }
        registry[HTMaterialKeys.OXYGEN] = ColorConvertible { HTColor.WHITE }
        registry[HTMaterialKeys.FLUORINE] = ColorConvertible { HTColor.GREEN }
        // 3rd Period
        registry[HTMaterialKeys.SODIUM] = ColorConvertible.ofColor(HTColor.DARK_BLUE to 1, HTColor.BLUE to 4)
        registry[HTMaterialKeys.MAGNESIUM] = ColorConvertible { HTColor.GRAY }
        registry[HTMaterialKeys.ALUMINUM] = ColorConvertible.ofColor(HTColor.BLUE to 1, HTColor.WHITE to 5)
        registry[HTMaterialKeys.SILICON] = ColorConvertible.ofColor(HTColor.BLACK to 2, HTColor.GRAY to 1, HTColor.BLUE to 1)
        registry[HTMaterialKeys.PHOSPHORUS] = ColorConvertible { HTColor.YELLOW }
        registry[HTMaterialKeys.SULFUR] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW)
        registry[HTMaterialKeys.CHLORINE] = ColorConvertible { HTColor.YELLOW }
        // 4th Period
        registry[HTMaterialKeys.POTASSIUM] = ColorConvertible.ofColor(HTColor.DARK_BLUE to 2, HTColor.BLUE to 3)
        registry[HTMaterialKeys.CALCIUM] = ColorConvertible { HTColor.GRAY }
        registry[HTMaterialKeys.TITANIUM] = ColorConvertible.ofColor(HTColor.GOLD to 1, HTColor.WHITE to 2)
        registry[HTMaterialKeys.CHROMIUM] = ColorConvertible { HTColor.GREEN }
        registry[HTMaterialKeys.MANGANESE] = ColorConvertible { HTColor.GRAY }
        registry[HTMaterialKeys.IRON] = ColorConvertible { HTColor.WHITE }
        registry[HTMaterialKeys.COBALT] = ColorConvertible { HTColor.BLUE }
        registry[HTMaterialKeys.NICKEL] = ColorConvertible.ofColor(HTColor.GOLD to 2, HTColor.GREEN to 1, HTColor.WHITE to 1)
        registry[HTMaterialKeys.COPPER] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.RED)
        registry[HTMaterialKeys.ZINC] = ColorConvertible.ofColor(HTColor.GREEN to 1, HTColor.WHITE to 2)
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
        // Vanilla - Fluids
        registry[HTMaterialKeys.WATER] = ColorConvertible { HTColor.BLUE }
        registry[HTMaterialKeys.LAVA] = ColorConvertible.ofColor(HTColor.DARK_RED, HTColor.GOLD)
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
    }

    override fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {
        // 1st Period
        registry[HTMaterialKeys.HYDROGEN] = FormulaConvertible { "H" }
        registry[HTMaterialKeys.HELIUM] = FormulaConvertible { "He" }
        // 2nd Period
        registry[HTMaterialKeys.LITHIUM] = FormulaConvertible { "Li" }
        registry[HTMaterialKeys.BERYLLIUM] = FormulaConvertible { "Be" }
        registry[HTMaterialKeys.CARBON] = FormulaConvertible { "C" }
        registry[HTMaterialKeys.NITROGEN] = FormulaConvertible { "N" }
        registry[HTMaterialKeys.OXYGEN] = FormulaConvertible { "O" }
        registry[HTMaterialKeys.FLUORINE] = FormulaConvertible { "F" }
        // 3rd Period
        registry[HTMaterialKeys.SODIUM] = FormulaConvertible { "Na" }
        registry[HTMaterialKeys.MAGNESIUM] = FormulaConvertible { "Mg" }
        registry[HTMaterialKeys.ALUMINUM] = FormulaConvertible { "Al" }
        registry[HTMaterialKeys.SILICON] = FormulaConvertible { "Si" }
        registry[HTMaterialKeys.PHOSPHORUS] = FormulaConvertible { "P" }
        registry[HTMaterialKeys.SULFUR] = FormulaConvertible { "S" }
        registry[HTMaterialKeys.CHLORINE] = FormulaConvertible { "Cl" }
        // 4th Period
        registry[HTMaterialKeys.POTASSIUM] = FormulaConvertible { "K" }
        registry[HTMaterialKeys.CALCIUM] = FormulaConvertible { "Ca" }
        registry[HTMaterialKeys.TITANIUM] = FormulaConvertible { "Ti" }
        registry[HTMaterialKeys.CHROMIUM] = FormulaConvertible { "Cr" }
        registry[HTMaterialKeys.MANGANESE] = FormulaConvertible { "Mn" }
        registry[HTMaterialKeys.IRON] = FormulaConvertible { "Fe" }
        registry[HTMaterialKeys.COBALT] = FormulaConvertible { "Co" }
        registry[HTMaterialKeys.NICKEL] = FormulaConvertible { "Ni" }
        registry[HTMaterialKeys.COPPER] = FormulaConvertible { "Cu" }
        registry[HTMaterialKeys.ZINC] = FormulaConvertible { "Zn" }
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
    }

    override fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {
        // 1st Period
        registry[HTMaterialKeys.HYDROGEN] = MolarMassConvertible { 1.0 }
        registry[HTMaterialKeys.HELIUM] = MolarMassConvertible { 4.0 }
        // 2nd Period
        registry[HTMaterialKeys.LITHIUM] = MolarMassConvertible { 6.9 }
        registry[HTMaterialKeys.BERYLLIUM] = MolarMassConvertible { 9.0 }
        registry[HTMaterialKeys.CARBON] = MolarMassConvertible { 12.0 }
        registry[HTMaterialKeys.NITROGEN] = MolarMassConvertible { 14.0 }
        registry[HTMaterialKeys.OXYGEN] = MolarMassConvertible { 16.0 }
        registry[HTMaterialKeys.FLUORINE] = MolarMassConvertible { 19.0 }
        // 3rd Period
        registry[HTMaterialKeys.SODIUM] = MolarMassConvertible { 23.0 }
        registry[HTMaterialKeys.MAGNESIUM] = MolarMassConvertible { 24.3 }
        registry[HTMaterialKeys.ALUMINUM] = MolarMassConvertible { 27.0 }
        registry[HTMaterialKeys.SILICON] = MolarMassConvertible { 28.1 }
        registry[HTMaterialKeys.PHOSPHORUS] = MolarMassConvertible { 31.0 }
        registry[HTMaterialKeys.SULFUR] = MolarMassConvertible { 32.1 }
        registry[HTMaterialKeys.CHLORINE] = MolarMassConvertible { 35.5 }
        // 4th Period
        registry[HTMaterialKeys.POTASSIUM] = MolarMassConvertible { 39.1 }
        registry[HTMaterialKeys.CALCIUM] = MolarMassConvertible { 40.1 }
        registry[HTMaterialKeys.TITANIUM] = MolarMassConvertible { 47.9 }
        registry[HTMaterialKeys.CHROMIUM] = MolarMassConvertible { 52.0 }
        registry[HTMaterialKeys.MANGANESE] = MolarMassConvertible { 54.9 }
        registry[HTMaterialKeys.IRON] = MolarMassConvertible { 55.8 }
        registry[HTMaterialKeys.COBALT] = MolarMassConvertible { 58.9 }
        registry[HTMaterialKeys.NICKEL] = MolarMassConvertible { 58.7 }
        registry[HTMaterialKeys.COPPER] = MolarMassConvertible { 63.5 }
        registry[HTMaterialKeys.ZINC] = MolarMassConvertible { 65.4 }
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
    }
}
