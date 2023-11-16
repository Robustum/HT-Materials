package io.github.hiiragi283.material.api.part

import com.dm.earth.tags_binder.api.LoadTagsCallback
import com.google.common.collect.HashBasedTable
import com.google.common.collect.ImmutableTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.tag.TagKey
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object HTPartManager {

    private val logger: Logger = LogManager.getLogger("HTPartManager")

    //    ItemConvertible -> HTPart    //

    private val itemToPart: MutableMap<ItemConvertible, HTPart> = mutableMapOf()

    @JvmField
    val ITEM_TO_PART: Map<ItemConvertible, HTPart> = itemToPart

    //    HTMaterial, HTShape -> ItemConvertible    //

    private val partToItem: Table<HTMaterial, HTShape, ItemConvertible> = HashBasedTable.create()

    @JvmStatic
    fun getDefaultItemTable(): ImmutableTable<HTMaterial, HTShape, ItemConvertible> = ImmutableTable.copyOf(partToItem)

    //    HTMaterial, HTShape -> Collection<ItemConvertible>    //

    private val partToItems: Table<HTMaterial, HTShape, MutableSet<ItemConvertible>> = HashBasedTable.create()

    @JvmStatic
    fun getPartToItemsTable(): ImmutableTable<HTMaterial, HTShape, Collection<ItemConvertible>> =
        ImmutableTable.copyOf(partToItems)

    //    Initialization    //

    init {
        //Copper
        forceRegister(HTElementMaterials.COPPER, HTShape.BLOCK, Blocks.COPPER_BLOCK)
        forceRegister(HTElementMaterials.COPPER, HTShape.INGOT, Items.COPPER_INGOT)
        forceRegister(HTElementMaterials.COPPER, HTShape.RAW_BLOCK, Blocks.RAW_COPPER_BLOCK)
        forceRegister(HTElementMaterials.COPPER, HTShape.RAW_ORE, Items.RAW_COPPER)
        //Diamond
        forceRegister(HTVanillaMaterials.DIAMOND, HTShape.BLOCK, Blocks.DIAMOND_BLOCK)
        forceRegister(HTVanillaMaterials.DIAMOND, HTShape.GEM, Items.DIAMOND)
        //Iron
        forceRegister(HTElementMaterials.IRON, HTShape.BLOCK, Blocks.IRON_BLOCK)
        forceRegister(HTElementMaterials.IRON, HTShape.INGOT, Items.IRON_INGOT)
        forceRegister(HTElementMaterials.IRON, HTShape.NUGGET, Items.IRON_NUGGET)
        forceRegister(HTElementMaterials.IRON, HTShape.RAW_BLOCK, Blocks.RAW_IRON_BLOCK)
        forceRegister(HTElementMaterials.IRON, HTShape.RAW_ORE, Items.RAW_IRON)
        //Netherite
        forceRegister(HTVanillaMaterials.NETHERITE, HTShape.BLOCK, Blocks.NETHERITE_BLOCK)
        forceRegister(HTVanillaMaterials.NETHERITE, HTShape.INGOT, Items.NETHERITE_INGOT)
        //Gold
        forceRegister(HTElementMaterials.GOLD, HTShape.BLOCK, Blocks.GOLD_BLOCK)
        forceRegister(HTElementMaterials.GOLD, HTShape.INGOT, Items.GOLD_INGOT)
        forceRegister(HTElementMaterials.GOLD, HTShape.NUGGET, Items.GOLD_NUGGET)
        forceRegister(HTElementMaterials.GOLD, HTShape.RAW_BLOCK, Blocks.RAW_GOLD_BLOCK)
        forceRegister(HTElementMaterials.GOLD, HTShape.RAW_ORE, Items.RAW_GOLD)
    }

    //    Registration    //

    fun registerEvent() {
        LoadTagsCallback.ITEM.register { handler: LoadTagsCallback.TagHandler<Item> ->

            val table: ImmutableTable<HTMaterial, HTShape, ItemConvertible> = getDefaultItemTable()

            HTMaterial.REGISTRY.forEach { material: HTMaterial ->
                HTShape.REGISTRY.forEach { shape: HTShape ->
                    //Register CommonTag for Material Items
                    val forgeTag: TagKey<Item> = shape.getForgeTag(material)
                    val commonTag: TagKey<Item> = shape.getCommonTag(material)
                    table.get(material, shape)?.let { handler.register(commonTag, it.asItem()) }
                    //Sync ForgeTag and CommonTag
                    handler.register(forgeTag)
                    handler.register(commonTag)
                    handler.register(forgeTag, *handler.get(commonTag).toTypedArray())
                    handler.register(commonTag, *handler.get(forgeTag).toTypedArray())
                    //Register items to HTPartManager
                    handler.get(shape.getCommonTag(material)).forEach {
                        register(material, shape, it)
                    }
                }
            }
        }
    }

    private fun register(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
        val part = HTPart(material, shape)
        //ItemConvertible -> HTPart
        itemToPart.putIfAbsent(itemConvertible, part)
        //HTMaterial, HTShape -> ItemConvertible
        if (!partToItem.contains(material, shape)) {
            forceRegister(material, shape, itemConvertible)
        }
        //HTMaterial, HTShape -> Collection<ItemConvertible>
        if (!partToItems.contains(material, shape)) {
            partToItems.put(material, shape, mutableSetOf())
        }
        partToItems.get(material, shape)!!.add(itemConvertible)
        //print info
        logger.info("The Item: $itemConvertible linked to Material: $material and Shape: $shape!")
    }

    internal fun forceRegister(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
        partToItem.put(material, shape, itemConvertible)
        logger.info("The Item: $itemConvertible registered as Default Item for Material: $material and Shape: $shape!!")
    }

}