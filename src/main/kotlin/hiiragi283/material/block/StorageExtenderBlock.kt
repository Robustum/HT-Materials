package hiiragi283.material.block

import hiiragi283.material.api.block.HiiragiBlockWithEntity
import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.init.HiiragiItemGroups
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class StorageExtenderBlock<T : BlockEntity>(function: (BlockPos, BlockState) -> T?) : HiiragiBlockWithEntity<T>(
    function,
    FabricBlockSettings.of(Material.METAL)
) {

    init {
        defaultState = defaultState.with(Properties.FACING, Direction.NORTH)
    }

    //    BlockState    //

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(Properties.FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? =
        defaultState.with(Properties.FACING, ctx.playerLookDirection.opposite)

    //    HiiragiBlock    //

    override val blockItem: HiiragiBlockItem = HiiragiBlockItem(
        this,
        FabricItemSettings().group(HiiragiItemGroups.COMMON)
    )

}