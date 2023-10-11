package hiiragi283.material.api.item

import hiiragi283.material.api.registry.HiiragiRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item

abstract class HiiragiItem(settings: FabricItemSettings) : Item(settings), HiiragiRegistry.Entry<Item>