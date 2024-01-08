package io.github.hiiragi283.material.api.part

import io.github.hiiragi283.material.api.item.HTMaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.item.Item
import net.minecraft.item.Items

data class HTPart(
    override val materialKey: HTMaterialKey,
    override val shapeKey: HTShapeKey
) : HTMaterialItemConvertible {

    fun getMaterial(): HTMaterial = materialKey.getMaterial()

    fun getMaterialOrNull(): HTMaterial? = materialKey.getMaterialOrNull()

    fun getShape(): HTShape = shapeKey.getShape()

    fun getShapeOrNull(): HTShape? = shapeKey.getShapeOrNull()

    override fun asItem(): Item = HTPartManager.getDefaultItem(materialKey, shapeKey) ?: Items.AIR

}