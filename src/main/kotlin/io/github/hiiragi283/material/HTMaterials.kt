package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.util.getTransaction
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

@Suppress("UnstableApiUsage")
object HTMaterials : HTMaterialsAPI, PreLaunchEntrypoint, ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    private lateinit var itemGroup: ItemGroup
    private lateinit var iconItem: Item

    @JvmStatic
    fun itemGroup(): ItemGroup = itemGroup

    @JvmStatic
    fun iconItem(): Item = iconItem

    // HTMaterialsAPI
    internal lateinit var shapeRegistry: HTShapeRegistry
    internal lateinit var materialRegistry: HTMaterialRegistry
    internal lateinit var fluidManager: HTFluidManager
    internal lateinit var partManager: HTPartManager

    override fun shapeRegistry(): HTShapeRegistry = shapeRegistry

    override fun materialRegistry(): HTMaterialRegistry = materialRegistry

    override fun fluidManager(): HTFluidManager = fluidManager

    override fun partManager(): HTPartManager = partManager

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
        // HTPart.initCache()
    }

    // ModInitializer
    override fun onInitialize() {
        // Initialize Game Objects
        itemGroup = FabricItemGroupBuilder.create(HTMaterialsAPI.id("io/github/hiiragi283/material")).icon { iconItem.defaultStack }.build()
        iconItem = Registry.register(
            Registry.ITEM,
            HTMaterialsAPI.id("icon"),
            Item(FabricItemSettings().group(itemGroup).rarity(Rarity.EPIC)),
        )
        HTMaterialsCore.createContent(Registry.BLOCK_KEY)
        HTMaterialsAPI.log("All Material Blocks Registered!")
        HTMaterialsCore.createContent(Registry.FLUID_KEY)
        HTMaterialsAPI.log("All Material Fluids Registered!")
        HTMaterialsCore.createContent(Registry.ITEM_KEY)
        HTMaterialsAPI.log("All Material Items Registered!")
    }

    // ClientModInitializer
    override fun onInitializeClient() {
        HTMaterialsCore.postInitialize(EnvType.CLIENT)
        ItemTooltipCallback.EVENT.register(::getTooltip)
        HTMaterialsAPI.log("Client Post-Initialization completed!")
    }

    private fun getTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        HTMaterialsAPI.getInstance().partManager().getPart(stack.item)?.let {
            HTMaterial.appendTooltip(it.getMaterial(), it.shapeKey, stack, lines)
        }

        FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
            ?.iterable(getTransaction())
            ?.map(StorageView<FluidVariant>::getResource)
            ?.map(FluidVariant::getFluid)
            ?.mapNotNull { HTMaterialsAPI.getInstance().fluidManager().getMaterialKey(it) }
            ?.forEach { HTMaterial.appendTooltip(it.getMaterial(), null, stack, lines) }
    }

    // DedicatedServerModInitializer
    override fun onInitializeServer() {
        HTMaterialsCore.postInitialize(EnvType.SERVER)
        HTMaterialsAPI.log("Server Post-Initialization completed!")
    }
}
