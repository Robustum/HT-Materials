package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.item.ItemConvertible

@JvmDefaultWithCompatibility
interface HTMaterialItemConvertible : ItemConvertible {

    val materialKey: HTMaterialKey
    val shapeKey: HTShapeKey

    operator fun component1(): HTMaterialKey = materialKey

    operator fun component2(): HTShapeKey = shapeKey

}