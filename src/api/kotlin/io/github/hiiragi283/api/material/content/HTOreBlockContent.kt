package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.tag.Tag
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import java.util.function.Supplier

class HTOreBlockContent(
    override var harvestTool: Supplier<Tag<net.minecraft.item.Item>>?,
    override var harvestLevel: Int,
) : HTMaterialContent.Block(HTShapeKeys.ORE) {
    //    HTMaterialContent    //

    override fun block(materialKey: HTMaterialKey): net.minecraft.block.Block = BlockImpl(
        materialKey,
        shapeKey,
        FabricBlockSettings.copyOf(Blocks.STONE).apply {
            harvestTool?.let {
                requiresTool()
                breakByTool(it.get(), harvestLevel)
            } ?: run {
                breakByHand(true)
            }
        },
    )

    override fun blockItem(materialKey: HTMaterialKey): BlockItem = BlockItemImpl(block, materialKey, shapeKey)

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
