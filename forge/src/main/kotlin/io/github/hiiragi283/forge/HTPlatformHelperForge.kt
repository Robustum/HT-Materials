package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.ForgeTagHandler
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLLoader
import net.minecraftforge.fml.loading.moddiscovery.ModInfo
import net.minecraftforge.registries.ForgeRegistries

class HTPlatformHelperForge : HTPlatformHelper {
    companion object {
        internal val BLOCK_COLORS: MutableMap<Block, BlockColorProvider> = hashMapOf()
        internal val ITEM_COLORS: MutableMap<Item, ItemColorProvider> = hashMapOf()
    }

    override fun getAllModId(): Collection<String> = ModList.get().mods.map(ModInfo::getModId)

    override fun isDevelop(): Boolean = !FMLLoader.isProduction()

    override fun isModLoaded(id: String): Boolean = ModList.get().isLoaded(id)

    override fun getSide(): HTPlatformHelper.Side = when (FMLLoader.getDist()) {
        Dist.CLIENT -> HTPlatformHelper.Side.CLIENT
        Dist.DEDICATED_SERVER -> HTPlatformHelper.Side.SERVER
        null -> throw IllegalStateException("")
    }

    override fun getLoaderType(): HTPlatformHelper.Loader = HTPlatformHelper.Loader.FORGE

    override fun getBlockTag(id: Identifier): Tag.Identified<Block> =
        ForgeTagHandler.createOptionalTag(ForgeRegistries.BLOCKS, id)

    override fun getFluidTag(id: Identifier): Tag.Identified<Fluid> =
        ForgeTagHandler.createOptionalTag(ForgeRegistries.FLUIDS, id)

    override fun getItemTag(id: Identifier): Tag.Identified<Item> =
        ForgeTagHandler.createOptionalTag(ForgeRegistries.ITEMS, id)

    override fun getBlock(id: String): Block = ForgeRegistries.BLOCKS.getValue(HTMaterialsAPI.id(id)) ?: Blocks.AIR

    override fun getFluid(id: String): Fluid = ForgeRegistries.FLUIDS.getValue(HTMaterialsAPI.id(id)) ?: Fluids.EMPTY

    override fun getItem(id: String): Item = ForgeRegistries.ITEMS.getValue(HTMaterialsAPI.id(id)) ?: Items.AIR

    override fun <T : Block> registerBlock(id: String, block: T): T = block
        .apply { setRegistryName(HTMaterialsAPI.MOD_ID, id) }
        .also(ForgeRegistries.BLOCKS::register)

    override fun <T : Fluid> registerFluid(id: String, fluid: T): T = fluid
        .apply { setRegistryName(HTMaterialsAPI.MOD_ID, id) }
        .also(ForgeRegistries.FLUIDS::register)

    override fun <T : Item> registerItem(id: String, item: T): T = item
        .apply { setRegistryName(HTMaterialsAPI.MOD_ID, id) }
        .also(ForgeRegistries.ITEMS::register)

    override fun registerBlockColor(provider: BlockColorProvider, block: Block) {
        BLOCK_COLORS[block] = provider
    }

    override fun registerItemColor(provider: ItemColorProvider, item: Item) {
        ITEM_COLORS[item] = provider
    }
}