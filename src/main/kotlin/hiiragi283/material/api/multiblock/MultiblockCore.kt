package hiiragi283.material.api.multiblock

import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

interface MultiblockCore {

    fun getPattern(world: ServerWorld, origin: BlockPos): MultiblockPattern

    fun onSucceeded()

    fun onFailed()

}