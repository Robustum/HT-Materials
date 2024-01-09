package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.block.Block as MCBlock
import net.minecraft.item.Item as MCItem

sealed interface HTMaterialContent<T> {

    val shapeKey: HTShapeKey

    fun getContentType(): Type

    fun create(materialKey: HTMaterialKey): T

    interface Block : HTMaterialContent<MCBlock> {

        override fun getContentType(): Type = Type.BLOCK

    }

    /*interface Fluid : HTMaterialContent<MCFluid> {

        override fun getContentType(): Type = Type.FLUID

    }*/

    interface Item : HTMaterialContent<MCItem> {

        override fun getContentType(): Type = Type.ITEM

    }

    enum class Type {
        BLOCK, FLUID, ITEM
    }

}