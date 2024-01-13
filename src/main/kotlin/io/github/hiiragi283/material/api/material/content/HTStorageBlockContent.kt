package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.HTMaterialType
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.api.util.*
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvironmentInterface
import net.fabricmc.api.EnvironmentInterfaces
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.data.server.BlockLootTableGenerator
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootTable
import net.minecraft.tag.Tag
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class HTStorageBlockContent(
    private val strength: Float = 5.0f,
    private val toolTag: Tag<Item>? = null,
    private val toolLevel: Int = 0
) : HTMaterialContent.BLOCK() {

    private fun getBlockSetting(type: HTMaterialType): FabricBlockSettings {
        val material: Material = when (type) {
            HTMaterialType.Gem.AMETHYST -> Material.STONE
            HTMaterialType.Gem.COAL -> Material.STONE
            HTMaterialType.Gem.CUBIC -> Material.STONE
            HTMaterialType.Gem.DIAMOND -> Material.STONE
            HTMaterialType.Gem.EMERALD -> Material.STONE
            HTMaterialType.Gem.LAPIS -> Material.STONE
            HTMaterialType.Gem.QUARTZ -> Material.STONE
            HTMaterialType.Gem.RUBY -> Material.STONE
            HTMaterialType.Metal -> Material.METAL
            HTMaterialType.Stone -> Material.STONE
            HTMaterialType.Undefined -> Material.SOIL
            HTMaterialType.Wood -> Material.WOOD
        }
        return FabricBlockSettings.of(material).apply {
            toolTag?.let {
                strength(strength)
                this.requiresTool()
                this.breakByTool(it, toolLevel)
            } ?: run {
                this.breakByHand(true)
            }
        }
    }

    companion object {
        private fun getResourcePath(type: HTMaterialType): String = when (type) {
            HTMaterialType.Gem.AMETHYST -> "gem"
            HTMaterialType.Gem.COAL -> "gem"
            HTMaterialType.Gem.CUBIC -> "gem"
            HTMaterialType.Gem.DIAMOND -> "gem"
            HTMaterialType.Gem.EMERALD -> "gem"
            HTMaterialType.Gem.LAPIS -> "gem"
            HTMaterialType.Gem.QUARTZ -> "gem"
            HTMaterialType.Gem.RUBY -> "gem"
            HTMaterialType.Metal -> "metal"
            HTMaterialType.Stone -> "solid"
            HTMaterialType.Undefined -> "solid"
            HTMaterialType.Wood -> "solid"
        }

    }

    //    HTMaterialContent    //

    override val shapeKey: HTShapeKey = HTShapes.BLOCK

    override fun create(materialKey: HTMaterialKey): Block? = BlockImpl(
        materialKey,
        shapeKey,
        getBlockSetting(materialKey.getMaterial().type)
    ).takeUnless { HTPartManager.hasDefaultItem(materialKey, shapeKey) }

    override fun onCreate(materialKey: HTMaterialKey, created: Block) {
        BlockItemImpl(created, materialKey, shapeKey).run {
            Registry.register(Registry.ITEM, getIdentifier(materialKey), this)
            HTPartManager.forceRegister(materialKey, shapeKey, this)
        }
    }

    //    Block    //

    @EnvironmentInterfaces(
        value = [
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomBlockStateBlock::class),
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomColoredBlock::class)
        ]
    )
    private class BlockImpl(
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
        settings: Settings
    ) : Block(settings), HTCustomBlockStateBlock, HTCustomColoredBlock, HTCustomLootTableBlock {

        override fun getName(): MutableText = shapeKey.getTranslatedText(materialKey)

        @Environment(EnvType.CLIENT)
        override fun getBlockStateId(): Identifier =
            HTMaterials.id("blockstates/storage/${getResourcePath(materialKey.getMaterial().type)}.json")

        @Environment(EnvType.CLIENT)
        override fun getColorProvider(): BlockColorProvider = BlockColorProvider { _, _, _, _ ->
            materialKey.getMaterial().color.rgb
        }

        override fun getLootTable(): LootTable.Builder = BlockLootTableGenerator.drops(this)

    }

    //    BlockItem    //

    @EnvironmentInterfaces(
        value = [
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomColoredItem::class),
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomModelItem::class)
        ]
    )
    private class BlockItemImpl(
        block: Block,
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey
    ) : BlockItem(
        block,
        FabricItemSettings().group(HTMaterials.ITEM_GROUP)
    ), HTCustomColoredItem, HTCustomModelItem {

        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)

        @Environment(EnvType.CLIENT)
        override fun getColorProvider(): ItemColorProvider = ItemColorProvider { _, tintIndex: Int ->
            if (tintIndex == 0) materialKey.getMaterial().color.rgb else -1
        }

        @Environment(EnvType.CLIENT)
        override fun getModelId(): Identifier =
            HTMaterials.id("models/block/storage/${getResourcePath(materialKey.getMaterial().type)}.json")

    }

}