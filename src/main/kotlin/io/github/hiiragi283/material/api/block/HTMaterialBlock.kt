package io.github.hiiragi283.material.api.block

import io.github.hiiragi283.material.api.item.HTMaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.text.MutableText

class HTMaterialBlock(
    override val materialHT: HTMaterial,
    override val shapeHT: HTShape,
    settings: FabricBlockSettings
) : Block(settings), HTMaterialItemConvertible {

    override fun getName(): MutableText = getPart().getTranslatedText()

}