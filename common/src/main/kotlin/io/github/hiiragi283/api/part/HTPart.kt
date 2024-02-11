package io.github.hiiragi283.api.part

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

data class HTPart(
    val materialKey: HTMaterialKey,
    val shapeKey: HTShapeKey,
) {
    constructor(material: HTMaterial, shape: HTShape) : this(material.key, shape.key)

    fun getMaterial(): HTMaterial = materialKey.getMaterial()

    fun getShape(): HTShape = shapeKey.getShape()

    fun getPartId(): Identifier = Identifier("part", "$shapeKey/$materialKey")

    fun getPartTag(): Tag.Identified<Item> = HTPlatformHelper.INSTANCE.getItemTag(getPartId())

    companion object {
        @JvmStatic
        private lateinit var cache: Map<Identifier, HTPart>

        @JvmStatic
        fun initCache() {
            val map: MutableMap<Identifier, HTPart> = hashMapOf()
            HTMaterialsAPI.INSTANCE.shapeRegistry().getValues().forEach { shape ->
                HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { material ->
                    shape.getCommonId(material)
                    map[shape.getCommonId(material)] = HTPart(material, shape.key)
                }
            }
            cache = map
        }

        @JvmStatic
        fun fromTag(tag: Tag<*>): HTPart? = (tag as? Tag.Identified<*>)?.id?.let(Companion::fromId)

        @JvmStatic
        fun fromId(id: Identifier): HTPart? = if (id.namespace == "c") cache[id] else null
    }
}
