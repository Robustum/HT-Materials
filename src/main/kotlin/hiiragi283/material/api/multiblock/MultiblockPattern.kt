package hiiragi283.material.api.multiblock

import net.minecraft.predicate.BlockPredicate
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import java.util.function.BiPredicate

class MultiblockPattern : BiPredicate<ServerWorld, BlockPos> {

    val MAP: Map<BlockPos, BlockPredicate>

    constructor(map: Map<BlockPos, BlockPredicate> = mapOf()) {
        MAP = map
    }

    constructor(vararg pair: Pair<BlockPos, BlockPredicate>) {
        MAP = pair.toMap()
    }

    fun getAbsolutePoses(origin: BlockPos): List<BlockPos> = MAP.keys.map(origin::add)

    //    BiPredicate    //

    override fun test(t: ServerWorld, u: BlockPos): Boolean = MAP
        .all { (pos, predicate) -> predicate.test(t, u.add(pos)) }

}