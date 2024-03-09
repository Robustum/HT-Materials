package io.github.hiiragi283.api.part

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.id
import io.github.hiiragi283.api.extension.nonAirOrNull
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapes
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.tag.Tag

interface HTPartManager {
    // Item -> HTPart

    val itemToEntryMap: Map<Item, HTPart>

    operator fun get(itemConvertible: ItemConvertible): HTPart? = itemToEntryMap[itemConvertible.asItem()]

    operator fun contains(itemConvertible: ItemConvertible): Boolean = itemConvertible.asItem() in itemToEntryMap

    // HTPart -> Item

    fun getDefaultItem(part: HTPart): Item? = getDefaultItem(part.materialKey, part.shape)

    fun getDefaultItem(materialKey: HTMaterialKey, shape: HTShape): Item? {
        val items: Collection<Item> = get(materialKey, shape)
        for (item: Item in items) {
            val namespace: String = item.id.namespace
            return when (namespace) {
                "minecraft" -> item
                HTMaterialsAPI.MOD_ID -> item
                else -> continue
            }
        }
        return items.firstOrNull()
    }

    // HTPart -> Collection<Item>

    val partToEntriesMap: Map<HTPart, Set<Item>>

    val allItems: Set<Item>
        get() = partToEntriesMap.values.flatten().toSet()

    operator fun get(materialKey: HTMaterialKey, shape: HTShape): Set<Item> = get(HTPart(materialKey, shape))

    operator fun get(part: HTPart): Set<Item> = partToEntriesMap.getOrDefault(part, setOf())

    fun convertItems(itemConvertible: ItemConvertible): Set<Item> = get(itemConvertible)?.let(::get) ?: setOf()

    fun contains(materialKey: HTMaterialKey, shape: HTShape): Boolean = contains(HTPart(materialKey, shape))

    operator fun contains(part: HTPart): Boolean = partToEntriesMap.containsKey(part)

    //    Builder    //

    class Builder {
        val registeredItemMap: Map<Item, HTPart>
            get() = registeredItemMap1
        private val registeredItemMap1: MutableMap<Item, HTPart> = hashMapOf()

        val registeredTagMap: Map<Tag<Item>, HTPart>
            get() = registeredTagMap1
        private val registeredTagMap1: MutableMap<Tag<Item>, HTPart> = hashMapOf()

        fun add(materialKey: HTMaterialKey, shape: HTShape, item: ItemConvertible) {
            add(HTPart(materialKey, shape), item)
        }

        fun add(part: HTPart, item: ItemConvertible) {
            item.nonAirOrNull?.run { registeredItemMap1[this] = part }
        }

        fun add(materialKey: HTMaterialKey, shape: HTShape, tag: Tag<Item>) {
            add(HTPart(materialKey, shape), tag)
        }

        operator fun set(part: HTPart, item: ItemConvertible) {
            add(part, item)
        }

        fun add(part: HTPart, tag: Tag<Item>) {
            registeredTagMap1[tag] = part
        }

        fun remove(item: Item) {
            item.nonAirOrNull?.run {
                registeredItemMap1.remove(this)
            }
        }

        fun remove(tag: Tag<Item>) {
            registeredTagMap1.remove(tag)
        }

        operator fun minusAssign(item: Item) {
            remove(item)
        }

        operator fun minusAssign(tag: Tag<Item>) {
            remove(tag)
        }

        init {
            // Amethyst
            // add(HTMaterialKeys.AMETHYST, HTShapes.BLOCK, Items.AMETHYST_BLOCK)
            // add(HTMaterialKeys.AMETHYST, HTShapes.GEM, Items.AMETHYST_SHARD)
            // Andesite
            add(HTMaterialKeys.ANDESITE, HTShapes.BLOCK, Items.ANDESITE)
            add(HTMaterialKeys.ANDESITE, HTShapes.BLOCK, Items.POLISHED_ANDESITE)
            // Basalt
            add(HTMaterialKeys.BASALT, HTShapes.BLOCK, Items.BASALT)
            add(HTMaterialKeys.BASALT, HTShapes.BLOCK, Items.POLISHED_BASALT)
            // add(HTMaterialKeys.BASALT, HTShapes.BLOCK, Items.SMOOTH_BASALT)
            // Blackstone
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BLOCK, Items.BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BLOCK, Items.POLISHED_BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS, Items.CHISELED_POLISHED_BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS)
            add(HTMaterialKeys.BLACKSTONE, HTShapes.BRICKS, Items.POLISHED_BLACKSTONE_BRICKS)
            // Brick
            add(HTMaterialKeys.BRICK, HTShapes.BRICKS, Items.BRICKS)
            add(HTMaterialKeys.BRICK, HTShapes.INGOT, Items.BRICK)
            // Calcite
            // add(HTMaterialKeys.CALCITE, HTShapes.BLOCK, Items.CALCITE)
            // Charcoal
            add(HTMaterialKeys.CHARCOAL, HTShapes.GEM, Items.CHARCOAL)
            // Clay
            add(HTMaterialKeys.CLAY, HTShapes.BLOCK, Items.CLAY)
            add(HTMaterialKeys.CLAY, HTShapes.GEM, Items.CLAY_BALL)
            // Coal
            add(HTMaterialKeys.COAL, HTShapes.GEM, Items.COAL)
            add(HTMaterialKeys.COAL, HTShapes.BLOCK, Items.COAL_BLOCK)
            // Copper
            // add(HTMaterialKeys.COPPER, HTShapes.BLOCK, Items.COPPER_BLOCK)
            // add(HTMaterialKeys.COPPER, HTShapes.INGOT, Items.COPPER_INGOT)
            // add(HTMaterialKeys.COPPER, HTShapes.ORE, Items.COPPER_ORE)
            // add(HTMaterialKeys.COPPER, HTShapes.RAW_BLOCK, Items.RAW_COPPER_BLOCK)
            // add(HTMaterialKeys.COPPER, HTShapes.RAW_ORE, Items.RAW_COPPER)
            // Deepslate
            // add(HTMaterialKeys.DEEPSLATE, HTShapes.BLOCK, Items.CHISELED_DEEPSLATE)
            // add(HTMaterialKeys.DEEPSLATE, HTShapes.BLOCK, Items.CRACKED_DEEPSLATE_TILES)
            // add(HTMaterialKeys.DEEPSLATE, HTShapes.BLOCK, Items.DEEPSLATE)
            // add(HTMaterialKeys.DEEPSLATE, HTShapes.BLOCK, Items.DEEPSLATE_TILES)
            // add(HTMaterialKeys.DEEPSLATE, HTShapes.BRICKS, Items.CRACKED_DEEPSLATE_BRICKS)
            // add(HTMaterialKeys.DEEPSLATE, HTShapes.BRICKS, Items.DEEPSLATE_BRICKS)
            // Diamond
            add(HTMaterialKeys.DIAMOND, HTShapes.BLOCK, Items.DIAMOND_BLOCK)
            add(HTMaterialKeys.DIAMOND, HTShapes.GEM, Items.DIAMOND)
            add(HTMaterialKeys.DIAMOND, HTShapes.ORE, Items.DIAMOND_ORE)
            // Diorite
            add(HTMaterialKeys.DIORITE, HTShapes.BLOCK, Items.DIORITE)
            add(HTMaterialKeys.DIORITE, HTShapes.BLOCK, Items.POLISHED_DIORITE)
            // Emerald
            add(HTMaterialKeys.EMERALD, HTShapes.BLOCK, Items.EMERALD_BLOCK)
            add(HTMaterialKeys.EMERALD, HTShapes.GEM, Items.EMERALD)
            add(HTMaterialKeys.EMERALD, HTShapes.ORE, Items.EMERALD_ORE)
            // End Stone
            add(HTMaterialKeys.END_STONE, HTShapes.BLOCK, Items.END_STONE)
            add(HTMaterialKeys.END_STONE, HTShapes.BRICKS, Items.END_STONE_BRICKS)
            // Ender Pearl
            add(HTMaterialKeys.ENDER_PEARL, HTShapes.GEM, Items.ENDER_PEARL)
            // Flint
            add(HTMaterialKeys.FLINT, HTShapes.GEM, Items.FLINT)
            // Iron
            add(HTMaterialKeys.IRON, HTShapes.BLOCK, Items.IRON_BLOCK)
            add(HTMaterialKeys.IRON, HTShapes.INGOT, Items.IRON_INGOT)
            add(HTMaterialKeys.IRON, HTShapes.NUGGET, Items.IRON_NUGGET)
            add(HTMaterialKeys.IRON, HTShapes.ORE, Items.IRON_ORE)
            // add(HTMaterialKeys.IRON, HTShapes.RAW_BLOCK, Items.RAW_IRON_BLOCK)
            // add(HTMaterialKeys.IRON, HTShapes.RAW_ORE, Items.RAW_IRON)
            // Glass
            add(HTMaterialKeys.GLASS, HTShapes.BLOCK, Items.GLASS)
            // Glowstone
            add(HTMaterialKeys.GLOWSTONE, HTShapes.BLOCK, Items.GLOWSTONE)
            add(HTMaterialKeys.GLOWSTONE, HTShapes.DUST, Items.GLOWSTONE_DUST)
            // Gold
            add(HTMaterialKeys.GOLD, HTShapes.BLOCK, Items.GOLD_BLOCK)
            add(HTMaterialKeys.GOLD, HTShapes.INGOT, Items.GOLD_INGOT)
            add(HTMaterialKeys.GOLD, HTShapes.NUGGET, Items.GOLD_NUGGET)
            add(HTMaterialKeys.GOLD, HTShapes.ORE, Items.GOLD_ORE)
            // add(HTMaterialKeys.GOLD, HTShapes.RAW_BLOCK, Items.RAW_GOLD_BLOCK)
            // add(HTMaterialKeys.GOLD, HTShapes.RAW_ORE, Items.RAW_GOLD)
            // Granite
            add(HTMaterialKeys.GRANITE, HTShapes.BLOCK, Items.GRANITE)
            add(HTMaterialKeys.GRANITE, HTShapes.BLOCK, Items.POLISHED_GRANITE)
            // Lapis
            add(HTMaterialKeys.LAPIS, HTShapes.BLOCK, Items.LAPIS_BLOCK)
            add(HTMaterialKeys.LAPIS, HTShapes.GEM, Items.LAPIS_LAZULI)
            add(HTMaterialKeys.LAPIS, HTShapes.ORE, Items.LAPIS_ORE)
            // Nether Brick
            add(HTMaterialKeys.NETHER_BRICK, HTShapes.BRICKS, Items.NETHER_BRICKS)
            add(HTMaterialKeys.NETHER_BRICK, HTShapes.INGOT, Items.NETHER_BRICK)
            // Netherite
            add(HTMaterialKeys.NETHERITE, HTShapes.BLOCK, Items.NETHERITE_BLOCK)
            add(HTMaterialKeys.NETHERITE, HTShapes.INGOT, Items.NETHERITE_INGOT)
            // Netherrack
            add(HTMaterialKeys.NETHERRACK, HTShapes.BLOCK, Items.NETHERRACK)
            // Obsidian
            add(HTMaterialKeys.OBSIDIAN, HTShapes.BLOCK, Items.OBSIDIAN)
            // Quartz
            add(HTMaterialKeys.QUARTZ, HTShapes.BLOCK, Items.QUARTZ_BLOCK)
            add(HTMaterialKeys.QUARTZ, HTShapes.BLOCK, Items.SMOOTH_QUARTZ)
            add(HTMaterialKeys.QUARTZ, HTShapes.GEM, Items.QUARTZ)
            add(HTMaterialKeys.QUARTZ, HTShapes.ORE, Items.NETHER_QUARTZ_ORE)
            // Redstone
            add(HTMaterialKeys.REDSTONE, HTShapes.BLOCK, Items.REDSTONE_BLOCK)
            add(HTMaterialKeys.REDSTONE, HTShapes.DUST, Items.REDSTONE)
            add(HTMaterialKeys.REDSTONE, HTShapes.ORE, Items.REDSTONE_ORE)
            // Stone
            add(HTMaterialKeys.STONE, HTShapes.BLOCK, Items.STONE)
            add(HTMaterialKeys.STONE, HTShapes.BRICKS, Items.STONE_BRICKS)
            add(HTMaterialKeys.STONE, HTShapes.BRICKS, Items.MOSSY_STONE_BRICKS)
            add(HTMaterialKeys.STONE, HTShapes.BRICKS, Items.CRACKED_STONE_BRICKS)
            // Tuff
            // add(HTMaterialKeys.TUFF, HTShapes.BLOCK, Items.TUFF)
            // Wood
            listOf(
                Items.OAK_LOG,
                Items.BIRCH_LOG,
                Items.SPRUCE_LOG,
                Items.JUNGLE_LOG,
                Items.ACACIA_LOG,
                Items.DARK_OAK_LOG,
                Items.CRIMSON_HYPHAE,
                Items.WARPED_HYPHAE,
                // Items.MANGROVE_LOG,
                // Items.CHERRY_LOG,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapes.LOG, it) }
            listOf(
                Items.OAK_WOOD,
                Items.BIRCH_WOOD,
                Items.SPRUCE_WOOD,
                Items.JUNGLE_WOOD,
                Items.ACACIA_WOOD,
                Items.DARK_OAK_WOOD,
                Items.CRIMSON_STEM,
                Items.WARPED_STEM,
                // Items.MANGROVE_WOOD,
                // Items.CHERRY_WOOD,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapes.LOG, it) }
            listOf(
                Items.OAK_PLANKS,
                Items.BIRCH_PLANKS,
                Items.SPRUCE_PLANKS,
                Items.JUNGLE_PLANKS,
                Items.ACACIA_PLANKS,
                Items.DARK_OAK_PLANKS,
                Items.CRIMSON_PLANKS,
                Items.WARPED_PLANKS,
                // Items.MANGROVE_PLANKS,
                // Items.CHERRY_PLANKS,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapes.PLANKS, it) }
        }
    }
}
