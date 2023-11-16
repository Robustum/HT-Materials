package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible

interface MaterialItemConvertible : ItemConvertible {

    val material: HTMaterial
    val shape: HTShape

    fun getPart(): HTPart = HTPart(material, shape)

}