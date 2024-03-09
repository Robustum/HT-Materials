package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.material.dictionary.MaterialDictionaryItem
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

object HTMaterials : PreLaunchEntrypoint, ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    // PreLaunchEntrypoint
    override fun onPreLaunch() {
        // Collect Addons
        HTMaterialsCore.initAddons()
        // Create Shapes
        HTMaterialsCore.initShapeRegistry()
        // Create Materials
        HTMaterialsCore.initMaterialRegistry()
        HTMaterialsCore.verifyMaterial()
    }

    // ModInitializer
    override fun onInitialize() {
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
        HTMaterialsAPI.log("Client post-initialize completed!")
    }

    // DedicatedServerModInitializer
    override fun onInitializeServer() {
        HTMaterialsCore.postInitialize(EnvType.SERVER)
        HTMaterialsAPI.log("Server post-initialize completed!")
    }
}
