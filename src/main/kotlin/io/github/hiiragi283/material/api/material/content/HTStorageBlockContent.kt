package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.text.MutableText

class HTStorageBlockContent : HTMaterialContent.Block {

    override val shapeKey: HTShapeKey = HTShapes.BLOCK

    override fun create(materialKey: HTMaterialKey): Block =
        MaterialStorageBlock(materialKey, shapeKey, FabricBlockSettings.copyOf(Blocks.IRON_BLOCK))

    private class MaterialStorageBlock(
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
        settings: Settings
    ) : Block(settings) {

        override fun getName(): MutableText = shapeKey.getTranslatedText(materialKey)

    }

}