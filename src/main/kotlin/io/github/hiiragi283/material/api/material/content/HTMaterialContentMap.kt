package io.github.hiiragi283.material.api.material.content

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.util.registry.RegistryKey

class HTMaterialContentMap private constructor(private val table: Table<HTShapeKey, RegistryKey<*>, HTMaterialContent<*>>) {

    @Suppress("UNCHECKED_CAST")
    fun <T> getContents(registryKey: RegistryKey<T>): Iterable<HTMaterialContent<T>> =
        table.column(registryKey).values as Iterable<HTMaterialContent<T>>

    //    Builder    //

    class Builder {

        private val backingTable: Table<HTShapeKey, RegistryKey<*>, HTMaterialContent<*>> =
            HashBasedTable.create()

        fun <T> add(content: HTMaterialContent<T>) {
            backingTable.put(content.shapeKey, content.registry.key, content)
        }

        fun <T> remove(shapeKey: HTShapeKey, registryKey: RegistryKey<T>) {
            backingTable.remove(shapeKey, registryKey)
        }

        internal fun build(): HTMaterialContentMap = HTMaterialContentMap(backingTable)

    }

}