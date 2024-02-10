package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
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

    fun addMetalComponents() = apply {
        add(HTSimpleItemContent(HTShapeKeys.DUST))
        add(HTSimpleItemContent(HTShapeKeys.GEAR))
        add(HTSimpleItemContent(HTShapeKeys.INGOT))
        add(HTSimpleItemContent(HTShapeKeys.NUGGET))
        add(HTSimpleItemContent(HTShapeKeys.PLATE))
        add(HTSimpleItemContent(HTShapeKeys.ROD))
    }

    fun addGemComponents() = apply {
        add(HTSimpleItemContent(HTShapeKeys.DUST))
        add(HTSimpleItemContent(HTShapeKeys.GEAR))
        add(HTSimpleItemContent(HTShapeKeys.GEM))
        add(HTSimpleItemContent(HTShapeKeys.PLATE))
        add(HTSimpleItemContent(HTShapeKeys.ROD))
    }

    fun <T> add(content: HTMaterialContent<T>): HTMaterialContentMap = apply {
        getMap(content.objClass)[content.shapeKey] = content
    }

    inline fun <reified T> remove(shapeKey: HTShapeKey) = remove(T::class.java, shapeKey)

    fun <T> remove(clazz: Class<T>, shapeKey: HTShapeKey): HTMaterialContentMap = apply {
        getMap(clazz).remove(shapeKey)
    }

    fun <T> getContents(clazz: Class<T>): Collection<HTMaterialContent<T>> = getMap(clazz).values.filterIsInstance<HTMaterialContent<T>>()
}