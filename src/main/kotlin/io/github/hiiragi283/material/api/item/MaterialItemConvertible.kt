package io.github.hiiragi283.material.api.item

import com.google.common.collect.HashBasedTable
import com.google.common.collect.ImmutableTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("unused")
interface MaterialItemConvertible : ItemConvertible {

    val material: HTMaterial
    val shape: HTShape

    fun getPart(): HTPart = HTPart(material, shape)

    companion object {

        private val logger: Logger = LogManager.getLogger("MaterialItemConvertible")

        private val table: Table<HTMaterial, HTShape, ItemConvertible> = HashBasedTable.create()

        @JvmStatic
        fun getTable(): ImmutableTable<HTMaterial, HTShape, ItemConvertible> = ImmutableTable.copyOf(table)

        @JvmStatic
        fun getItems(): Collection<ItemConvertible> = table.values()

        @JvmStatic
        fun registerDefaultItem(part: HTPart, itemConvertible: ItemConvertible) {
            registerDefaultItem(part.material, part.shape, itemConvertible)
        }

        @JvmStatic
        fun registerDefaultItem(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
            if (hasDefaultItem(material, shape)) {
                throw IllegalStateException("")
            }
            table.put(material, shape, itemConvertible)
            logger.info("The item: $itemConvertible registered as default for material: $material, shape: $shape!")
        }

        @JvmStatic
        fun getDefaultItem(part: HTPart): ItemConvertible? = getDefaultItem(part.material, part.shape)

        @JvmStatic
        fun getDefaultItem(material: HTMaterial, shape: HTShape): ItemConvertible? = table.get(material, shape)

        @JvmStatic
        fun hasDefaultItem(part: HTPart): Boolean = table.contains(part.material, part.shape)

        @JvmStatic
        fun hasDefaultItem(material: HTMaterial, shape: HTShape): Boolean = table.contains(material, shape)

    }

}