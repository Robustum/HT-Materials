package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.client.HTColoredMaterialBlock
import io.github.hiiragi283.material.client.HTColoredMaterialItem
import io.github.hiiragi283.material.client.HTCustomBlockStateIdItem
import io.github.hiiragi283.material.client.HTCustomModelIdItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class HTStorageBlockContent : HTMaterialContent.BLOCK() {

    override val shapeKey: HTShapeKey = HTShapes.BLOCK

    override fun create(materialKey: HTMaterialKey): Block? = MaterialStorageBlock(
        materialKey,
        shapeKey,
        FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
    ).takeUnless { HTPartManager.hasDefaultItem(materialKey, shapeKey) }

    override fun onCreate(materialKey: HTMaterialKey, created: Block) {
        Registry.register(
            Registry.ITEM,
            getIdentifier(materialKey),
            object : BlockItem(created, FabricItemSettings().group(HTMaterialsCommon.ITEM_GROUP)),
                HTColoredMaterialItem, HTCustomModelIdItem {

                override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

                override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)

                override fun getColorProvider(): ItemColorProvider = ItemColorProvider { _, tintIndex: Int ->
                    if (tintIndex == 0) materialKey.getMaterial().color.rgb else -1
                }

                override fun getModelId(): Identifier = HTMaterialsCommon.id("models/block/block.json")

            }
        ).run {
            HTPartManager.forceRegister(materialKey, shapeKey, this)
        }
    }

    //    Block    //

    private class MaterialStorageBlock(
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
        settings: Settings
    ) : Block(settings), HTColoredMaterialBlock, HTCustomBlockStateIdItem {

        override fun getName(): MutableText = shapeKey.getTranslatedText(materialKey)

        override fun getColorProvider(): BlockColorProvider = BlockColorProvider { _, _, _, _ ->
            materialKey.getMaterial().color.rgb
        }

        override fun getBlockStateId(): Identifier = HTMaterialsCommon.id("blockstates/block.json")

    }

}