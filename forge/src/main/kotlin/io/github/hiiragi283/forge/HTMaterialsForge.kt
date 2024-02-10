package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider
import net.minecraft.client.MinecraftClient
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

        MOD_BUS.run {
            addListener(::commonSetup)
            addListener(::blockColor)
            addListener(::itemColor)
        }

        MinecraftForge.EVENT_BUS.register(::aboutToServerStart)

        HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(this)
            MinecraftClient.getInstance().resourcePackManager.addPackFinder(HTResourcePackProvider.CLIENT)
            HTMaterialsAPI.log("Registered runtime resource pack!")
        }
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        HTMaterialsCoreForge.postInitialize(HTPlatformHelper.INSTANCE.getSide())
    }

    private fun blockColor(event: ColorHandlerEvent.Block) {
        HTPlatformHelperForge.blockColors = event.blockColors
    }

    private fun itemColor(event: ColorHandlerEvent.Item) {
        HTPlatformHelperForge.itemColors = event.itemColors
    }

    private fun aboutToServerStart(event: FMLServerAboutToStartEvent) {
        HTMaterialsCoreForge.registerRecipes()
        event.server.dataPackManager.addPackFinder(HTResourcePackProvider.SERVER)
        HTMaterialsAPI.log("Registered runtime data pack!")
    }

    @Suppress("unused")
    @SubscribeEvent
    fun onItemTooltip(event: ItemTooltipEvent) {
        val stack: ItemStack = event.itemStack
        if (stack.isEmpty) return
        val tooltip: MutableList<Text> = event.toolTip

        HTMaterialsAPI.INSTANCE.partManager().getPart(stack.item)?.let {
            it.getMaterial().appendTooltip(it.shapeKey, stack, tooltip)
        }

        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent { handler ->
            handler.let { (0..handler.tanks).map(handler::getFluidInTank) }
                .map(FluidStack::getFluid)
                .mapNotNull { HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(it) }
                .map(HTMaterialKey::getMaterial)
                .forEach { it.appendTooltip(null, stack, tooltip) }
        }
    }
}
