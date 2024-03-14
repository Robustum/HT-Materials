package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.property.HTPropertyType
import io.github.hiiragi283.material.dictionary.MaterialDictionaryItem
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

object HTMaterials : ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    init {
        HTMaterialsAPI
    }

    // ModInitializer
    override fun onInitialize() {
        // Print sorted addons
        HTMaterialsAPI.LOGGER.info("HTMaterialsAddon collected!")
        HTMaterialsAPI.LOGGER.info("=== List ===")
        HTMaterialsAPI.INSTANCE.forEachAddon {
            HTMaterialsAPI.LOGGER.info("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.LOGGER.info("============")
        // Initialize Registry
        HTMaterialsAPI.INSTANCE.shapeRegistry
        HTMaterialsAPI.INSTANCE.materialRegistry
        HTMaterialsAPI.INSTANCE.fluidManager
        HTMaterialsAPI.INSTANCE.partManager
        HTPropertyType.REGISTRY
        // Initialize Game Objects
        HTMaterialsAPIImpl.iconItem1 = Registry.register(
            Registry.ITEM,
            HTMaterialsAPI.id("icon"),
            Item(Item.Settings().group(ItemGroup.MISC).rarity(Rarity.EPIC)),
        )
        HTMaterialsAPIImpl.dictionaryItem1 = Registry.register(
            Registry.ITEM,
            HTMaterialsAPI.id("material_dictionary"),
            MaterialDictionaryItem,
        )
    }

    // ClientModInitializer
    override fun onInitializeClient() {
        HTMaterialsCore.postInitialize(EnvType.CLIENT)
        HTMaterialsAPI.LOGGER.info("Client post-initialize completed!")
    }

    // DedicatedServerModInitializer
    override fun onInitializeServer() {
        HTMaterialsCore.postInitialize(EnvType.SERVER)
        HTMaterialsAPI.LOGGER.info("Server post-initialize completed!")
    }
}
