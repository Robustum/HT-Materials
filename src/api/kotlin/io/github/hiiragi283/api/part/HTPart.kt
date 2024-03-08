package io.github.hiiragi283.api.part

import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShape
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

data class HTPart(
    val materialKey: HTMaterialKey,
    val shapeKey: HTShape,
) {
    constructor(material: HTMaterial, shapeKey: HTShape) : this(material.key, shapeKey)

    val material: HTMaterial
        get() = materialKey.material

    val partId: Identifier = Identifier("c", "$shapeKey/$materialKey")

    val partTag: Tag<Item>
        get() = TagRegistry.item(partId)
}
