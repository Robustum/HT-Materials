package io.github.hiiragi283.fabric

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Rarity

@Suppress("UnstableApiUsage")
object HTMaterialsFabric : PreLaunchEntrypoint, ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    // PreLaunchEntrypoint
    override fun onPreLaunch() {
        // Collect Addons
        HTMaterialsCoreFabric.initAddons()
        // Create Shapes
        HTMaterialsAPIFabric.shapeRegistry = HTShapeRegistry(HTMaterialsCoreFabric.createShapeMap())
        // Create Materials
        HTMaterialsAPIFabric.materialRegistry = HTMaterialRegistry(HTMaterialsCoreFabric.createMaterialMap())
        HTMaterialsCoreFabric.verifyMaterial()
        // Init HTPart cache
        HTPart.initCache()
    }

    // ModInitializer
    override fun onInitialize() {
        // Initialize Game Objects
        HTMaterialsAPIFabric.itemGroup = FabricItemGroupBuilder.build(HTMaterialsAPI.id("material")) {
            HTMaterialsAPI.INSTANCE.iconItem().defaultStack
        }
        HTMaterialsAPIFabric.iconItem = HTPlatformHelper.INSTANCE.registerItem(
            "icon",
            Item(Item.Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).rarity(Rarity.EPIC)),
        )
        HTMaterialsCoreFabric.initContents()
    }

    // ClientModInitializer
    override fun onInitializeClient() {
        HTMaterialsCoreFabric.postInitialize(HTPlatformHelper.Side.CLIENT)
        HTMaterialsCoreFabric.registerRecipes()
        ItemTooltipCallback.EVENT.register(HTMaterialsFabric::getTooltip)
        (MinecraftClient.getInstance().resourcePackManager as MutableResourcePackManager)
            .`ht_materials$addPackProvider`(HTResourcePackProvider.CLIENT)
        HTMaterialsAPI.log("Registered runtime resource pack!")
        HTMaterialsAPI.log("Client post-initialize completed!")
    }

    @Suppress("UNUSED_PARAMETER", "DEPRECATION")
    private fun getTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        if (stack.isEmpty) return

        HTMaterialsAPI.INSTANCE.partManager().getPart(stack.item)?.let {
            it.getMaterial().appendTooltip(it.shapeKey, stack, lines)
        }

        FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
            ?.iterable(Transaction.getCurrentUnsafe()?.openNested() ?: Transaction.openOuter())
            ?.map(StorageView<FluidVariant>::getResource)
            ?.map(FluidVariant::getFluid)
            ?.mapNotNull { HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(it) }
            ?.forEach { it.getMaterial().appendTooltip(null, stack, lines) }
    }

    // DedicatedServerModInitializer
    override fun onInitializeServer() {
        HTMaterialsCoreFabric.postInitialize(HTPlatformHelper.Side.SERVER)
        HTMaterialsCoreFabric.registerRecipes()
        HTMaterialsAPI.log("Server post-initialize completed!")
    }
}
