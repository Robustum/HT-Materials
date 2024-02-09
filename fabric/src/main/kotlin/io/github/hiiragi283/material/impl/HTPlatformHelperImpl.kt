package io.github.hiiragi283.material.impl

import com.google.common.base.Suppliers
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.tag.TagRegistry
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.api.metadata.ModMetadata
import net.minecraft.block.Block
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

class HTPlatformHelperImpl : HTPlatformHelper {
    override fun getAllModId(): Collection<String> = FabricLoader.getInstance()
        .allMods
        .map(ModContainer::getMetadata)
        .map(ModMetadata::getId)

    override fun isDevelop() = FabricLoader.getInstance().isDevelopmentEnvironment

    override fun isModLoaded(id: String): Boolean = FabricLoader.getInstance().isModLoaded(id)

    override fun getSide(): HTPlatformHelper.Side = when (FabricLoader.getInstance().environmentType) {
        EnvType.CLIENT -> HTPlatformHelper.Side.CLIENT
        EnvType.SERVER -> HTPlatformHelper.Side.SERVER
        null -> throw IllegalStateException("")
    }

    override fun getLoaderType(): HTPlatformHelper.Loader = HTPlatformHelper.Loader.FABRIC

    override fun getBlockTag(id: Identifier): Tag.Identified<Block> = TagRegistry.block(id) as Tag.Identified<Block>

    override fun getFluidTag(id: Identifier): Tag.Identified<Fluid> = TagRegistry.fluid(id) as Tag.Identified<Fluid>

    override fun getItemTag(id: Identifier): Tag.Identified<Item> = TagRegistry.item(id) as Tag.Identified<Item>

    override fun getBlock(id: String): Block = Registry.BLOCK.get(HTMaterialsAPI.id(id))

    override fun getFluid(id: String): Fluid = Registry.FLUID.get(HTMaterialsAPI.id(id))

    override fun getItem(id: String): Item = Registry.ITEM.get(HTMaterialsAPI.id(id))

    override fun <T : Block> registerBlock(id: String, block: Supplier<T>): Supplier<T> =
        Registry.register(Registry.BLOCK, HTMaterialsAPI.id(id), block.get()).let { Suppliers.ofInstance(it) }

    override fun <T : Fluid> registerFluid(id: String, fluid: Supplier<T>): Supplier<T> =
        Registry.register(Registry.FLUID, HTMaterialsAPI.id(id), fluid.get()).let { Suppliers.ofInstance(it) }

    override fun <T : Item> registerItem(id: String, item: Supplier<T>): Supplier<T> =
        Registry.register(Registry.ITEM, HTMaterialsAPI.id(id), item.get()).let { Suppliers.ofInstance(it) }

    override fun registerBlockColor(provider: BlockColorProvider, block: Block) {
        ColorProviderRegistry.BLOCK.register(provider, block)
    }

    override fun registerItemColor(provider: ItemColorProvider, item: Item) {
        ColorProviderRegistry.ITEM.register(provider, item)
    }
}
