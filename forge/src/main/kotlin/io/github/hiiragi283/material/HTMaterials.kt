package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider
import io.github.hiiragi283.material.impl.HTMaterialsAPIImpl
import io.github.hiiragi283.material.impl.HTPlatformHelperImpl
import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Rarity
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(value = HTMaterialsAPI.MOD_ID)
object HTMaterials {
    init {
        HTMaterialsAPIImpl.itemGroup = object : ItemGroup(GROUPS.size - 1, "${HTMaterialsAPI.MOD_ID}.material") {
            override fun createIcon(): ItemStack = HTMaterialsAPI.INSTANCE.iconItem().defaultStack
        }
        HTMaterialsAPIImpl.iconItem = HTPlatformHelper.INSTANCE.registerItem("icon") {
            Item(Item.Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).rarity(Rarity.EPIC))
        }

        HTMaterialsCore.initAddons()
        HTMaterialsCore.createShape()
        HTMaterialsCore.createMaterial()
        HTMaterialsCore.verifyMaterial()
        HTMaterialsCore.forEachContent(Block::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Blocks registered!")
        HTMaterialsCore.forEachContent(Fluid::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Fluids registered!")
        HTMaterialsCore.forEachContent(Item::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Items registered!")

        MOD_BUS.run {
            HTPlatformHelperImpl.BLOCK.register(this)
            HTPlatformHelperImpl.FLUID.register(this)
            HTPlatformHelperImpl.ITEM.register(this)

            addListener(::commonSetup)
            addListener(::blockColor)
            addListener(::itemColor)
            addListener(::aboutToServerStart)
        }

        HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
            MinecraftClient.getInstance().resourcePackManager.addPackFinder(HTResourcePackProvider.CLIENT)
            HTMaterialsAPI.log("Registered runtime resource pack!")
        }
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        HTMaterialsCore.postInitialize(HTPlatformHelper.INSTANCE.getSide())
    }

    private fun blockColor(event: ColorHandlerEvent.Block) {
        HTPlatformHelperImpl.BLOCK_COLORS.forEach { (block: Block, blockColorProvider: BlockColorProvider) ->
            event.blockColors.registerColorProvider(blockColorProvider, block)
        }
    }

    private fun itemColor(event: ColorHandlerEvent.Item) {
        HTPlatformHelperImpl.ITEM_COLORS.forEach { (item: Item, itemColorProvider: ItemColorProvider) ->
            event.itemColors.register(itemColorProvider, item)
        }
    }

    private fun aboutToServerStart(event: FMLServerAboutToStartEvent) {
        event.server.dataPackManager.addPackFinder(HTResourcePackProvider.SERVER)
        HTMaterialsAPI.log("Registered runtime data pack!")
    }
}
