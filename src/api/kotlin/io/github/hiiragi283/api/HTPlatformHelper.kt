package io.github.hiiragi283.api

import io.github.hiiragi283.api.util.getInstance
import net.minecraft.block.Block
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import java.util.function.Supplier

interface HTPlatformHelper {
    companion object {
        @JvmStatic
        val INSTANCE: HTPlatformHelper = getInstance()
    }

    fun isDevelop(): Boolean

    fun getAllModId(): Collection<String>

    fun isModLoaded(id: String): Boolean

    //    Side    //

    fun getSide(): Side

    fun isClient(): Boolean = getSide().isClient

    fun onSide(side: Side, action: () -> Unit) {
        if (getSide() == side) action()
    }

    enum class Side(val isClient: Boolean) {
        CLIENT(true),
        SERVER(false),
    }

    //    Loader    //

    fun getLoaderType(): Loader

    fun isFabric(): Boolean = getLoaderType() == Loader.FABRIC

    fun isForge(): Boolean = getLoaderType() == Loader.FORGE

    fun onLoader(loader: Loader, action: () -> Unit) {
        if (getLoaderType() == loader) action()
    }

    enum class Loader(val tagNamespace: String) {
        FABRIC("c"),
        FORGE("forge"),
    }

    //    Tag    //

    fun getBlockTag(id: Identifier): Tag.Identified<Block>

    fun getFluidTag(id: Identifier): Tag.Identified<Fluid>

    fun getItemTag(id: Identifier): Tag.Identified<Item>

    fun getCommonBlockTag(path: String): Tag.Identified<Block> = getBlockTag(Identifier(getLoaderType().tagNamespace, path))

    fun getCommonFluidTag(path: String): Tag.Identified<Fluid> = getFluidTag(Identifier(getLoaderType().tagNamespace, path))

    fun getCommonItemTag(path: String): Tag.Identified<Item> = getItemTag(Identifier(getLoaderType().tagNamespace, path))

    //    Register    //

    fun registerBlock(id: String, block: Supplier<Block>): Supplier<Block>

    fun registerFluid(id: String, fluid: Supplier<Fluid>): Supplier<Fluid>

    fun registerItem(id: String, item: Supplier<Item>): Supplier<Item>

    fun registerBlockColor(provider: BlockColorProvider, block: Block)

    fun registerItemColor(provider: ItemColorProvider, item: Item)
}
