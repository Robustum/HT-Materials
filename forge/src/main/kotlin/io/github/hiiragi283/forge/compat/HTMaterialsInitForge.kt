package io.github.hiiragi283.forge.compat

import io.github.hiiragi283.api.HTAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.HTMaterialsInit
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.forge.content.HTForgeFluidContent
import io.github.hiiragi283.forge.content.HTStorageBlockContent
import net.minecraftforge.common.ToolType

@HTAddon
class HTMaterialsInitForge : HTMaterialsInit() {

    override fun modifyShapeTagPath(registry: MutableMap<HTShapeKey, String>) {
        // Block
        registry[HTShapeKeys.BLOCK] = "storage_blocks/%s"
        registry[HTShapeKeys.ORE] = "ores/%s"
        // Items
        registry[HTShapeKeys.DUST] = "dusts/%s"
        registry[HTShapeKeys.GEAR] = "gears/%s"
        registry[HTShapeKeys.GEM] = "gems/%s"
        registry[HTShapeKeys.INGOT] = "ingots/%s"
        registry[HTShapeKeys.NUGGET] = "nuggets/%s"
        registry[HTShapeKeys.PLATE] = "plates/%s"
        registry[HTShapeKeys.ROD] = "rods/%s"
    }

    override fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {
        super.modifyMaterialContent(registry)
        // 1st Period
        registry.getOrCreate(HTMaterialKeys.HYDROGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.HELIUM)
            .add(HTForgeFluidContent())
        // 2nd Period
        registry.getOrCreate(HTMaterialKeys.NITROGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.OXYGEN)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.FLUORINE)
            .add(HTForgeFluidContent())
        // 3rd Period
        registry.getOrCreate(HTMaterialKeys.ALUMINUM)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.CHLORINE)
            .add(HTForgeFluidContent())
        // 4th Period
        registry.getOrCreate(HTMaterialKeys.TITANIUM)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.NICKEL)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.COPPER)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.ZINC)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        // 5th Period
        registry.getOrCreate(HTMaterialKeys.SILVER)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.TIN)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        // 6th Period
        registry.getOrCreate(HTMaterialKeys.TUNGSTEN)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.IRIDIUM)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.PLATINUM)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.MERCURY)
            .add(HTForgeFluidContent())
        registry.getOrCreate(HTMaterialKeys.LEAD)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        // 7th Period
        registry.getOrCreate(HTMaterialKeys.URANIUM)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.PLUTONIUM)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        // Common - Gems
        registry.getOrCreate(HTMaterialKeys.CINNABAR)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.COKE)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE))
        registry.getOrCreate(HTMaterialKeys.OLIVINE)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.PERIDOT)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.RUBY)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 3))
        registry.getOrCreate(HTMaterialKeys.SALT)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.SAPPHIRE)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 3))
        // Common - Metals
        registry.getOrCreate(HTMaterialKeys.BRASS)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.BRONZE)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 1))
        registry.getOrCreate(HTMaterialKeys.ELECTRUM)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.INVAR)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.STAINLESS_STEEL)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        registry.getOrCreate(HTMaterialKeys.STEEl)
            .add(HTStorageBlockContent(toolType = ToolType.PICKAXE, toolLevel = 2))
        // Common - Solids
        registry.getOrCreate(HTMaterialKeys.RUBBER)
            .add(HTStorageBlockContent())
    }

}