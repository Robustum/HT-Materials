package io.github.hiiragi283.material.impl

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.collection.DefaultedTable
import io.github.hiiragi283.api.util.getAllModId
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.registry.Registry

internal class HTPartManagerImpl(registry: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) :
    HTPartManager {
    override val itemToPartMap: MutableMap<Item, HTPart> = mutableMapOf()

    override val partToItemsMap: Multimap<HTPart, Item> = HashMultimap.create()

    init {
        // Register items from Vanilla's registry
        loadVanillaEntries()
        getAllModId().forEach { modid ->
            HTMaterialsAPI.getInstance().materialRegistry().getKeys().forEach { materialKey ->
                HTMaterialsAPI.getInstance().shapeRegistry().getValues().forEach { shapeKey ->
                    Registry.ITEM.get(shapeKey.getIdentifier(materialKey, modid)).run {
                        register(materialKey, shapeKey.key, this)
                    }
                }
            }
        }
        // Register items from addons
        registry.forEach { materialKey, shapeKey, items ->
            items.forEach { item ->
                register(materialKey, shapeKey, item.asItem())
            }
        }
        // Register event
        ServerWorldEvents.LOAD.register(::onWorldLoad)
    }

    private fun register(materialKey: HTMaterialKey, shapeKey: HTShapeKey, item: Item) {
        if (item == Items.AIR) return
        itemToPartMap[item] = HTPart(materialKey, shapeKey)
        partToItemsMap.put(HTPart(materialKey, shapeKey), item)
    }

    private fun loadVanillaEntries() {
        // Amethyst
        // register(HTMaterialKeys.AMETHYST, HTShapeKeys.BLOCK, Items.AMETHYST_BLOCK)
        // register(HTMaterialKeys.AMETHYST, HTShapeKeys.GEM, Items.AMETHYST_SHARD)
        // Andesite
        register(HTMaterialKeys.ANDESITE, HTShapeKeys.BLOCK, Items.ANDESITE)
        register(HTMaterialKeys.ANDESITE, HTShapeKeys.BLOCK, Items.POLISHED_ANDESITE)
        // Basalt
        register(HTMaterialKeys.BASALT, HTShapeKeys.BLOCK, Items.BASALT)
        register(HTMaterialKeys.BASALT, HTShapeKeys.BLOCK, Items.POLISHED_BASALT)
        // Brick
        register(HTMaterialKeys.BRICK, HTShapeKeys.BLOCK, Items.BRICKS)
        register(HTMaterialKeys.BRICK, HTShapeKeys.GEM, Items.BRICK)
        // Calcite
        // register(HTMaterialKeys.CALCITE, HTShapeKeys.BLOCK, Items.CALCITE)
        // Charcoal
        register(HTMaterialKeys.CHARCOAL, HTShapeKeys.GEM, Items.CHARCOAL)
        // Clay
        register(HTMaterialKeys.CLAY, HTShapeKeys.BLOCK, Items.CLAY)
        register(HTMaterialKeys.CLAY, HTShapeKeys.GEM, Items.CLAY_BALL)
        // Coal
        register(HTMaterialKeys.COAL, HTShapeKeys.GEM, Items.COAL)
        register(HTMaterialKeys.COAL, HTShapeKeys.BLOCK, Items.COAL_BLOCK)
        // Copper
        // register(HTMaterialKeys.COPPER, HTShapeKeys.BLOCK, Items.COPPER_BLOCK)
        // register(HTMaterialKeys.COPPER, HTShapeKeys.INGOT, Items.COPPER_INGOT)
        // register(HTMaterialKeys.COPPER, HTShapeKeys.ORE, Items.COPPER_ORE)
        // register(HTMaterialKeys.COPPER, HTShapeKeys.RAW_BLOCK, Items.RAW_COPPER_BLOCK)
        // register(HTMaterialKeys.COPPER, HTShapeKeys.RAW_ORE, Items.RAW_COPPER)
        // Deepslate
        // register(HTMaterialKeys.DEEPSLATE, HTShapeKeys.BLOCK, Items.DEEPSLATE)*/
        // Diamond
        register(HTMaterialKeys.DIAMOND, HTShapeKeys.BLOCK, Items.DIAMOND_BLOCK)
        register(HTMaterialKeys.DIAMOND, HTShapeKeys.GEM, Items.DIAMOND)
        register(HTMaterialKeys.DIAMOND, HTShapeKeys.ORE, Items.DIAMOND_ORE)
        // Diorite
        register(HTMaterialKeys.DIORITE, HTShapeKeys.BLOCK, Items.DIORITE)
        register(HTMaterialKeys.DIORITE, HTShapeKeys.BLOCK, Items.POLISHED_DIORITE)
        // Dripstone
        // register(HTMaterialKeys.DRIPSTONE, HTShapeKeys.BLOCK, Items.DRIPSTONE_BLOCK)
        // Emerald
        register(HTMaterialKeys.EMERALD, HTShapeKeys.BLOCK, Items.EMERALD_BLOCK)
        register(HTMaterialKeys.EMERALD, HTShapeKeys.GEM, Items.EMERALD)
        register(HTMaterialKeys.EMERALD, HTShapeKeys.ORE, Items.EMERALD_ORE)
        // End Stone
        register(HTMaterialKeys.END_STONE, HTShapeKeys.BLOCK, Items.END_STONE)
        // Ender Pearl
        register(HTMaterialKeys.ENDER_PEARL, HTShapeKeys.GEM, Items.ENDER_PEARL)
        // Flint
        register(HTMaterialKeys.FLINT, HTShapeKeys.GEM, Items.FLINT)
        // Iron
        register(HTMaterialKeys.IRON, HTShapeKeys.BLOCK, Items.IRON_BLOCK)
        register(HTMaterialKeys.IRON, HTShapeKeys.INGOT, Items.IRON_INGOT)
        register(HTMaterialKeys.IRON, HTShapeKeys.NUGGET, Items.IRON_NUGGET)
        register(HTMaterialKeys.IRON, HTShapeKeys.ORE, Items.IRON_ORE)
        // register(HTMaterialKeys.IRON, HTShapeKeys.RAW_BLOCK, Items.RAW_IRON_BLOCK)
        // register(HTMaterialKeys.IRON, HTShapeKeys.RAW_ORE, Items.RAW_IRON)
        // Glass
        register(HTMaterialKeys.GLASS, HTShapeKeys.BLOCK, Items.GLASS)
        // Glowstone
        register(HTMaterialKeys.GLOWSTONE, HTShapeKeys.BLOCK, Items.GLOWSTONE)
        register(HTMaterialKeys.GLOWSTONE, HTShapeKeys.DUST, Items.GLOWSTONE_DUST)
        // Gold
        register(HTMaterialKeys.GOLD, HTShapeKeys.BLOCK, Items.GOLD_BLOCK)
        register(HTMaterialKeys.GOLD, HTShapeKeys.INGOT, Items.GOLD_INGOT)
        register(HTMaterialKeys.GOLD, HTShapeKeys.NUGGET, Items.GOLD_NUGGET)
        register(HTMaterialKeys.GOLD, HTShapeKeys.ORE, Items.GOLD_ORE)
        // register(HTMaterialKeys.GOLD, HTShapeKeys.RAW_BLOCK, Items.RAW_GOLD_BLOCK)
        // register(HTMaterialKeys.GOLD, HTShapeKeys.RAW_ORE, Items.RAW_GOLD)
        // Granite
        register(HTMaterialKeys.GRANITE, HTShapeKeys.BLOCK, Items.GRANITE)
        register(HTMaterialKeys.GRANITE, HTShapeKeys.BLOCK, Items.POLISHED_GRANITE)
        // Lapis
        register(HTMaterialKeys.LAPIS, HTShapeKeys.BLOCK, Items.LAPIS_BLOCK)
        register(HTMaterialKeys.LAPIS, HTShapeKeys.GEM, Items.LAPIS_LAZULI)
        register(HTMaterialKeys.LAPIS, HTShapeKeys.ORE, Items.LAPIS_ORE)
        // Nether Brick
        register(HTMaterialKeys.NETHER_BRICK, HTShapeKeys.BLOCK, Items.NETHER_BRICKS)
        register(HTMaterialKeys.NETHER_BRICK, HTShapeKeys.GEM, Items.NETHER_BRICK)
        // Netherite
        register(HTMaterialKeys.NETHERITE, HTShapeKeys.BLOCK, Items.NETHERITE_BLOCK)
        register(HTMaterialKeys.NETHERITE, HTShapeKeys.INGOT, Items.NETHERITE_INGOT)
        // Netherrack
        register(HTMaterialKeys.NETHERRACK, HTShapeKeys.BLOCK, Items.NETHERRACK)
        // Obsidian
        register(HTMaterialKeys.OBSIDIAN, HTShapeKeys.BLOCK, Items.OBSIDIAN)
        // Prismarine
        // register(HTMaterialKeys.PRISMARINE, HTShapeKeys.DUST, Items.PRISMARINE_CRYSTALS)
        // register(HTMaterialKeys.PRISMARINE, HTShapeKeys.GEM, Items.PRISMARINE_SHARD)
        // Quartz
        register(HTMaterialKeys.QUARTZ, HTShapeKeys.BLOCK, Items.QUARTZ_BLOCK)
        register(HTMaterialKeys.QUARTZ, HTShapeKeys.GEM, Items.QUARTZ)
        register(HTMaterialKeys.QUARTZ, HTShapeKeys.ORE, Items.NETHER_QUARTZ_ORE)
        // Redstone
        register(HTMaterialKeys.REDSTONE, HTShapeKeys.BLOCK, Items.REDSTONE_BLOCK)
        register(HTMaterialKeys.REDSTONE, HTShapeKeys.DUST, Items.REDSTONE)
        register(HTMaterialKeys.REDSTONE, HTShapeKeys.ORE, Items.REDSTONE_ORE)
        // Stone
        register(HTMaterialKeys.STONE, HTShapeKeys.BLOCK, Items.STONE)
        // Tuff
        // register(HTMaterialKeys.TUFF, HTShapeKeys.BLOCK, Items.TUFF)
        // Wood
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.OAK_PLANKS)
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.BIRCH_PLANKS)
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.SPRUCE_PLANKS)
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.JUNGLE_PLANKS)
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.ACACIA_PLANKS)
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.DARK_OAK_PLANKS)
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.CRIMSON_HYPHAE)
        register(HTMaterialKeys.WOOD, HTShapeKeys.BLOCK, Items.WARPED_HYPHAE)
    }

    private fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
        // Clear old values
        itemToPartMap.clear()
        partToItemsMap.clear()
        // Register items from Vanilla's registry
        loadVanillaEntries()
        // Register items from part tag
        HTMaterialsAPI.getInstance().materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
            HTMaterialsAPI.getInstance().shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                HTPart(materialKey, shapeKey).getPartTag().values().forEach { item ->
                    register(materialKey, shapeKey, item)
                }
            }
        }
    }
}
