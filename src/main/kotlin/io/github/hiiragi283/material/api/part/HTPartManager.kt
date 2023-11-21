package io.github.hiiragi283.material.api.part

import com.dm.earth.tags_binder.api.LoadTagsCallback
import com.google.common.collect.HashBasedTable
import com.google.common.collect.ImmutableTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTTagManager
import io.github.hiiragi283.material.common.util.*
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object HTPartManager {

    private val logger: Logger = LogManager.getLogger("HTPartManager")

    //    Item -> HTPart    //

    private val itemToPart: MutableMap<Item, HTPart> = mutableMapOf()

    @JvmField
    val ITEM_TO_PART: Map<Item, HTPart> = itemToPart

    @JvmStatic
    fun getPart(itemConvertible: ItemConvertible): HTPart? = itemToPart[itemConvertible.asItem()]

    @JvmStatic
    fun hasPart(itemConvertible: ItemConvertible): Boolean = itemConvertible.asItem() in itemToPart

    //    HTMaterial, HTShape -> Item    //

    private val partToItem: Table<HTMaterial, HTShape, Item> = HashBasedTable.create()

    @JvmStatic
    fun getDefaultItemTable(): ImmutableTable<HTMaterial, HTShape, Item> = ImmutableTable.copyOf(partToItem)

    @JvmStatic
    fun getDefaultItem(material: HTMaterial, shape: HTShape): Item? = partToItem.get(material, shape)

    @JvmStatic
    fun hasDefaultItem(material: HTMaterial, shape: HTShape): Boolean = partToItem.contains(material, shape)

    //    HTMaterial, HTShape -> Collection<Item>    //

    private val partToItems: Table<HTMaterial, HTShape, MutableSet<Item>> = HashBasedTable.create()

    @JvmStatic
    fun getPartToItemsTable(): ImmutableTable<HTMaterial, HTShape, Collection<Item>> =
        ImmutableTable.copyOf(partToItems)

    @JvmStatic
    fun getItems(material: HTMaterial, shape: HTShape): Collection<Item> = partToItems.get(material, shape) ?: setOf()

    //    Initialization    //

    init {
        //Amethyst
        forceRegister(HTVanillaMaterials.AMETHYST, HTShape.BLOCK, Blocks.AMETHYST_BLOCK)
        forceRegister(HTVanillaMaterials.AMETHYST, HTShape.GEM, Items.AMETHYST_SHARD)
        //Andesite
        forceRegister(HTVanillaMaterials.ANDESITE, HTShape.BLOCK, Blocks.ANDESITE)
        //Basalt
        forceRegister(HTVanillaMaterials.BASALT, HTShape.BLOCK, Blocks.BASALT)
        //Brick
        forceRegister(HTVanillaMaterials.BRICK, HTShape.BLOCK, Blocks.BRICKS)
        forceRegister(HTVanillaMaterials.BRICK, HTShape.GEM, Items.BRICK)
        //Calcite
        forceRegister(HTVanillaMaterials.CALCITE, HTShape.BLOCK, Blocks.CALCITE)
        //Charcoal
        forceRegister(HTVanillaMaterials.CHARCOAL, HTShape.GEM, Items.CHARCOAL)
        //Clay
        forceRegister(HTVanillaMaterials.CLAY, HTShape.BLOCK, Items.CLAY)
        forceRegister(HTVanillaMaterials.CLAY, HTShape.GEM, Items.CLAY_BALL)
        //Copper
        forceRegister(HTElementMaterials.COPPER, HTShape.BLOCK, Blocks.COPPER_BLOCK)
        forceRegister(HTElementMaterials.COPPER, HTShape.INGOT, Items.COPPER_INGOT)
        forceRegister(HTElementMaterials.COPPER, HTShape.ORE, Blocks.COPPER_ORE)
        forceRegister(HTElementMaterials.COPPER, HTShape.RAW_BLOCK, Blocks.RAW_COPPER_BLOCK)
        forceRegister(HTElementMaterials.COPPER, HTShape.RAW_ORE, Items.RAW_COPPER)
        //Deepslate
        forceRegister(HTVanillaMaterials.DEEPSLATE, HTShape.BLOCK, Blocks.DEEPSLATE)
        //Diamond
        forceRegister(HTVanillaMaterials.DIAMOND, HTShape.BLOCK, Blocks.DIAMOND_BLOCK)
        forceRegister(HTVanillaMaterials.DIAMOND, HTShape.GEM, Items.DIAMOND)
        forceRegister(HTVanillaMaterials.DIAMOND, HTShape.ORE, Blocks.DIAMOND_ORE)
        //Diorite
        forceRegister(HTVanillaMaterials.DIORITE, HTShape.BLOCK, Blocks.DIORITE)
        //Dripstone
        forceRegister(HTVanillaMaterials.DRIPSTONE, HTShape.BLOCK, Blocks.DRIPSTONE_BLOCK)
        //End Stone
        forceRegister(HTVanillaMaterials.END_STONE, HTShape.BLOCK, Blocks.END_STONE)
        //Ender Pearl
        forceRegister(HTVanillaMaterials.ENDER_PEARL, HTShape.GEM, Items.ENDER_PEARL)
        //Iron
        forceRegister(HTElementMaterials.IRON, HTShape.BLOCK, Blocks.IRON_BLOCK)
        forceRegister(HTElementMaterials.IRON, HTShape.INGOT, Items.IRON_INGOT)
        forceRegister(HTElementMaterials.IRON, HTShape.NUGGET, Items.IRON_NUGGET)
        forceRegister(HTElementMaterials.IRON, HTShape.ORE, Blocks.IRON_ORE)
        forceRegister(HTElementMaterials.IRON, HTShape.RAW_BLOCK, Blocks.RAW_IRON_BLOCK)
        forceRegister(HTElementMaterials.IRON, HTShape.RAW_ORE, Items.RAW_IRON)
        //Glass
        forceRegister(HTVanillaMaterials.GLASS, HTShape.BLOCK, Blocks.GLASS)
        //Glowstone
        forceRegister(HTVanillaMaterials.GLOWSTONE, HTShape.BLOCK, Blocks.GLOWSTONE)
        forceRegister(HTVanillaMaterials.GLOWSTONE, HTShape.DUST, Items.GLOWSTONE_DUST)
        //Gold
        forceRegister(HTElementMaterials.GOLD, HTShape.BLOCK, Blocks.GOLD_BLOCK)
        forceRegister(HTElementMaterials.GOLD, HTShape.INGOT, Items.GOLD_INGOT)
        forceRegister(HTElementMaterials.GOLD, HTShape.NUGGET, Items.GOLD_NUGGET)
        forceRegister(HTElementMaterials.GOLD, HTShape.ORE, Blocks.GOLD_ORE)
        forceRegister(HTElementMaterials.GOLD, HTShape.RAW_BLOCK, Blocks.RAW_GOLD_BLOCK)
        forceRegister(HTElementMaterials.GOLD, HTShape.RAW_ORE, Items.RAW_GOLD)
        //Granite
        forceRegister(HTVanillaMaterials.GRANITE, HTShape.BLOCK, Blocks.GRANITE)
        //Lapis
        forceRegister(HTVanillaMaterials.LAPIS, HTShape.BLOCK, Blocks.LAPIS_BLOCK)
        forceRegister(HTVanillaMaterials.LAPIS, HTShape.GEM, Items.LAPIS_LAZULI)
        forceRegister(HTVanillaMaterials.LAPIS, HTShape.ORE, Blocks.LAPIS_ORE)
        //Lava
        forceRegister(HTVanillaMaterials.LAVA, HTShape.BUCKET, Items.LAVA_BUCKET)
        //Nether Brick
        forceRegister(HTVanillaMaterials.NETHER_BRICK, HTShape.BLOCK, Blocks.NETHER_BRICKS)
        forceRegister(HTVanillaMaterials.NETHER_BRICK, HTShape.GEM, Items.NETHER_BRICK)
        //Netherite
        forceRegister(HTVanillaMaterials.NETHERITE, HTShape.BLOCK, Blocks.NETHERITE_BLOCK)
        forceRegister(HTVanillaMaterials.NETHERITE, HTShape.INGOT, Items.NETHERITE_INGOT)
        //Netherrack
        forceRegister(HTVanillaMaterials.NETHERRACK, HTShape.BLOCK, Blocks.NETHERRACK)
        //Obsidian
        forceRegister(HTVanillaMaterials.OBSIDIAN, HTShape.BLOCK, Blocks.OBSIDIAN)
        //Prismarine
        forceRegister(HTVanillaMaterials.PRISMARINE, HTShape.DUST, Items.PRISMARINE_CRYSTALS)
        forceRegister(HTVanillaMaterials.PRISMARINE, HTShape.GEM, Items.PRISMARINE_SHARD)
        //Quartz
        forceRegister(HTVanillaMaterials.QUARTZ, HTShape.BLOCK, Blocks.QUARTZ_BLOCK)
        forceRegister(HTVanillaMaterials.QUARTZ, HTShape.GEM, Items.QUARTZ)
        forceRegister(HTVanillaMaterials.QUARTZ, HTShape.ORE, Blocks.NETHER_QUARTZ_ORE)
        //Redstone
        forceRegister(HTVanillaMaterials.REDSTONE, HTShape.BLOCK, Blocks.REDSTONE_BLOCK)
        forceRegister(HTVanillaMaterials.REDSTONE, HTShape.DUST, Items.REDSTONE)
        forceRegister(HTVanillaMaterials.REDSTONE, HTShape.ORE, Blocks.REDSTONE_ORE)
        //Stone
        forceRegister(HTVanillaMaterials.STONE, HTShape.BLOCK, Blocks.STONE)
        //Tuff
        forceRegister(HTVanillaMaterials.TUFF, HTShape.BLOCK, Blocks.TUFF)
        //Water
        forceRegister(HTVanillaMaterials.WATER, HTShape.BUCKET, Items.WATER_BUCKET)
        //Wood
        forceRegister(HTVanillaMaterials.WOOD, HTShape.BLOCK, Blocks.OAK_PLANKS)
    }

    //    Registration    //

    fun registerEvent() {

        partToItem.forEach { (material: HTMaterial, shape: HTShape, item: ItemConvertible) ->
            HTTagManager.registerItemTags(shape.getForgeTag(material), item.asItem())
            HTTagManager.registerItemTags(shape.getCommonTag(material), item.asItem())
        }

        LoadTagsCallback.ITEM.register { handler: LoadTagsCallback.TagHandler<Item> ->

            HTMaterial.REGISTRY.forEach { material: HTMaterial ->
                HTShape.REGISTRY.forEach { shape: HTShape ->
                    //Register ForgeTag for Material Items
                    val forgeTag: TagKey<Item> = shape.getForgeTag(material)
                    if (forgeTag in handler.keys) {
                        handler.get(forgeTag).forEach { register(material, shape, it) }
                    }
                    //Register CommonTag for Material Items
                    val commonTag: TagKey<Item> = shape.getCommonTag(material)
                    if (commonTag in handler.keys) {
                        handler.get(commonTag).forEach { register(material, shape, it) }
                    }
                }
            }
        }
    }

    private fun checkItemNotAir(itemConvertible: ItemConvertible): Item = itemConvertible.asItem().also { item ->
        check(!item.isAir()) { "The Entry: $itemConvertible has no valid Item!" }
    }

    @JvmStatic
    fun register(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
        //Check if the itemConvertible has non-air item
        val item: Item = checkItemNotAir(itemConvertible)
        //Register Tag
        HTTagManager.registerItemTags(shape.getForgeTag(material), item)
        HTTagManager.registerItemTags(shape.getCommonTag(material), item)
        //ItemConvertible -> HTPart
        itemToPart.putIfAbsent(item, HTPart(material, shape))
        //HTMaterial, HTShape -> ItemConvertible
        if (!partToItem.contains(material, shape)) {
            partToItem.put(material, shape, item)
            logger.info("The Item: ${Registry.ITEM.getId(item)} registered as Default Item for Material: $material and Shape: $shape!!")
        }
        //HTMaterial, HTShape -> Collection<ItemConvertible>
        if (!partToItems.contains(material, shape)) {
            partToItems.put(material, shape, mutableSetOf())
        }
        partToItems.get(material, shape)!!.add(item)
        //print info
        logger.info("The Item: ${Registry.ITEM.getId(item)} linked to Material: $material and Shape: $shape!")
    }

    internal fun forceRegister(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
        //Check if the itemConvertible has non-air item
        val item: Item = checkItemNotAir(itemConvertible)
        //Register Tag
        HTTagManager.registerItemTags(shape.getForgeTag(material), item)
        HTTagManager.registerItemTags(shape.getCommonTag(material), item)
        //ItemConvertible -> HTPart
        itemToPart.putIfAbsent(item, HTPart(material, shape))
        //HTMaterial, HTShape -> ItemConvertible
        partToItem.put(material, shape, item)
        logger.info("The Item: ${Registry.ITEM.getId(item)} registered as Default Item for Material: $material and Shape: $shape!!")
        //HTMaterial, HTShape -> Collection<ItemConvertible>
        if (!partToItems.contains(material, shape)) {
            partToItems.put(material, shape, mutableSetOf())
        }
        partToItems.get(material, shape)!!.add(item)
        //print info
        logger.info("The Item: ${Registry.ITEM.getId(item)} linked to Material: $material and Shape: $shape!")
    }

}