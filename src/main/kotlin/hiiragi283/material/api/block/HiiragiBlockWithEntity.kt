package hiiragi283.material.api.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

abstract class HiiragiBlockWithEntity<T : BlockEntity>(
    val function: (BlockPos, BlockState) -> T?,
    settings: FabricBlockSettings
) : HiiragiBlock(settings), BlockEntityProvider {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? = function(pos, state)

}