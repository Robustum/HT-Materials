package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider
import io.github.hiiragi283.api.util.resource.HTRuntimeDataPack
import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Rarity
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import net.minecraftforge.fml.event.server.FMLServerStartedEvent
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(value = HTMaterialsAPI.MOD_ID)
object HTMaterialsForge {
    init {
        HTMaterialsAPIForge.itemGroup = object : ItemGroup("${HTMaterialsAPI.MOD_ID}.material") {
            override fun createIcon(): ItemStack = HTMaterialsAPI.INSTANCE.iconItem().defaultStack
        }
        HTMaterialsAPIForge.iconItem = HTPlatformHelper.INSTANCE.registerItem(
            "icon",
            Item(Item.Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).rarity(Rarity.EPIC)),
        )

        HTMaterialsCoreForge.initAddons()
        HTMaterialsAPIForge.shapeRegistry = HTShapeRegistry(HTMaterialsCoreForge.createShapeMap())
        HTMaterialsAPIForge.materialRegistry = HTMaterialRegistry(HTMaterialsCoreForge.createMaterialMap())
        HTMaterialsCoreForge.verifyMaterial()
        HTMaterialsCoreForge.initContents()
        HTMaterialsCoreForge.initColorHandlers()

        MOD_BUS.run {
            addListener(::commonSetup)
            addListener(::blockColor)
            addListener(::itemColor)
            addListener(::aboutToServerStart)
            addListener(::serverStarted)
        }

        HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(EventHandler)
            MinecraftClient.getInstance().resourcePackManager.addPackFinder(HTResourcePackProvider.CLIENT)
            HTMaterialsAPI.log("Registered runtime resource pack!")
        }
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        HTMaterialsCoreForge.postInitialize(HTPlatformHelper.INSTANCE.getSide())
        // Register Tags from HTPartManager
        HTMaterialsAPI.INSTANCE.partManager().getAllEntries().forEach { entry ->
            val (materialKey: HTMaterialKey, shapeKey: HTShapeKey, item: Item) = entry
            // Shape tag
            HTRuntimeDataPack.getTagBuilder<Item>(shapeKey.getShapeId())
                .add(ForgeRegistries.ITEMS.getKey(item), HTMaterialsAPI.MOD_NAME)
            // Material tag
            HTRuntimeDataPack.getTagBuilder<Item>(materialKey.getMaterialId())
                .add(ForgeRegistries.ITEMS.getKey(item), HTMaterialsAPI.MOD_NAME)
            // Part tag
            HTRuntimeDataPack.getTagBuilder<Item>(HTPart(materialKey, shapeKey).getPartId())
                .add(ForgeRegistries.ITEMS.getKey(item), HTMaterialsAPI.MOD_NAME)
        }
        HTMaterialsAPI.log("Registered Tags for HTPartManager's Entries!")
    }

    private fun blockColor(event: ColorHandlerEvent.Block) {
        HTPlatformHelperForge.BLOCK_COLORS.forEach { (block: Block, blockColorProvider: BlockColorProvider) ->
            event.blockColors.registerColorProvider(blockColorProvider, block)
        }
        HTMaterialsAPI.log("Registered Block Colors!")
    }

    private fun itemColor(event: ColorHandlerEvent.Item) {
        HTPlatformHelperForge.ITEM_COLORS.forEach { (item: Item, itemColorProvider: ItemColorProvider) ->
            event.itemColors.register(itemColorProvider, item)
        }
    }

    private fun aboutToServerStart(event: FMLServerAboutToStartEvent) {
        HTMaterialsCoreForge.registerRecipes()
        HTRuntimeDataPack.writeTagData()
        event.server.dataPackManager.addPackFinder(HTResourcePackProvider.SERVER)
        HTMaterialsAPI.log("Registered runtime data pack!")
    }

    private fun serverStarted(event: FMLServerStartedEvent) {
        HTMaterialsCoreForge.serverStarted(event)
    }

    object EventHandler {
        @Suppress("unused")
        @SubscribeEvent
        fun onItemTooltip(event: ItemTooltipEvent) {
            val stack: ItemStack = event.itemStack
            if (stack.isEmpty) return
            val tooltip: MutableList<Text> = event.toolTip

            HTMaterialsAPI.INSTANCE.partManager().getPart(stack.item)?.let {
                it.getMaterial().appendTooltip(it.shapeKey, stack, tooltip)
            }

            stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
                .map { handler -> (0..handler.tanks).map { handler.getFluidInTank(it) } }.orElse(listOf())
                .map(FluidStack::getFluid)
                .mapNotNull { HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(it) }
                .forEach { it.getMaterial().appendTooltip(null, stack, tooltip) }
        }
    }
}