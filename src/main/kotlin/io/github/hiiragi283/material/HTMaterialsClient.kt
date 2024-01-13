package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.getPart
import io.github.hiiragi283.material.api.util.HTCustomColoredBlock
import io.github.hiiragi283.material.api.util.HTCustomColoredItem
import io.github.hiiragi283.material.api.util.HTFluidRenderHandler
import io.github.hiiragi283.material.util.getTransaction
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("unused", "UnstableApiUsage")
@Environment(EnvType.CLIENT)
object HTMaterialsClient : ClientModInitializer {
    private val LOGGER: Logger = LogManager.getLogger("${HTMaterials.MOD_NAME}/Client")

    override fun onInitializeClient() {
        // Register Render Handler for Material Fluid
        registerFluidRenderHandler()
        LOGGER.info("Material Fluid Renderer Registered!")

        // Register Block Color Provider
        registerBlockColorProvider()
        LOGGER.info("Block Color Provider Registered!")

        // Register Item Color Provider
        registerItemColorProvider()
        LOGGER.info("Item Color Provider Registered!")

        // Register Client Events
        registerEvents()
        LOGGER.info("Client Events Registered!")
    }

    private fun registerFluidRenderHandler() {
        HTMaterial.REGISTRY.forEach { (key: HTMaterialKey, material: HTMaterial) ->
            HTMaterialFluid.getFluid(key)?.let { fluid: HTMaterialFluid ->
                FluidRenderHandlerRegistry.INSTANCE.register(fluid.still, HTFluidRenderHandler(material))
                FluidRenderHandlerRegistry.INSTANCE.register(fluid.flowing, HTFluidRenderHandler(material))
            }
        }
    }

    private fun registerBlockColorProvider() {
        Registry.BLOCK.forEach { block ->
            if (block is HTCustomColoredBlock) {
                ColorProviderRegistry.BLOCK.register(block.getColorProvider(), block)
            }
        }
    }

    private fun registerItemColorProvider() {
        Registry.ITEM.forEach { item ->
            if (item is HTCustomColoredItem) {
                ColorProviderRegistry.ITEM.register(item.getColorProvider(), item)
            }
        }
    }

    private fun registerEvents() {
        ItemTooltipCallback.EVENT.register { stack: ItemStack, _, lines: MutableList<Text> ->

            stack.item.getPart()?.let {
                HTMaterial.appendTooltip(it.getMaterial(), it.shapeKey, stack, lines)
            }

            FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
                ?.iterable(getTransaction())
                ?.map(StorageView<FluidVariant>::getResource)
                ?.map(FluidVariant::getFluid)
                ?.mapNotNull(HTFluidManager::getMaterialKey)
                ?.forEach { HTMaterial.appendTooltip(it.getMaterial(), null, stack, lines) }
        }
    }
}
