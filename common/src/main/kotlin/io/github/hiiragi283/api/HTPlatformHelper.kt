package io.github.hiiragi283.api

import io.github.hiiragi283.api.util.getInstance
import net.minecraft.block.Block
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

interface HTPlatformHelper {
    companion object {
        @JvmStatic
        val INSTANCE: HTPlatformHelper = getInstance()
    }

    fun getAllModId(): Collection<String>

    fun isDevelop(): Boolean

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

    fun onLoader(loader: Loader, action: () -> Unit) {
        if (getLoaderType() == loader) action()
    }

    enum class Loader(val tagNamespace: String) {
        FABRIC("c"),
        FORGE("forge");

        fun id(path: String) = Identifier(tagNamespace, path)
    }

    //    Tag    //

    fun getBlockTag(id: Identifier): Tag.Identified<Block>

    fun getFluidTag(id: Identifier): Tag.Identified<Fluid>

    fun getItemTag(id: Identifier): Tag.Identified<Item>

    fun getCommonBlockTag(path: String): Tag.Identified<Block> = getBlockTag(Identifier(getLoaderType().tagNamespace, path))

    fun getCommonFluidTag(path: String): Tag.Identified<Fluid> = getFluidTag(Identifier(getLoaderType().tagNamespace, path))

    fun getCommonItemTag(path: String): Tag.Identified<Item> = getItemTag(Identifier(getLoaderType().tagNamespace, path))

    //    Registry    //

    fun getBlock(id: String): Block

    fun getFluid(id: String): Fluid

    fun getItem(id: String): Item

    fun <T : Block> registerBlock(id: String, block: T): T

    fun <T : Fluid> registerFluid(id: String, fluid: T): T

    fun <T : Item> registerItem(id: String, item: T): T

    fun registerBlockColor(provider: BlockColorProvider, block: Block)

    fun registerItemColor(provider: ItemColorProvider, item: Item)
}