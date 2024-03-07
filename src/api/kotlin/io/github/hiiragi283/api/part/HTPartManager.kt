package io.github.hiiragi283.api.part

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.allModsId
import io.github.hiiragi283.api.extension.nonAirOrNull
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.util.registry.Registry

class HTPartManager private constructor(builder: Builder) {
    init {
        // Register items from Vanilla's registry
        allModsId.forEach { modid ->
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getValues().forEach { shapeKey ->
                    Registry.ITEM.get(shapeKey.getIdentifier(materialKey, modid)).nonAirOrNull()?.run {
                        builder.add(materialKey, shapeKey.key, this)
                    }
                }
            }
        }
    }

    // Item -> Entry

    val itemToEntryMap: ImmutableMap<Item, Entry> = ImmutableMap.copyOf(builder.itemToEntryMap)

    fun getEntry(itemConvertible: ItemConvertible): Entry? = itemToEntryMap[itemConvertible.asItem()]

    // fun hasEntry(itemConvertible: ItemConvertible): Boolean = itemConvertible.asItem() in itemToEntryMap

    // HTPart -> Entry

    /*fun getDefaultEntry(part: HTPart): Entry? = getDefaultEntry(part.materialKey, part.shapeKey)

    fun getDefaultEntry(materialKey: HTMaterialKey, shapeKey: HTShapeKey): Entry? {
        val entries: Collection<Entry> = getEntries(materialKey, shapeKey)
        for (entry: Entry in entries) {
            val namespace: String = entry.item.id.namespace
            return when (namespace) {
                "minecraft" -> entry
                HTMaterialsAPI.MOD_ID -> entry
                else -> continue
            }
        }
        return entries.firstOrNull()
    }*/

    // HTPart -> Collection<Entry>

    val partToEntriesMap: ImmutableMultimap<HTPart, Entry> = ImmutableMultimap.copyOf(builder.partToEntriesMap)

    // fun getAllEntries(): Collection<Entry> = partToEntriesMap.values()

    // fun getEntries(materialKey: HTMaterialKey, shapeKey: HTShapeKey): Collection<Entry> = getEntries(HTPart(materialKey, shapeKey))

    // fun getEntries(part: HTPart): Collection<Entry> = partToEntriesMap.get(part)

    // fun getEntries(itemConvertible: ItemConvertible): Collection<Entry> = getEntry(itemConvertible)?.part?.let { getEntries(it) } ?: listOf()

    // fun hasEntry(materialKey: HTMaterialKey, shapeKey: HTShapeKey): Boolean = hasEntry(HTPart(materialKey, shapeKey))

    // fun hasEntry(part: HTPart): Boolean = partToEntriesMap.containsKey(part)

    data class Entry(val materialKey: HTMaterialKey, val shapeKey: HTShapeKey, val item: Item) {
        val part = HTPart(materialKey, shapeKey)
    }

    class Builder {
        internal val itemToEntryMap: MutableMap<Item, Entry> = mutableMapOf()

        internal val partToEntriesMap: Multimap<HTPart, Entry> = LinkedHashMultimap.create()

        fun add(materialKey: HTMaterialKey, shapeKey: HTShapeKey, itemConvertible: ItemConvertible) {
            val item: Item = itemConvertible.nonAirOrNull() ?: return
            val part = HTPart(materialKey, shapeKey)
            val entry = Entry(materialKey, shapeKey, item)
            itemToEntryMap[item] = entry
            partToEntriesMap.put(part, entry)
        }

        fun remove(materialKey: HTMaterialKey, shapeKey: HTShapeKey, itemConvertible: ItemConvertible) {
            val item: Item = itemConvertible.nonAirOrNull() ?: return
            val part = HTPart(materialKey, shapeKey)
            val entry = Entry(materialKey, shapeKey, item)
            if (item in itemToEntryMap && partToEntriesMap.containsKey(part)) {
                itemToEntryMap.remove(item, entry)
                partToEntriesMap.remove(part, entry)
            }
        }

        fun build() = HTPartManager(this)

        init {
            // Amethyst
            // register(HTMaterialKeys.AMETHYST, HTShapeKeys.BLOCK, Items.AMETHYST_BLOCK)
            // register(HTMaterialKeys.AMETHYST, HTShapeKeys.GEM, Items.AMETHYST_SHARD)
            // Andesite
            add(HTMaterialKeys.ANDESITE, HTShapeKeys.BLOCK, Items.ANDESITE)
            add(HTMaterialKeys.ANDESITE, HTShapeKeys.BLOCK, Items.POLISHED_ANDESITE)
            // Basalt
            add(HTMaterialKeys.BASALT, HTShapeKeys.BLOCK, Items.BASALT)
            add(HTMaterialKeys.BASALT, HTShapeKeys.BLOCK, Items.POLISHED_BASALT)
            // Blackstone
            add(HTMaterialKeys.BLACKSTONE, HTShapeKeys.BLOCK, Items.BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapeKeys.BLOCK, Items.POLISHED_BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapeKeys.BRICKS, Items.CHISELED_POLISHED_BLACKSTONE)
            add(HTMaterialKeys.BLACKSTONE, HTShapeKeys.BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS)
            add(HTMaterialKeys.BLACKSTONE, HTShapeKeys.BRICKS, Items.POLISHED_BLACKSTONE_BRICKS)
            // Brick
            add(HTMaterialKeys.BRICK, HTShapeKeys.BRICKS, Items.BRICKS)
            add(HTMaterialKeys.BRICK, HTShapeKeys.INGOT, Items.BRICK)
            // Calcite
            // add(HTMaterialKeys.CALCITE, HTShapeKeys.BLOCK, Items.CALCITE)
            // Charcoal
            add(HTMaterialKeys.CHARCOAL, HTShapeKeys.GEM, Items.CHARCOAL)
            // Clay
            add(HTMaterialKeys.CLAY, HTShapeKeys.BLOCK, Items.CLAY)
            add(HTMaterialKeys.CLAY, HTShapeKeys.GEM, Items.CLAY_BALL)
            // Coal
            add(HTMaterialKeys.COAL, HTShapeKeys.GEM, Items.COAL)
            add(HTMaterialKeys.COAL, HTShapeKeys.BLOCK, Items.COAL_BLOCK)
            // Copper
            // add(HTMaterialKeys.COPPER, HTShapeKeys.BLOCK, Items.COPPER_BLOCK)
            // add(HTMaterialKeys.COPPER, HTShapeKeys.INGOT, Items.COPPER_INGOT)
            // add(HTMaterialKeys.COPPER, HTShapeKeys.ORE, Items.COPPER_ORE)
            // add(HTMaterialKeys.COPPER, HTShapeKeys.RAW_BLOCK, Items.RAW_COPPER_BLOCK)
            // add(HTMaterialKeys.COPPER, HTShapeKeys.RAW_ORE, Items.RAW_COPPER)
            // Deepslate
            // add(HTMaterialKeys.DEEPSLATE, HTShapeKeys.BLOCK, Items.DEEPSLATE)*/
            // Diamond
            add(HTMaterialKeys.DIAMOND, HTShapeKeys.BLOCK, Items.DIAMOND_BLOCK)
            add(HTMaterialKeys.DIAMOND, HTShapeKeys.GEM, Items.DIAMOND)
            add(HTMaterialKeys.DIAMOND, HTShapeKeys.ORE, Items.DIAMOND_ORE)
            // Diorite
            add(HTMaterialKeys.DIORITE, HTShapeKeys.BLOCK, Items.DIORITE)
            add(HTMaterialKeys.DIORITE, HTShapeKeys.BLOCK, Items.POLISHED_DIORITE)
            // Dripstone
            // add(HTMaterialKeys.DRIPSTONE, HTShapeKeys.BLOCK, Items.DRIPSTONE_BLOCK)
            // Emerald
            add(HTMaterialKeys.EMERALD, HTShapeKeys.BLOCK, Items.EMERALD_BLOCK)
            add(HTMaterialKeys.EMERALD, HTShapeKeys.GEM, Items.EMERALD)
            add(HTMaterialKeys.EMERALD, HTShapeKeys.ORE, Items.EMERALD_ORE)
            // End Stone
            add(HTMaterialKeys.END_STONE, HTShapeKeys.BLOCK, Items.END_STONE)
            add(HTMaterialKeys.END_STONE, HTShapeKeys.BRICKS, Items.END_STONE_BRICKS)
            // Ender Pearl
            add(HTMaterialKeys.ENDER_PEARL, HTShapeKeys.GEM, Items.ENDER_PEARL)
            // Flint
            add(HTMaterialKeys.FLINT, HTShapeKeys.GEM, Items.FLINT)
            // Iron
            add(HTMaterialKeys.IRON, HTShapeKeys.BLOCK, Items.IRON_BLOCK)
            add(HTMaterialKeys.IRON, HTShapeKeys.INGOT, Items.IRON_INGOT)
            add(HTMaterialKeys.IRON, HTShapeKeys.NUGGET, Items.IRON_NUGGET)
            add(HTMaterialKeys.IRON, HTShapeKeys.ORE, Items.IRON_ORE)
            // add(HTMaterialKeys.IRON, HTShapeKeys.RAW_BLOCK, Items.RAW_IRON_BLOCK)
            // add(HTMaterialKeys.IRON, HTShapeKeys.RAW_ORE, Items.RAW_IRON)
            // Glass
            add(HTMaterialKeys.GLASS, HTShapeKeys.BLOCK, Items.GLASS)
            // Glowstone
            add(HTMaterialKeys.GLOWSTONE, HTShapeKeys.BLOCK, Items.GLOWSTONE)
            add(HTMaterialKeys.GLOWSTONE, HTShapeKeys.DUST, Items.GLOWSTONE_DUST)
            // Gold
            add(HTMaterialKeys.GOLD, HTShapeKeys.BLOCK, Items.GOLD_BLOCK)
            add(HTMaterialKeys.GOLD, HTShapeKeys.INGOT, Items.GOLD_INGOT)
            add(HTMaterialKeys.GOLD, HTShapeKeys.NUGGET, Items.GOLD_NUGGET)
            add(HTMaterialKeys.GOLD, HTShapeKeys.ORE, Items.GOLD_ORE)
            // add(HTMaterialKeys.GOLD, HTShapeKeys.RAW_BLOCK, Items.RAW_GOLD_BLOCK)
            // add(HTMaterialKeys.GOLD, HTShapeKeys.RAW_ORE, Items.RAW_GOLD)
            // Granite
            add(HTMaterialKeys.GRANITE, HTShapeKeys.BLOCK, Items.GRANITE)
            add(HTMaterialKeys.GRANITE, HTShapeKeys.BLOCK, Items.POLISHED_GRANITE)
            // Lapis
            add(HTMaterialKeys.LAPIS, HTShapeKeys.BLOCK, Items.LAPIS_BLOCK)
            add(HTMaterialKeys.LAPIS, HTShapeKeys.GEM, Items.LAPIS_LAZULI)
            add(HTMaterialKeys.LAPIS, HTShapeKeys.ORE, Items.LAPIS_ORE)
            // Nether Brick
            add(HTMaterialKeys.NETHER_BRICK, HTShapeKeys.BRICKS, Items.NETHER_BRICKS)
            add(HTMaterialKeys.NETHER_BRICK, HTShapeKeys.INGOT, Items.NETHER_BRICK)
            // Netherite
            add(HTMaterialKeys.NETHERITE, HTShapeKeys.BLOCK, Items.NETHERITE_BLOCK)
            add(HTMaterialKeys.NETHERITE, HTShapeKeys.INGOT, Items.NETHERITE_INGOT)
            // Netherrack
            add(HTMaterialKeys.NETHERRACK, HTShapeKeys.BLOCK, Items.NETHERRACK)
            // Obsidian
            add(HTMaterialKeys.OBSIDIAN, HTShapeKeys.BLOCK, Items.OBSIDIAN)
            // Prismarine
            // add(HTMaterialKeys.PRISMARINE, HTShapeKeys.DUST, Items.PRISMARINE_CRYSTALS)
            // add(HTMaterialKeys.PRISMARINE, HTShapeKeys.GEM, Items.PRISMARINE_SHARD)
            // Quartz
            add(HTMaterialKeys.QUARTZ, HTShapeKeys.BLOCK, Items.QUARTZ_BLOCK)
            add(HTMaterialKeys.QUARTZ, HTShapeKeys.GEM, Items.QUARTZ)
            add(HTMaterialKeys.QUARTZ, HTShapeKeys.ORE, Items.NETHER_QUARTZ_ORE)
            // Redstone
            add(HTMaterialKeys.REDSTONE, HTShapeKeys.BLOCK, Items.REDSTONE_BLOCK)
            add(HTMaterialKeys.REDSTONE, HTShapeKeys.DUST, Items.REDSTONE)
            add(HTMaterialKeys.REDSTONE, HTShapeKeys.ORE, Items.REDSTONE_ORE)
            // Stone
            add(HTMaterialKeys.STONE, HTShapeKeys.BLOCK, Items.STONE)
            add(HTMaterialKeys.STONE, HTShapeKeys.BRICKS, Items.STONE_BRICKS)
            add(HTMaterialKeys.STONE, HTShapeKeys.BRICKS, Items.MOSSY_STONE_BRICKS)
            add(HTMaterialKeys.STONE, HTShapeKeys.BRICKS, Items.CRACKED_STONE_BRICKS)
            // Tuff
            // add(HTMaterialKeys.TUFF, HTShapeKeys.BLOCK, Items.TUFF)
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
            ).forEach { add(HTMaterialKeys.WOOD, HTShapeKeys.LOG, it) }
            listOf(
                Items.OAK_WOOD,
                Items.BIRCH_WOOD,
                Items.SPRUCE_WOOD,
                Items.JUNGLE_WOOD,
                Items.ACACIA_WOOD,
                Items.DARK_OAK_WOOD,
                Items.CRIMSON_STEM,
                Items.WARPED_STEM,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapeKeys.LOG, it) }
            listOf(
                Items.OAK_PLANKS,
                Items.BIRCH_PLANKS,
                Items.SPRUCE_PLANKS,
                Items.JUNGLE_PLANKS,
                Items.ACACIA_PLANKS,
                Items.DARK_OAK_PLANKS,
                Items.CRIMSON_PLANKS,
                Items.WARPED_PLANKS,
            ).forEach { add(HTMaterialKeys.WOOD, HTShapeKeys.PLANKS, it) }
        }
    }
}
