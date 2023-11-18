package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.block.HTMaterialBlock
import io.github.hiiragi283.material.api.entorypoint.HTMaterialPlugin
import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.prefix
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
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.model.ModelJsonBuilder

@Environment(EnvType.CLIENT)
object HTMaterialsClient : ClientModInitializer {

    override fun onInitializeClient() {

        //Client Initialization for registering texture for Material Block/Item
        HTMaterialPlugin.Client.clientInitialize()

        //Register Block Color Provider
        registerBlockColorProvider()

        //Register Item Color Provider
        registerItemColorProvider()

        //Register Render Handler for Material Fluid
        registerFluidRenderHandler()

        //Register BlockStates
        registerBlockState()

        //Register Models
        registerModels()

        //Register Client Events
        registerEvents()

    }

    private fun registerBlockColorProvider() {
        //Material Blocks
        HTPartManager.getDefaultItemTable().values()
            .filterIsInstance<HTMaterialBlock>()
            .forEach { block: HTMaterialBlock ->
                ColorProviderRegistry.BLOCK.register(
                    BlockColorProvider { _, _, _, tintIndex: Int -> if (tintIndex == 0) block.materialHT.getColor() else -1 },
                    block
                )
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _, tintIndex: Int -> if (tintIndex == 0) block.materialHT.getColor() else -1 },
                    block
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

    private fun registerBlockState() {
        //Material Block
        HTPartManager.getDefaultItemTable().values()
            .filterIsInstance<HTMaterialBlock>()
            .forEach { block: HTMaterialBlock ->
                val (material: HTMaterial, shape: HTShape) = block
                val identifier: Identifier = shape.getIdentifier(HTMaterialsCommon.MOD_ID, material)
                val blockModelId: Identifier = identifier.prefix("block/")
                val itemModelId: Identifier = identifier.prefix("item/")
                //BlockState
                HTMaterialsCommon.RESOURCE_PACK.addBlockState(
                    identifier,
                    BlockStateModelGenerator.createSingletonBlockState(block, blockModelId)
                )
                //Block Model
                HTMaterialsCommon.RESOURCE_PACK.addModel(blockModelId, shape.model)
                //Item Model
                HTMaterialsCommon.RESOURCE_PACK.addModel(itemModelId, ModelJsonBuilder.create(blockModelId))
            }
        //Material Fluid Block
        HTMaterialFluid.getBlocks().forEach { block: HTMaterialFluid.Block ->
            val modelId: Identifier = block.identifier.prefix("block/fluid/")
            //BlockState
            HTMaterialsCommon.RESOURCE_PACK.addBlockState(
                block.identifier,
                BlockStateModelGenerator.createSingletonBlockState(block, modelId)
            )
            //Model (for particle)
            HTMaterialsCommon.RESOURCE_PACK.addModel(modelId, block.shapeHT.model)
        }
    }

    private fun registerModels() {
        //Material Item
        HTPartManager.getDefaultItemTable().values()
            .filterIsInstance<HTMaterialItem>()
            .forEach { item: HTMaterialItem ->
                val (material: HTMaterial, shape: HTShape) = item
                HTMaterialsCommon.RESOURCE_PACK.addModel(
                    shape.getIdentifier(HTMaterialsCommon.MOD_ID, material).prefix("item/"),
                    shape.model
                )
            }
        //Material Fluid Bucket
        HTMaterialFluid.getBuckets().forEach { bucket: HTMaterialFluid.Bucket ->
            HTMaterialsCommon.RESOURCE_PACK.addModel(bucket.identifier.prefix("item/"), bucket.shapeHT.model)
        }
    }

    private fun registerEvents() {

        ItemTooltipCallback.EVENT.register { stack: ItemStack, context: TooltipContext, lines: MutableList<Text> ->
            HTPartManager.ITEM_TO_PART[stack.item]?.appendTooltip(stack, context, lines)
        }

    }

}