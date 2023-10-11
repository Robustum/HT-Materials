package hiiragi283.material.block.entity

import hiiragi283.material.init.HiiragiBlockEntities
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

@Suppress("UnstableApiUsage")
class InventoryExtenderBlockEntity(pos: BlockPos, state: BlockState) : StorageExtenderBlockEntity<ItemVariant>(
    HiiragiBlockEntities.INVENTORY_EXTENDER,
    pos,
    state,
    ItemStorage::SIDED
)