package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible

@JvmDefaultWithCompatibility
interface HTMaterialItemConvertible : ItemConvertible {

    val materialHT: HTMaterial
    val shapeHT: HTShape

    fun getPart(): HTPart = HTPart(materialHT, shapeHT)

    operator fun component1(): HTMaterial = materialHT

    operator fun component2(): HTShape = shapeHT

}