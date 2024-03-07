package io.github.hiiragi283.api.part

import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import net.fabricmc.fabric.api.util.Item2ObjectMap
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.Tag

interface HTPartRegistry : Item2ObjectMap<HTPart?> {
    val allEntries: Map<Item, HTPart>

    fun add(item: ItemConvertible, materialKey: HTMaterialKey, shapeKey: HTShapeKey) {
        add(item, HTPart(materialKey, shapeKey))
    }

    fun add(tag: Tag<Item>, materialKey: HTMaterialKey, shapeKey: HTShapeKey) {
        add(tag, HTPart(materialKey, shapeKey))
    }

    fun getItem(part: HTPart): Collection<Item>

    fun convertItems(item: ItemConvertible): Collection<Item> = get(item)?.let(::getItem) ?: listOf()

    operator fun contains(item: ItemConvertible): Boolean

    operator fun contains(part: HTPart): Boolean
}
