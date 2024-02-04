package io.github.hiiragi283.api

import io.github.hiiragi283.api.util.getInstance
import net.fabricmc.api.EnvType
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import java.util.function.Supplier

interface HTPlatformHelper {
    fun isDevelop(): Boolean

    fun getEnvType(): EnvType

    fun onEnv(envType: EnvType, action: () -> Unit) {
        if (getEnvType() == envType) action()
    }

    fun getAllModId(): Collection<String>

    fun isModLoaded(id: String): Boolean

    //    Loader    //

    fun getLoaderType(): Loader

    fun isFabric(): Boolean = getLoaderType() == Loader.FABRIC

    fun isForge(): Boolean = getLoaderType() == Loader.FORGE

    fun onLoader(loader: Loader, action: () -> Unit) {
        if (getLoaderType() == loader) action()
    }

    //    Tag    //

    fun getBlockTag(id: Identifier): Tag.Identified<Block>

    fun getFluidTag(id: Identifier): Tag.Identified<Fluid>

    fun getItemTag(id: Identifier): Tag.Identified<Item>

    //    Register    //

    fun registerBlock(id: String, block: Supplier<Block>): Supplier<Block>

    fun registerFluid(id: String, fluid: Supplier<Fluid>): Supplier<Fluid>

    fun registerItem(id: String, item: Supplier<Item>): Supplier<Item>

    enum class Loader(val tagNamespace: String) {
        FABRIC("c"),
        FORGE("forge"),
    }

    companion object {
        @JvmStatic
        val INSTANCE: HTPlatformHelper = getInstance()
    }
}
