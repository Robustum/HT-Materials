package io.github.hiiragi283.material.impl.part

import com.google.common.collect.HashMultimap
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.material.impl.HTMaterialsAPIImpl
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.registry.Registry

internal class HTPartManagerImpl(builder: Builder) : HTPartManager {
    init {
        // Register items from Vanilla's registry
        builder.loadVanillaEntries()
        HTPlatformHelper.INSTANCE.getAllModId().forEach { modid ->
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getValues().forEach { shapeKey ->
                    Registry.ITEM.get(shapeKey.getIdentifier(materialKey, modid)).run {
                        builder.add(materialKey, shapeKey.key, this)
                    }
                }
            }
        }
        // Register event
        ServerWorldEvents.LOAD.register(::onWorldLoad)
    }

    override val itemToPartMap: ImmutableMap<Item, HTPart> = ImmutableMap.copyOf(builder.itemToPartMap)

    override val partToItemsMap: ImmutableMultimap<HTPart, Item> = ImmutableMultimap.copyOf(builder.partToItemsMap)

    class Builder : HTPartManager.Builder {
        val itemToPartMap: MutableMap<Item, HTPart> = mutableMapOf()

        val partToItemsMap: Multimap<HTPart, Item> = HashMultimap.create()

        override fun add(materialKey: HTMaterialKey, shapeKey: HTShapeKey, itemConvertible: ItemConvertible) {
            val item: Item = itemConvertible.asItem().takeUnless { it == Items.AIR } ?: return
            val part = HTPart(materialKey, shapeKey)
            itemToPartMap[item] = part
            partToItemsMap.put(part, item)
        }

        fun loadVanillaEntries() {
            // Amethyst
            // register(HTMaterialKeys.AMETHYST, HTShapeKeys.BLOCK, Items.AMETHYST_BLOCK)
            // register(HTMaterialKeys.AMETHYST, HTShapeKeys.GEM, Items.AMETHYST_SHARD)
            // Andesite
            add(HTMaterialKeys.ANDESITE, HTShapeKeys.BLOCK, Items.ANDESITE)
            add(HTMaterialKeys.ANDESITE, HTShapeKeys.BLOCK, Items.POLISHED_ANDESITE)
            // Basalt
            add(HTMaterialKeys.BASALT, HTShapeKeys.BLOCK, Items.BASALT)
            add(HTMaterialKeys.BASALT, HTShapeKeys.BLOCK, Items.POLISHED_BASALT)
            // Brick
            add(HTMaterialKeys.BRICK, HTShapeKeys.BLOCK, Items.BRICKS)
            add(HTMaterialKeys.BRICK, HTShapeKeys.GEM, Items.BRICK)
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
            add(HTMaterialKeys.NETHER_BRICK, HTShapeKeys.BLOCK, Items.NETHER_BRICKS)
            add(HTMaterialKeys.NETHER_BRICK, HTShapeKeys.GEM, Items.NETHER_BRICK)
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
            // Tuff
            // add(HTMaterialKeys.TUFF, HTShapeKeys.BLOCK, Items.TUFF)
            // Wood
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.OAK_PLANKS)
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.BIRCH_PLANKS)
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.SPRUCE_PLANKS)
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.JUNGLE_PLANKS)
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.ACACIA_PLANKS)
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.DARK_OAK_PLANKS)
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.CRIMSON_HYPHAE)
            add(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.WARPED_HYPHAE)
        }
    }
}

@Suppress("UNUSED_PARAMETER")
private fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
    HTMaterialsAPIImpl.partManager = HTPartManagerImpl.Builder().apply {
        // Register items from Vanilla's registry
        loadVanillaEntries()
        // Register items from part tag
        HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
            HTMaterialsAPI.INSTANCE.shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                HTPart(materialKey, shapeKey).getPartTag().values().forEach { item ->
                    add(materialKey, shapeKey, item)
                }
            }
        }
    }.let(::HTPartManagerImpl)
}
