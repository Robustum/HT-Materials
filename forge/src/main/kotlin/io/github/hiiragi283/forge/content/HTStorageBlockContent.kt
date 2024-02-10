package io.github.hiiragi283.forge.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.api.util.resource.HTRuntimeDataPack
import io.github.hiiragi283.api.util.resource.HTRuntimeResourcePack
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Material
import net.minecraft.data.server.BlockLootTableGenerator
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraftforge.common.ToolType
import net.minecraft.block.Block as MCBlock

class HTStorageBlockContent(
    private val strength: Float = 5.0f,
    private val toolType: ToolType? = null,
    private val toolLevel: Int = 0,
) : HTMaterialContent.Block(HTShapeKeys.BLOCK) {
    private fun getBlockSetting(type: HTMaterialType): AbstractBlock.Settings {
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
        return AbstractBlock.Settings.of(material).apply {
            toolType?.let {
                strength(strength)
                requiresTool()
                harvestTool(it)
                harvestLevel(toolLevel)
            } ?: run {
                breakInstantly()
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

    override fun blockId(materialKey: HTMaterialKey): String = shapeKey.getShape().getIdentifier(materialKey).path

    override fun block(materialKey: HTMaterialKey): MCBlock = BlockImpl(
        materialKey,
        shapeKey,
        getBlockSetting(materialKey.getMaterial().type),
    )

    override fun blockItem(materialKey: HTMaterialKey): BlockItem = BlockItemImpl(block, materialKey, shapeKey)

    override fun postInit(materialKey: HTMaterialKey) {
        // LootTable
        HTRuntimeDataPack.addBlockLootTable(block, BlockLootTableGenerator.drops(block))
        // Client-only
        HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
            // BlockColor
            HTPlatformHelper.INSTANCE.registerBlockColor(
                { _, _, _, _ -> materialKey.getMaterial().color().rgb },
                block,
            )
            // BlockState
            val modelId: Identifier = HTMaterialsAPI.id("block/storage/${getResourcePath(materialKey.getMaterial().type)}")
            HTRuntimeResourcePack.addBlockState(
                block,
                buildJson {
                    addObject("variants") {
                        addObject("") {
                            addProperty("model", modelId.toString())
                        }
                    }
                },
            )
            // ItemColor
            HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
                HTPlatformHelper.INSTANCE.registerItemColor(
                    { _, tintIndex: Int -> if (tintIndex == 0) materialKey.getMaterial().color().rgb else -1 },
                    blockItem,
                )
            }
            // Model
            HTRuntimeResourcePack.addModel(blockItem, buildJson { addProperty("parent", modelId.toString()) })
        }
    }

    //    Block    //

    private class BlockImpl(
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
        settings: Settings,
    ) : MCBlock(settings) {
        override fun getName(): MutableText = shapeKey.getTranslatedText(materialKey)
    }

    //    BlockItem    //

    private class BlockItemImpl(
        block: MCBlock,
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
    ) : BlockItem(block, Settings().group(HTMaterialsAPI.INSTANCE.itemGroup())) {
        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)
    }
}
