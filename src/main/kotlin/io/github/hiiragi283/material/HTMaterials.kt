package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.material.dictionary.MaterialDictionaryItem
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.item.Item
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

object HTMaterials : PreLaunchEntrypoint, ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    // PreLaunchEntrypoint
    override fun onPreLaunch() {
        // Collect Addons
        HTMaterialsCore.initAddons()
        // Create Shapes
        HTMaterialsAPIImpl.shapeRegistry = HTShapeRegistry(HTMaterialsCore.createShapeMap())
        // Create Materials
        HTMaterialsAPIImpl.materialRegistry = HTMaterialRegistry(HTMaterialsCore.createMaterialMap())
        HTMaterialsCore.verifyMaterial()
        // Init HTPart cache
        HTPart.initCache()
    }

    // ModInitializer
    override fun onInitialize() {
        // Initialize Game Objects
        HTMaterialsAPIImpl.itemGroup = FabricItemGroupBuilder.build(HTMaterialsAPI.id("material")) {
            HTMaterialsAPI.INSTANCE.iconItem().defaultStack
        }
        HTMaterialsAPIImpl.iconItem = Registry.register(
            Registry.ITEM,
            HTMaterialsAPI.id("icon"),
            Item(Item.Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).rarity(Rarity.EPIC)),
        )
        HTMaterialsAPIImpl.dictionaryItem = Registry.register(
            Registry.ITEM,
            HTMaterialsAPI.id("material_dictionary"),
            MaterialDictionaryItem,
        )
        HTMaterialsCore.initContents()
        HTMaterialsAPIImpl.initRegister()
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
