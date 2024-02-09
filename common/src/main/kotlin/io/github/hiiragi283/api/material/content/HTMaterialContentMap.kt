package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item

class HTMaterialContentMap {
    private val blockMap: MutableMap<HTShapeKey, HTMaterialContent<*>> = hashMapOf()
    private val fluidMap: MutableMap<HTShapeKey, HTMaterialContent<*>> = hashMapOf()
    private val itemMap: MutableMap<HTShapeKey, HTMaterialContent<*>> = hashMapOf()

    private fun <T> getMap(clazz: Class<T>): MutableMap<HTShapeKey, HTMaterialContent<*>> = when (clazz) {
        Block::class.java -> blockMap
        Fluid::class.java -> fluidMap
        Item::class.java -> itemMap
        else -> mutableMapOf()
    }

    fun <T> add(content: HTMaterialContent<T>) {
        getMap(content.objClass)[content.shapeKey] = content
    }

    fun <T> remove(content: HTMaterialContent<T>, shapeKey: HTShapeKey) = getMap(content.objClass).remove(shapeKey)

    fun <T> getContents(clazz: Class<T>): Collection<HTMaterialContent<T>> = getMap(clazz).values.filterIsInstance<HTMaterialContent<T>>()
}
