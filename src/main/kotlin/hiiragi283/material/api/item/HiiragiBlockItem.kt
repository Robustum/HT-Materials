package hiiragi283.material.api.item

import hiiragi283.material.api.registry.HiiragiRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item

open class HiiragiBlockItem(block: Block, settings: FabricItemSettings) : BlockItem(block, settings),
    HiiragiRegistry.Entry<Item>