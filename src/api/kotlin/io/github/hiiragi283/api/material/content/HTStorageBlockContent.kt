package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extention.runWhenOn
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.resource.HTRuntimeDataPack
import io.github.hiiragi283.api.resource.HTRuntimeResourcePack
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.block.AbstractBlock
import net.minecraft.data.server.BlockLootTableGenerator
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.tag.Tag
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.function.Supplier

class HTStorageBlockContent(
    private val strength: Float = 5.0f,
    override var harvestTool: Supplier<Tag<net.minecraft.item.Item>>? = null,
    override var harvestLevel: Int = 0,
) : HTMaterialContent.Block(HTShapeKeys.BLOCK) {
    private fun getBlockSetting(type: HTMaterialType) = AbstractBlock.Settings.of(type.blockMaterial).strength(strength)

    //    HTMaterialContent    //

    override fun block(materialKey: HTMaterialKey): net.minecraft.block.Block = BlockImpl(
        materialKey,
        shapeKey,
        getBlockSetting(materialKey.getMaterial().type),
    )

    override fun blockItem(materialKey: HTMaterialKey): BlockItem = BlockItemImpl(block.get(), materialKey, shapeKey)

    override fun postInit(materialKey: HTMaterialKey) {
        super.postInit(materialKey)
        // LootTable
        HTRuntimeDataPack.addBlockLootTable(block.get(), BlockLootTableGenerator.drops(blockItem.get()))
        // Client-only
        EnvType.CLIENT.runWhenOn {
            // BlockColor
            ColorProviderRegistry.BLOCK.register(
                { _, _, _, _ -> materialKey.getMaterial().color().rgb },
                block.get(),
            )
            // BlockState
            val modelId: Identifier = HTMaterialsAPI.id("block/storage/${materialKey.getMaterial().type.resourcePath}")
            HTRuntimeResourcePack.addBlockState(
                block.get(),
                buildJson {
                    addObject("variants") {
                        addObject("") {
                            addProperty("model", modelId.toString())
                        }
                    }
                },
            )
            // ItemColor
            ColorProviderRegistry.ITEM.register(
                { _, tintIndex: Int -> if (tintIndex == 0) materialKey.getMaterial().color().rgb else -1 },
                blockItem.get(),
            )
            // Model
            HTRuntimeResourcePack.addModel(blockItem.get(), buildJson { addProperty("parent", modelId.toString()) })
        }
    }

    //    Block    //

    private class BlockImpl(
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
        settings: Settings,
    ) : net.minecraft.block.Block(settings) {
        override fun getName(): MutableText = shapeKey.getTranslatedText(materialKey)
    }

    //    BlockItem    //

    private class BlockItemImpl(
        block: net.minecraft.block.Block,
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
    ) : BlockItem(block, Settings().group(HTMaterialsAPI.INSTANCE.itemGroup())) {
        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)
    }
}
