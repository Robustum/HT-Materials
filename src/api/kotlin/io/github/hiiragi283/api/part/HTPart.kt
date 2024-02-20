package io.github.hiiragi283.api.part

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extention.id
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import net.fabricmc.fabric.api.tag.TagRegistry
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

    fun getPartTag(): Tag<Item> = TagRegistry.item(getPartId())

    companion object {
        @JvmStatic
        private lateinit var cache: Map<Identifier, HTPart>

        @JvmStatic
        fun initCache() {
            val map: MutableMap<Identifier, HTPart> = hashMapOf()
            HTMaterialsAPI.INSTANCE.shapeRegistry().getValues().forEach { shape ->
                HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { material ->
                    map[shape.getCommonId(material)] = HTPart(material, shape.key)
                }
            }
            cache = map
        }

        @JvmStatic
        inline fun <reified T> fromTag(tag: Tag<T>): HTPart? = tag.id()?.let(Companion::fromId)

        @JvmStatic
        fun fromId(id: Identifier): HTPart? = cache[id]
    }
}
