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

    override val modId: String = HTMaterialsCommon.MOD_ID

    override val priority: Int = -90

    override fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialKey>) {
        //Fluids
        //Gems
        registry.addAll(
            CINNABAR,
            COKE,
            OLIVINE,
            PERIDOT,
            RUBY,
            SALT,
            SAPPHIRE
        )
        //Metals
        registry.addAll(
            BRASS,
            BRONZE,
            ELECTRUM,
            INVAR,
            STAINLESS_STEEL,
            STEEl
        )
        //Solids
        registry.addAll(
            ASHES,
            BAUXITE,
            RUBBER
        )
        //Stones
        registry.addAll(
            MARBLE
        )
        //Woods
    }

    override fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {
        //Fluids
        //Gems
        registry.getOrCreate(CINNABAR).apply {
            add(HTCompoundProperty(HTElementMaterials.MERCURY to 1, HTElementMaterials.SULFUR to 1))
            add(HTGemProperty.EMERALD)
        }
        registry.getOrCreate(COKE).apply {
            add(HTCompoundProperty(HTElementMaterials.CARBON to 1))
            add(HTGemProperty.COAL)
        }
        registry.getOrCreate(OLIVINE).add(HTGemProperty.EMERALD)
        registry.getOrCreate(PERIDOT).add(HTGemProperty.RUBY)
        registry.getOrCreate(RUBY).apply {
            add(HTCompoundProperty(*HTAtomicGroups.ALUMINUM_OXIDE))
            add(HTGemProperty.RUBY)
        }
        registry.getOrCreate(SALT).apply {
            add(HTCompoundProperty(HTElementMaterials.SODIUM to 1, HTElementMaterials.CHLORINE to 1))
            add(HTGemProperty.CUBIC)
        }
        registry.getOrCreate(SAPPHIRE).apply {
            add(HTCompoundProperty(*HTAtomicGroups.ALUMINUM_OXIDE))
            add(HTGemProperty.RUBY)
        }
        //Metals
        registry.getOrCreate(BRASS).apply {
            add(HTCompoundProperty(HTElementMaterials.COPPER to 3, HTElementMaterials.ZINC to 1))
            add(HTMetalProperty)
        }
        registry.getOrCreate(BRONZE).apply {
            add(HTCompoundProperty(HTElementMaterials.COPPER to 3, HTElementMaterials.TIN to 1))
            add(HTMetalProperty)
        }
        registry.getOrCreate(ELECTRUM).apply {
            add(HTCompoundProperty(HTElementMaterials.SILVER to 1, HTElementMaterials.GOLD to 1))
            add(HTMetalProperty)
        }
        registry.getOrCreate(INVAR).apply {
            add(HTCompoundProperty(HTElementMaterials.IRON to 2, HTElementMaterials.NICKEL to 1))
            add(HTMetalProperty)
        }
        registry.getOrCreate(STAINLESS_STEEL).apply {
            add(
                HTCompoundProperty(
                    HTElementMaterials.IRON to 6,
                    HTElementMaterials.CHROMIUM to 1,
                    HTElementMaterials.MANGANESE to 1,
                    HTElementMaterials.NICKEL to 1
                )
            )
            add(HTMetalProperty)
        }
        registry.getOrCreate(STEEl).apply {
            add(HTMixtureProperty(HTElementMaterials.IRON, HTElementMaterials.CARBON))
            add(HTMetalProperty)
        }
        //Solids
        registry.getOrCreate(ASHES)
        registry.getOrCreate(BAUXITE).add(HTHydrateProperty(RUBY, 2))
        registry.getOrCreate(RUBBER).add(HTPolymerProperty("CC(=C)C=C"))
        //Stones
        registry.getOrCreate(MARBLE).apply {
            add(HTCompoundProperty(HTElementMaterials.CALCIUM to 1, *HTAtomicGroups.CARBONATE))
            add(HTStoneProperty)
        }
        //Woods
    }

    override fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {
        //Fluids
        //Gems
        registry.getOrCreate(CINNABAR).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEM)
        }
        registry.getOrCreate(COKE).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEM)
        }
        registry.getOrCreate(OLIVINE).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEM)
        }
        registry.getOrCreate(PERIDOT).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEM)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(RUBY).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEM)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(SALT).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEM)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }

        registry.getOrCreate(SAPPHIRE).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEM)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        //Metals
        registry.getOrCreate(BRASS).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_INGOT)
            add(HTMaterialFlags.GENERATE_NUGGET)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(BRONZE).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_INGOT)
            add(HTMaterialFlags.GENERATE_NUGGET)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(ELECTRUM).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_INGOT)
            add(HTMaterialFlags.GENERATE_NUGGET)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(INVAR).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_INGOT)
            add(HTMaterialFlags.GENERATE_NUGGET)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(STAINLESS_STEEL).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_INGOT)
            add(HTMaterialFlags.GENERATE_NUGGET)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        registry.getOrCreate(STEEl).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_GEAR)
            add(HTMaterialFlags.GENERATE_INGOT)
            add(HTMaterialFlags.GENERATE_NUGGET)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        //Solids
        registry.getOrCreate(ASHES).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(BAUXITE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        registry.getOrCreate(RUBBER).apply {
            add(HTMaterialFlags.GENERATE_BLOCk)
            add(HTMaterialFlags.GENERATE_DUST)
            add(HTMaterialFlags.GENERATE_INGOT)
            add(HTMaterialFlags.GENERATE_PLATE)
            add(HTMaterialFlags.GENERATE_ROD)
        }
        //Stones
        registry.getOrCreate(MARBLE).apply {
            add(HTMaterialFlags.GENERATE_DUST)
        }
        //Woods
    }

    override fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {
        //Fluids
        //Gems
        registry[CINNABAR] = ColorConvertible { HTColor.RED }
        registry[COKE] = ColorConvertible { HTColor.DARK_GRAY }
        registry[OLIVINE] = ColorConvertible.ofColor(HTColor.DARK_GREEN, HTColor.GREEN)
        registry[PERIDOT] = ColorConvertible.ofColor(HTColor.GREEN, HTColor.WHITE)
        registry[RUBY] = ColorConvertible { HTColor.RED }
        registry[SALT] = ColorConvertible { HTColor.WHITE }
        registry[SAPPHIRE] = ColorConvertible { HTColor.BLUE }
        //Metals
        registry[BRASS] = ColorConvertible { HTColor.GOLD }
        registry[BRONZE]
        registry[ELECTRUM] = ColorConvertible.ofColor(HTColor.GOLD, HTColor.YELLOW, HTColor.WHITE)
        registry[INVAR] = ColorConvertible.ofColor(HTColor.GREEN to 1, HTColor.GRAY to 3, HTColor.WHITE to 4)
        registry[STAINLESS_STEEL] = ColorConvertible.ofColor(HTColor.GRAY, HTColor.WHITE)
        registry[STEEl] = ColorConvertible { HTColor.DARK_GRAY }
        //Solids
        registry[ASHES] = ColorConvertible { HTColor.DARK_GRAY }
        registry[BAUXITE] = ColorConvertible.ofColor(HTColor.BLACK to 1, HTColor.DARK_RED to 2, HTColor.GOLD to 1)
        registry[RUBBER] = ColorConvertible.ofColor(HTColor.BLACK, HTColor.DARK_GRAY)
        //Stones
        registry[MARBLE] = ColorConvertible { HTColor.WHITE }
        //Woods
    }

    override fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {
        //Fluids
        //Gems
        registry[CINNABAR]
        registry[COKE]
        registry[OLIVINE]
        registry[PERIDOT]
        registry[RUBY]
        registry[SALT]
        registry[SAPPHIRE]
        //Metals
        registry[BRASS]
        registry[BRONZE]
        registry[ELECTRUM]
        registry[INVAR]
        registry[STAINLESS_STEEL]
        registry[STEEl] = FormulaConvertible { "Fe, C" }
        //Solids
        registry[ASHES]
        registry[BAUXITE]
        registry[RUBBER]
        //Stones
        registry[MARBLE]
        //Woods
    }

    override fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {
        //Fluids
        //Gems
        //Metals
        //Solids
        //Stones
        //Woods
    }

}