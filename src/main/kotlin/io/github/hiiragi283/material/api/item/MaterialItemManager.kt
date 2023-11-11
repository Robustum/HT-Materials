package io.github.hiiragi283.material.api.item

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.block.Blocks
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object MaterialItemManager {

    private val logger: Logger = LogManager.getLogger("MaterialItemManager")

    private val tablePrimal: Table<HTMaterial, HTShape, Entry> = HashBasedTable.create()
    private val tableAll: Table<HTMaterial, HTShape, MutableSet<Entry>> = HashBasedTable.create()

    init {
        register(HTVanillaMaterials.GOLD, HTShape.BLOCK, Blocks.GOLD_BLOCK)
        register(HTVanillaMaterials.GOLD, HTShape.INGOT, Items.GOLD_INGOT)
        register(HTVanillaMaterials.GOLD, HTShape.NUGGET, Items.GOLD_NUGGET)
        register(HTVanillaMaterials.IRON, HTShape.BLOCK, Blocks.IRON_BLOCK)
        register(HTVanillaMaterials.IRON, HTShape.INGOT, Items.IRON_INGOT)
        register(HTVanillaMaterials.IRON, HTShape.NUGGET, Items.IRON_NUGGET)
    }

    @JvmStatic
    fun getItemPrimal(material: HTMaterial, shape: HTShape): Entry? = tablePrimal.get(material, shape)

    fun getItems(material: HTMaterial, shape: HTShape): Collection<Entry> = tableAll.get(material, shape) ?: listOf()

    @JvmStatic
    fun getItemsFromMaterial(material: HTMaterial): Collection<Entry> = tableAll.row(material).values.flatten()

    @JvmStatic
    fun getItemsFromShape(shape: HTShape): Collection<Entry> = tableAll.column(shape).values.flatten()

    @JvmStatic
    fun register(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
        val entry = Entry(material, shape, itemConvertible)
        if (!tablePrimal.contains(material, shape)) {
            tablePrimal.put(material, shape, entry)
            logger.info("The entry: $entry registered as primal entry!")
        }
        val collection: MutableSet<Entry> = tableAll[material, shape] ?: mutableSetOf()
        collection.add(entry)
        tableAll.put(material, shape, collection)
        logger.info("The entry: $entry registered!")
    }

    data class Entry(
        val material: HTMaterial,
        val shape: HTShape,
        val itemConvertible: ItemConvertible
    ) : ItemConvertible by itemConvertible

}