package io.github.hiiragi283.material.api.block

import io.github.hiiragi283.material.api.item.HTMaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.text.MutableText

class HTMaterialBlock(
    override val materialHT: HTMaterial,
    override val shapeHT: HTShape
) : Block(FabricBlockSettings.copyOf(Blocks.STONE)), HTMaterialItemConvertible {

    override fun getName(): MutableText = shapeHT.getTranslatedText(materialHT)

}