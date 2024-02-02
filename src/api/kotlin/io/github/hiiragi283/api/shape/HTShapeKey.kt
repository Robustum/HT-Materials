package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import net.minecraft.util.Identifier

data class HTShapeKey(val name: String) {
    val translationKey: String = "ht_shape.$name"

    fun getShape(): HTShape = checkNotNull(HTMaterialsAPI.getInstance().shapeRegistry().getShape(this))

    fun getShapeId() = Identifier("shape", name)
}
