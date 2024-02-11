package io.github.hiiragi283.fabric.content

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
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.data.server.BlockLootTableGenerator
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.tag.Tag
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.function.Supplier
import net.minecraft.block.Block as MCBlock
import net.minecraft.item.Item as MCItem

class HTStorageBlockContent(
    private val strength: Float = 5.0f,
    private val toolTag: Supplier<Tag<MCItem>>? = null,
    private val toolLevel: Int = 0,
) : HTMaterialContent.Block(HTShapeKeys.BLOCK) {
    private fun getBlockSetting(type: HTMaterialType): FabricBlockSettings {
        val material: Material = when (type) {
            is HTMaterialType.Gem -> Material.STONE
            is HTMaterialType.Metal -> Material.METAL
            is HTMaterialType.Stone -> Material.STONE
            is HTMaterialType.Undefined -> Material.SOIL
            is HTMaterialType.Wood -> Material.WOOD
        }
        return FabricBlockSettings.of(material).apply {
            toolTag?.let {
                strength(strength)
                requiresTool()
                breakByTool(it.get(), toolLevel)
            } ?: run {
                breakByHand(true)
            }
        }
    }

    companion object {
        private fun getResourcePath(type: HTMaterialType): String = when (type) {
            is HTMaterialType.Gem -> "gem"
            is HTMaterialType.Metal -> "metal"
            is HTMaterialType.Stone -> "solid"
            is HTMaterialType.Undefined -> "solid"
            is HTMaterialType.Wood -> "solid"
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
