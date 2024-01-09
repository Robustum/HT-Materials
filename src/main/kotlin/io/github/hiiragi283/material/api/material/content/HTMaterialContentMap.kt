package io.github.hiiragi283.material.api.material.content

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.shape.HTShapeKey

class HTMaterialContentMap private constructor(private val table: Table<HTShapeKey, HTMaterialContent.Type, HTMaterialContent<*>>) {

    @Suppress("UNCHECKED_CAST")
    fun <T : HTMaterialContent<*>> getAs(shapeKey: HTShapeKey, type: HTMaterialContent.Type): T? =
        table.get(shapeKey, type) as? T

    @Suppress("UNCHECKED_CAST")
    fun <T : HTMaterialContent<*>> getContents(type: HTMaterialContent.Type): Iterable<T> =
        table.column(type).values as Iterable<T>

    //    Builder    //

    class Builder {

        private val backingTable: Table<HTShapeKey, HTMaterialContent.Type, HTMaterialContent<*>> =
            HashBasedTable.create()

        fun <T> add(content: HTMaterialContent<T>) {
            backingTable.put(content.shapeKey, content.getContentType(), content)
        }

        fun remove(shapeKey: HTShapeKey, type: HTMaterialContent.Type) {
            backingTable.remove(shapeKey, type)
        }

        internal fun build(): HTMaterialContentMap = HTMaterialContentMap(backingTable)

    }

}