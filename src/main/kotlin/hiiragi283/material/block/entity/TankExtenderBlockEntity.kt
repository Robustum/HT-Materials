package hiiragi283.material.block.entity

import hiiragi283.material.init.HiiragiBlockEntities
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

@Suppress("UnstableApiUsage")
class TankExtenderBlockEntity(pos: BlockPos, state: BlockState) : StorageExtenderBlockEntity<FluidVariant>(
    HiiragiBlockEntities.TANK_EXTENDER,
    pos,
    state,
    FluidStorage::SIDED
)