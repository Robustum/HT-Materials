package io.github.hiiragi283.api.material.content

import com.google.common.collect.HashBasedTable
import com.google.common.collect.ImmutableTable
import com.google.common.collect.Table
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys

class HTMaterialContentMap private constructor(builder: Builder) {
    private val table: ImmutableTable<HTMaterialContent.Type, HTShapeKey, HTMaterialContent> = ImmutableTable.copyOf(builder.table)

    fun getContents(type: HTMaterialContent.Type) = table.values().filter { it.type == type }

    class Builder {
        internal val table: Table<HTMaterialContent.Type, HTShapeKey, HTMaterialContent> = HashBasedTable.create()

        private fun getMap(type: HTMaterialContent.Type): MutableMap<HTShapeKey, HTMaterialContent> = table.row(type)

        fun addMetalComponents(): Builder = apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.INGOT))
            add(HTSimpleItemContent(HTShapeKeys.NUGGET))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }

        fun addGemComponents(): Builder = apply {
            add(HTSimpleItemContent(HTShapeKeys.DUST))
            add(HTSimpleItemContent(HTShapeKeys.GEAR))
            add(HTSimpleItemContent(HTShapeKeys.GEM))
            add(HTSimpleItemContent(HTShapeKeys.PLATE))
            add(HTSimpleItemContent(HTShapeKeys.ROD))
        }

        fun add(content: HTMaterialContent): Builder = apply {
            getMap(content.type)[content.shapeKey] = content
        }

        fun remove(type: HTMaterialContent.Type, shapeKey: HTShapeKey): Builder = apply {
            getMap(type).remove(shapeKey)
        }

        fun build(): HTMaterialContentMap = HTMaterialContentMap(this)
    }
}