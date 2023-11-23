package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.item.HTMaterialBlockItem
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
object HTMaterialsClient : ClientModInitializer {

    override fun onInitializeClient() {

        //Register Default Model Consumer
        HTMaterialModelManager

        //Register Block Color Provider
        registerBlockColorProvider()

        //Register Item Color Provider
        registerItemColorProvider()

        //Register Render Handler for Material Fluid
        registerFluidRenderHandler()

        //Register BlockStates and Models
        HTMaterialModelManager.register()

        //Register Client Events
        registerEvents()

    }

    private fun registerBlockColorProvider() {
        //Material Blocks
        HTPartManager.getDefaultItemTable().values()
            .filterIsInstance<HTMaterialBlockItem>()
            .forEach { item: HTMaterialBlockItem ->
                ColorProviderRegistry.BLOCK.register(
                    BlockColorProvider { _, _, _, tintIndex: Int -> if (tintIndex == 0) item.materialHT.getColor() else -1 },
                    item.block
                )
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _, tintIndex: Int -> if (tintIndex == 0) item.materialHT.getColor() else -1 },
                    item
                )
            }
    }

    private fun registerItemColorProvider() {
        //Material Items
        HTPartManager.getDefaultItemTable().values()
            .filterIsInstance<HTMaterialItem>()
            .forEach { item: HTMaterialItem ->
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _, tintIndex: Int -> if (tintIndex == 0) item.materialHT.getColor() else -1 },
                    item
                )
            }
        //Material Fluid Bucket
        HTMaterialFluid.getBuckets().forEach { bucket: HTMaterialFluid.Bucket ->
            ColorProviderRegistry.ITEM.register(
                ItemColorProvider { _, tintIndex: Int -> if (tintIndex == 1) bucket.materialHT.getColor() else -1 },
                bucket
            )
        }
    }

    private fun registerFluidRenderHandler() {
        HTMaterial.REGISTRY.forEach { material: HTMaterial ->
            val flowing: HTMaterialFluid.Flowing = HTMaterialFluid.getFlowing(material) ?: return@forEach
            val still: HTMaterialFluid.Still = HTMaterialFluid.getStill(material) ?: return@forEach
            FluidRenderHandlerRegistry.INSTANCE.register(
                still, flowing, SimpleFluidRenderHandler(
                    Identifier("minecraft:block/white_concrete"),
                    Identifier("minecraft:block/white_concrete"),
                    material.getColor()
                )
            )
        }
    }

    private fun registerEvents() {

        ItemTooltipCallback.EVENT.register { stack: ItemStack, context: TooltipContext, lines: MutableList<Text> ->
            HTPartManager.getPart(stack.item)?.appendTooltip(stack, context, lines)
        }

    }

}