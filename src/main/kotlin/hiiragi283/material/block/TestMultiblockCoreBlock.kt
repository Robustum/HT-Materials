package hiiragi283.material.block

import hiiragi283.material.api.block.HiiragiBlock
import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.api.multiblock.MultiblockCore
import hiiragi283.material.api.multiblock.MultiblockPattern
import hiiragi283.material.util.blockPredicateOf
import hiiragi283.material.util.statePredicateOf
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos

object TestMultiblockCoreBlock : HiiragiBlock(FabricBlockSettings.of(Material.METAL)), MultiblockCore {

    override val blockItem: HiiragiBlockItem = HiiragiBlockItem(this, FabricItemSettings())

    //    MultiblockCore    //

    override fun getPattern(world: ServerWorld, origin: BlockPos): MultiblockPattern = MultiblockPattern(
        mapOf(
            BlockPos.ORIGIN to blockPredicateOf(this),
            BlockPos(0, -1, 0) to blockPredicateOf {
                state(
                    statePredicateOf {
                        exactMatch(Properties.ENABLED, true)
                    }
                )
            }
        )
    )

    override fun onSucceeded() {
        TODO("Not yet implemented")
    }

    override fun onFailed() {
        TODO("Not yet implemented")
    }

}