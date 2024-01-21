package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.getPart
import io.github.hiiragi283.material.util.getTransaction
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
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UnstableApiUsage")
object HTMaterials : ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    const val MOD_ID: String = "ht_materials"
    const val MOD_NAME: String = "HT Materials"

    private val LOGGER: Logger = LogManager.getLogger(MOD_NAME)

    @JvmField
    val ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(id("material")).icon { ICON.defaultStack }.build()

    @JvmField
    val ICON: Item = Item(FabricItemSettings().group(ITEM_GROUP).rarity(Rarity.EPIC))

    override fun onInitialize() {
        // Collect Addons
        HTMaterialsCore.initEntryPoints()
        // Create Shapes
        HTMaterialsCore.createShape()
        // Create Materials
        HTMaterialsCore.createMaterial()
        HTMaterialsCore.verifyMaterial()
        // Initialize Game Objects
        HTMaterialsCore.createContent(Registry.BLOCK_KEY)
        LOGGER.info("All Material Blocks Registered!")
        HTMaterialsCore.createContent(Registry.FLUID_KEY)
        LOGGER.info("All Material Fluids Registered!")
        Registry.register(Registry.ITEM, id("icon"), ICON)
        HTMaterialsCore.createContent(Registry.ITEM_KEY)
        LOGGER.info("All Material Items Registered!")
    }

    override fun onInitializeClient() {
        HTMaterialsCore.postInitalize(EnvType.CLIENT)
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
        LOGGER.info("Client Post-Initialization completed!")
    }

    override fun onInitializeServer() {
        HTMaterialsCore.postInitalize(EnvType.SERVER)
        LOGGER.info("Server Post-Initialization completed!")
    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)
}
