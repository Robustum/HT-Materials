package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.collection.DefaultedMap
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialsInit
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.material.content.HTSimpleFluidContent
import io.github.hiiragi283.material.content.HTStorageBlockContent
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags

object HTMaterialsInitFabric : HTMaterialsInit() {
    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap.Builder>) {
        super.modifyMaterialContent(registry)
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.HELIUM)
            .add(HTSimpleFluidContent())
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
            .add(HTSimpleFluidContent())
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.ALUMINUM)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
            .add(HTSimpleFluidContent())
        // 4th Period
        registry.getOrCreate(HTMaterialKeys.TITANIUM)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.NICKEL)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.COPPER)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.ZINC)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        // 5th Period
        registry.getOrCreate(HTMaterialKeys.SILVER)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.TIN)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.TUNGSTEN)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.IRIDIUM)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.PLATINUM)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.MERCURY)
            .add(HTSimpleFluidContent())
        registry.getOrCreate(HTMaterialKeys.LEAD)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        // 7th Period
        registry.getOrCreate(HTMaterialKeys.URANIUM)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.PLUTONIUM)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        // Common - Gems
        registry.getOrCreate(HTMaterialKeys.CINNABAR)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.COKE)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES))
        registry.getOrCreate(HTMaterialKeys.OLIVINE)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.PERIDOT)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.RUBY)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.SALT)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.SAPPHIRE)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 3))
        // Common - Metals
        registry.getOrCreate(HTMaterialKeys.BRASS)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.BRONZE)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.ELECTRUM)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.INVAR)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.STAINLESS_STEEL)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.STEEl)
            .add(HTStorageBlockContent(toolTag = FabricToolTags::PICKAXES, toolLevel = 2))
        // Common - Solids
        registry.getOrCreate(HTMaterialKeys.RUBBER)
            .add(HTStorageBlockContent())
    }
}
