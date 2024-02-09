package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider
import io.github.hiiragi283.material.impl.HTMaterialsAPIImpl
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Rarity

@Suppress("UnstableApiUsage")
object HTMaterials : PreLaunchEntrypoint, ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    // PreLaunchEntrypoint
    override fun onPreLaunch() {
        // Collect Addons
        HTMaterialsCore.initEntryPoints()
        // Create Shapes
        HTMaterialsCore.createShape()
        // Create Materials
        HTMaterialsCore.createMaterial()
        HTMaterialsCore.verifyMaterial()
        // Init HTPart cache
        HTPart.initCache()
    }

    // ModInitializer
    override fun onInitialize() {
        // Initialize Game Objects
        HTMaterialsAPIImpl.itemGroup = object : ItemGroup(GROUPS.size - 1, "${HTMaterialsAPI.MOD_ID}.material") {
            override fun createIcon(): ItemStack = HTMaterialsAPI.INSTANCE.iconItem().defaultStack
        }
        HTMaterialsAPIImpl.iconItem = HTPlatformHelper.INSTANCE.registerItem("icon") {
            Item(Item.Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).rarity(Rarity.EPIC))
        }
        HTMaterialsCore.forEachContent(Block::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Blocks registered!")
        HTMaterialsCore.forEachContent(Fluid::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Fluids registered!")
        HTMaterialsCore.forEachContent(Item::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Items registered!")
    }

    // ClientModInitializer
    override fun onInitializeClient() {
        HTMaterialsCore.postInitialize(HTPlatformHelper.Side.CLIENT)
        ItemTooltipCallback.EVENT.register(::getTooltip)
        (MinecraftClient.getInstance().resourcePackManager as MutableResourcePackManager)
            .`ht_materials$addPackProvider`(HTResourcePackProvider.CLIENT)
        HTMaterialsAPI.log("Registered runtime resource pack!")
        HTMaterialsAPI.log("Client post-initialize completed!")
    }

    @Suppress("UNUSED_PARAMETER", "DEPRECATION")
    private fun getTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        HTMaterialsAPI.INSTANCE.partManager().getPart(stack.item)?.let {
            HTMaterial.appendTooltip(it.getMaterial(), it.shapeKey, stack, lines)
        }

        FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
            ?.iterable(Transaction.getCurrentUnsafe()?.openNested() ?: Transaction.openOuter())
            ?.map(StorageView<FluidVariant>::getResource)
            ?.map(FluidVariant::getFluid)
            ?.mapNotNull { HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(it) }
            ?.forEach { HTMaterial.appendTooltip(it.getMaterial(), null, stack, lines) }
    }

    // DedicatedServerModInitializer
    override fun onInitializeServer() {
        HTMaterialsCore.postInitialize(HTPlatformHelper.Side.SERVER)
        HTMaterialsAPI.log("Server post-initialize completed!")
    }
}
