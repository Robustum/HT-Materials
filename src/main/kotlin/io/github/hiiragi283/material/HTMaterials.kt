package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.part.getPart
import io.github.hiiragi283.material.api.util.getTransaction
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
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UnstableApiUsage")
object HTMaterials : PreLaunchEntrypoint, ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    const val MOD_ID: String = "ht_materials"
    const val MOD_NAME: String = "HT Materials"

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

    private val logger: Logger = LogManager.getLogger(MOD_NAME)

    @JvmOverloads
    @JvmStatic
    fun log(message: String, level: Level = Level.INFO) {
        logger.log(level, "[$MOD_NAME] $message")
    }

    private lateinit var itemGroup: ItemGroup
    private lateinit var iconItem: Item

    @JvmStatic
    fun itemGroup(): ItemGroup = itemGroup

    @JvmStatic
    fun iconItem(): Item = iconItem

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

    override fun onInitialize() {
        // Initialize Game Objects
        itemGroup = FabricItemGroupBuilder.create(id("material")).icon { iconItem.defaultStack }.build()
        iconItem = Registry.register(Registry.ITEM, id("icon"), Item(FabricItemSettings().group(itemGroup).rarity(Rarity.EPIC)))
        HTMaterialsCore.createContent(Registry.BLOCK_KEY)
        log("All Material Blocks Registered!")
        HTMaterialsCore.createContent(Registry.FLUID_KEY)
        log("All Material Fluids Registered!")
        HTMaterialsCore.createContent(Registry.ITEM_KEY)
        log("All Material Items Registered!")
    }

    override fun onInitializeClient() {
        HTMaterialsCore.postInitialize(EnvType.CLIENT)
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
        log("Client Post-Initialization completed!")
    }

    override fun onInitializeServer() {
        HTMaterialsCore.postInitialize(EnvType.SERVER)
        log("Server Post-Initialization completed!")
    }
}
