package io.github.hiiragi283.material.api.item

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible

interface MaterialItemConvertible : ItemConvertible {

    val material: HTMaterial

    val shape: HTShape

    companion object {

        private val table: Table<HTMaterial, HTShape, MaterialItemConvertible> = HashBasedTable.create()

        @JvmStatic
        fun getItem(material: HTMaterial, shape: HTShape): MaterialItemConvertible? = table.get(material, shape)

        @JvmStatic
        fun getItemsFromMaterial(material: HTMaterial): Collection<MaterialItemConvertible> = table.row(material).values

        @JvmStatic
        fun getItemsFromShape(shape: HTShape): Collection<MaterialItemConvertible> = table.column(shape).values

        @JvmStatic
        fun register(material: HTMaterial, shape: HTShape, itemConvertible: MaterialItemConvertible) {
            table.put(material, shape, itemConvertible)
        }

    }

}