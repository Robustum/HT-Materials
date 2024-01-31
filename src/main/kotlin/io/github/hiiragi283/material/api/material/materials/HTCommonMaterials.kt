package io.github.hiiragi283.material.api.material.materials

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.material.api.material.content.HTSimpleItemContent
import io.github.hiiragi283.material.api.material.content.HTStorageBlockContent
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.material.property.component.HTCompoundProperty
import io.github.hiiragi283.material.api.material.property.component.HTHydrateProperty
import io.github.hiiragi283.material.api.material.property.component.HTMixtureProperty
import io.github.hiiragi283.material.api.material.property.component.HTPolymerProperty
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.api.util.HTColor
import io.github.hiiragi283.material.api.util.addAll
import io.github.hiiragi283.material.api.util.collection.DefaultedMap
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags

object HTCommonMaterials : HTMaterialsAddon {
    //    Fluids    //

    //    Gems    //

    @JvmField
    val CINNABAR = HTMaterialKey("cinnabar")

    @JvmField
    val COKE = HTMaterialKey("coke")

    @JvmField
    val OLIVINE = HTMaterialKey("olivine")

    @JvmField
    val PERIDOT = HTMaterialKey("peridot")

    @JvmField
    val RUBY = HTMaterialKey("ruby")

    @JvmField
    val SALT = HTMaterialKey("salt")

    @JvmField
    val SAPPHIRE = HTMaterialKey("sapphire")

    //    Metal    //

    @JvmField
    val BRASS = HTMaterialKey("brass")

    @JvmField
    val BRONZE = HTMaterialKey("bronze")

    @JvmField
    val ELECTRUM = HTMaterialKey("electrum")

    @JvmField
    val INVAR = HTMaterialKey("invar")

    @JvmField
    val STAINLESS_STEEL = HTMaterialKey("stainless_steel")

    @JvmField
    val STEEl = HTMaterialKey("steel")

    //    Solids    //

    @JvmField
    val ASHES = HTMaterialKey("ashes")

    @JvmField
    val BAUXITE = HTMaterialKey("bauxite")

    @JvmField
    val RUBBER = HTMaterialKey("rubber")

    //    Stones    //

    @JvmField
    val MARBLE = HTMaterialKey("marble")

    //    Woods    //

    //    Register    //

    override val modId: String = HTMaterials.MOD_ID

    override val priority: Int = -90

    override fun registerMaterialKey(registry: ImmutableSet.Builder<HTMaterialKey>) {
        // Fluids
        // Gems
        registry.addAll(
            CINNABAR,
            COKE,
            OLIVINE,
            PERIDOT,
            RUBY,
            SALT,
            SAPPHIRE,
        )
        // Metals
        registry.addAll(
            BRASS,
            BRONZE,
            ELECTRUM,
            INVAR,
            STAINLESS_STEEL,
            STEEl,
        )
        // Solids
        registry.addAll(
            ASHES,
            BAUXITE,
            RUBBER,
        )
        // Stones
        registry.addAll(
            MARBLE,
        )
        // Woods
    }

    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {
        // Fluids
        // Gems
        registry.getOrCreate(CINNABAR).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEM))
        }
        registry.getOrCreate(COKE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEM))
        }
        registry.getOrCreate(OLIVINE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEM))
        }
        registry.getOrCreate(PERIDOT).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEM))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(RUBY).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEM))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(SALT).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::SHOVELS,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEM))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(SAPPHIRE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEM))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        // Metals
        registry.getOrCreate(BRASS).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEAR))
            add(HTSimpleItemContent(HTShapes.INGOT))
            add(HTSimpleItemContent(HTShapes.NUGGET))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(BRONZE).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 1,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEAR))
            add(HTSimpleItemContent(HTShapes.INGOT))
            add(HTSimpleItemContent(HTShapes.NUGGET))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(ELECTRUM).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEAR))
            add(HTSimpleItemContent(HTShapes.INGOT))
            add(HTSimpleItemContent(HTShapes.NUGGET))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(INVAR).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEAR))
            add(HTSimpleItemContent(HTShapes.INGOT))
            add(HTSimpleItemContent(HTShapes.NUGGET))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(STAINLESS_STEEL).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEAR))
            add(HTSimpleItemContent(HTShapes.INGOT))
            add(HTSimpleItemContent(HTShapes.NUGGET))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        registry.getOrCreate(STEEl).apply {
            add(
                HTStorageBlockContent(
                    toolTag = FabricToolTags::PICKAXES,
                    toolLevel = 2,
                ),
            )
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.GEAR))
            add(HTSimpleItemContent(HTShapes.INGOT))
            add(HTSimpleItemContent(HTShapes.NUGGET))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        // Solids
        registry.getOrCreate(ASHES).apply {
            add(HTSimpleItemContent(HTShapes.DUST))
        }
        registry.getOrCreate(BAUXITE).apply {
            add(HTSimpleItemContent(HTShapes.DUST))
        }
        registry.getOrCreate(RUBBER).apply {
            add(HTStorageBlockContent())
            add(HTSimpleItemContent(HTShapes.DUST))
            add(HTSimpleItemContent(HTShapes.INGOT))
            add(HTSimpleItemContent(HTShapes.PLATE))
            add(HTSimpleItemContent(HTShapes.ROD))
        }
        // Stones
        registry.getOrCreate(MARBLE).apply {
            add(HTSimpleItemContent(HTShapes.DUST))
        }
        // Woods
    }

    override fun modifyMaterialProperty(registry: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {
        // Fluids
        // Gems
        registry.getOrCreate(CINNABAR).apply {
            add(HTCompoundProperty(HTElementMaterials.MERCURY to 1, HTElementMaterials.SULFUR to 1))
        }
        registry.getOrCreate(COKE).apply {
            add(HTCompoundProperty(HTElementMaterials.CARBON to 1))
        }
        registry.getOrCreate(OLIVINE)
        registry.getOrCreate(PERIDOT)
        registry.getOrCreate(RUBY).apply {
            add(HTCompoundProperty(*HTAtomicGroups.ALUMINUM_OXIDE))
        }
        registry.getOrCreate(SALT).apply {
            add(HTCompoundProperty(HTElementMaterials.SODIUM to 1, HTElementMaterials.CHLORINE to 1))
        }
        registry.getOrCreate(SAPPHIRE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.ALUMINUM_OXIDE))
        }
        // Metals
        registry.getOrCreate(BRASS).apply {
            add(HTCompoundProperty(HTElementMaterials.COPPER to 3, HTElementMaterials.ZINC to 1))
        }
        registry.getOrCreate(BRONZE).apply {
            add(HTCompoundProperty(HTElementMaterials.COPPER to 3, HTElementMaterials.TIN to 1))
        }
        registry.getOrCreate(ELECTRUM).apply {
            add(HTCompoundProperty(HTElementMaterials.SILVER to 1, HTElementMaterials.GOLD to 1))
        }
        registry.getOrCreate(INVAR).apply {
            add(HTCompoundProperty(HTElementMaterials.IRON to 2, HTElementMaterials.NICKEL to 1))
        }
        registry.getOrCreate(STAINLESS_STEEL).apply {
            add(
                HTCompoundProperty(
                    HTElementMaterials.IRON to 6,
                    HTElementMaterials.CHROMIUM to 1,
                    HTElementMaterials.MANGANESE to 1,
                    HTElementMaterials.NICKEL to 1,
                ),
            )
        }
        registry.getOrCreate(STEEl).apply {
            add(HTMixtureProperty(HTElementMaterials.IRON, HTElementMaterials.CARBON))
        }
        // Solids
        registry.getOrCreate(ASHES)
        registry.getOrCreate(BAUXITE).add(HTHydrateProperty(RUBY, 2))
        registry.getOrCreate(RUBBER).add(HTPolymerProperty("CC(=C)C=C"))
        // Stones
        registry.getOrCreate(MARBLE).apply {
            add(HTCompoundProperty(HTElementMaterials.CALCIUM to 1, *HTAtomicGroups.CARBONATE))
        }
        // Woods
    }

    override fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {
        // Fluids
        // Gems
        registry[CINNABAR] = ColorConvertible { HTColor.RED }
        registry[COKE] = ColorConvertible { HTColor.DARK_GRAY }
        registry[OLIVINE] = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.GREEN)
        registry[PERIDOT] = ColorConvertible.ofColor(HTColor.GREEN, HTColor.WHITE)
        registry[RUBY] = ColorConvertible { HTColor.RED }
        registry[SALT] = ColorConvertible { HTColor.WHITE }
        registry[SAPPHIRE] = ColorConvertible { HTColor.BLUE }
        // Metals
        registry[BRASS] = ColorConvertible { HTColor.GOLD }
        registry[BRONZE]
        registry[ELECTRUM] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
        registry[INVAR] = ColorConvertible.ofColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
        registry[STAINLESS_STEEL] = ColorConvertible.ofColor(HTColor.GRAY, HTColor.WHITE)
        registry[STEEl] = ColorConvertible { HTColor.DARK_GRAY }
        // Solids
        registry[ASHES] = ColorConvertible { HTColor.DARK_GRAY }
        registry[BAUXITE] = ColorConvertible.ofColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1)
        registry[RUBBER] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        // Stones
        registry[MARBLE] = ColorConvertible { HTColor.WHITE }
        // Woods
    }

    override fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {
        // Fluids
        // Gems
        registry[CINNABAR]
        registry[COKE]
        registry[OLIVINE]
        registry[PERIDOT]
        registry[RUBY]
        registry[SALT]
        registry[SAPPHIRE]
        // Metals
        registry[BRASS]
        registry[BRONZE]
        registry[ELECTRUM]
        registry[INVAR]
        registry[STAINLESS_STEEL]
        registry[STEEl] = FormulaConvertible { "Fe, C" }
        // Solids
        registry[ASHES]
        registry[BAUXITE]
        registry[RUBBER]
        // Stones
        registry[MARBLE]
        // Woods
    }

    override fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {
        // Fluids
        // Gems
        // Metals
        // Solids
        // Stones
        // Woods
    }

    override fun modifyMaterialType(registry: MutableMap<HTMaterialKey, HTMaterialType>) {
        // Fluids
        // Gems
        registry[CINNABAR] = HTMaterialType.Gem.EMERALD
        registry[COKE] = HTMaterialType.Gem.COAL
        registry[OLIVINE] = HTMaterialType.Gem.EMERALD
        registry[PERIDOT] = HTMaterialType.Gem.RUBY
        registry[RUBY] = HTMaterialType.Gem.RUBY
        registry[SALT] = HTMaterialType.Gem.CUBIC
        registry[SAPPHIRE] = HTMaterialType.Gem.RUBY
        // Metals
        registry[BRASS] = HTMaterialType.Metal
        registry[BRONZE] = HTMaterialType.Metal
        registry[ELECTRUM] = HTMaterialType.Metal
        registry[INVAR] = HTMaterialType.Metal
        registry[STAINLESS_STEEL] = HTMaterialType.Metal
        registry[STEEl] = HTMaterialType.Metal
        // Solids
        // Stones
        registry[MARBLE] = HTMaterialType.Stone
        // Woods
    }
}
