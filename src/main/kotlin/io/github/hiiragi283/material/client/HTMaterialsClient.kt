package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.fluid.MaterialFluid
import io.github.hiiragi283.material.api.item.MaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.common.HTEventHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier

object HTMaterialsClient : ClientModInitializer {

    override fun onInitializeClient() {

        HTEventHandler.registerClient()

        HTPartManager.getDefaultItemTable().values()
            .map(ItemConvertible::asItem)
            .filterIsInstance<MaterialItem>()
            .forEach { materialItem: MaterialItem ->
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _, _ -> materialItem.material.getColor() },
                    materialItem
                )
            }

        HTMaterial.REGISTRY.forEach { material ->
            val flowing: MaterialFluid.Flowing = MaterialFluid.getFlowing(material) ?: return@forEach
            val still: MaterialFluid.Still = MaterialFluid.getStill(material) ?: return@forEach
            FluidRenderHandlerRegistry.INSTANCE.register(
                still, flowing, SimpleFluidRenderHandler(
                    Identifier("minecraft:block/white_concrete"),
                    Identifier("minecraft:block/white_concrete"),
                    material.getColor()
                )
            )
        }

    }

}