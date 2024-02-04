package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.util.getTransaction
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

@Suppress("UnstableApiUsage")
object HTMaterials : PreLaunchEntrypoint, ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    private lateinit var itemGroup: ItemGroup
    private lateinit var iconItem: Item

    @JvmStatic
    fun itemGroup(): ItemGroup = itemGroup

    @JvmStatic
    fun iconItem(): Item = iconItem

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
        itemGroup = FabricItemGroupBuilder.create(HTMaterialsAPI.id("material")).icon { iconItem.defaultStack }.build()
        iconItem = Registry.register(
            Registry.ITEM,
            HTMaterialsAPI.id("icon"),
            Item(FabricItemSettings().group(itemGroup).rarity(Rarity.EPIC)),
        )
        HTMaterialsCore.createContent(Registry.BLOCK_KEY)
        HTMaterialsAPI.log("All Material Blocks registered!")
        HTMaterialsCore.createContent(Registry.FLUID_KEY)
        HTMaterialsAPI.log("All Material Fluids registered!")
        HTMaterialsCore.createContent(Registry.ITEM_KEY)
        HTMaterialsAPI.log("All Material Items registered!")
    }

    // ClientModInitializer
    override fun onInitializeClient() {
        HTMaterialsCore.postInitialize(EnvType.CLIENT)
        // ItemTooltipCallback.EVENT.register(::getTooltip)
        (MinecraftClient.getInstance().resourcePackManager as MutableResourcePackManager).`ht_materials$addPackProvider`(
            HTResourcePackProvider.CLIENT,
        )
        HTMaterialsAPI.log("Registered runtime resource pack!")
        HTMaterialsAPI.log("Client post-initialize completed!")
    }

    @Suppress("UNUSED_PARAMETER")
    private fun getTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        HTMaterialsAPI.INSTANCE.partManager().getPart(stack.item)?.let {
            HTMaterial.appendTooltip(it.getMaterial(), it.shapeKey, stack, lines)
        }

        FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
            ?.iterable(getTransaction())
            ?.map(StorageView<FluidVariant>::getResource)
            ?.map(FluidVariant::getFluid)
            ?.mapNotNull { HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(it) }
            ?.forEach { HTMaterial.appendTooltip(it.getMaterial(), null, stack, lines) }
    }

    // DedicatedServerModInitializer
    override fun onInitializeServer() {
        HTMaterialsCore.postInitialize(EnvType.SERVER)
        HTMaterialsAPI.log("Server post-initialize completed!")
    }
}
