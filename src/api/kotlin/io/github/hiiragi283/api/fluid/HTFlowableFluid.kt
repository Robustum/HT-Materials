package io.github.hiiragi283.api.fluid

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.function.Supplier

abstract class HTFlowableFluid(private val settings: Settings) : FlowableFluid() {
    class Settings(
        val still: Supplier<Fluid>,
        val flowing: Supplier<Fluid>,
    ) {
        var infinite: Boolean = false
        var bucket: Supplier<Item> = Supplier(Items::AIR)
        var block: Supplier<Block> = Supplier(Blocks::AIR)
        var flowSpeed: Int = 4
        var levelPerDecrease: Int = 1
        var blastResistance: Float = 100.0f
        var tickRate: Int = 5
    }

    override fun getBucketItem(): Item = settings.bucket.get()

    override fun canBeReplacedWith(
        state: FluidState,
        world: BlockView,
        pos: BlockPos,
        fluid: Fluid,
        direction: Direction,
    ): Boolean = direction == Direction.DOWN && !matchesType(fluid)

    override fun getTickRate(world: WorldView): Int = settings.tickRate

    override fun getBlastResistance(): Float = settings.blastResistance

    override fun toBlockState(state: FluidState): BlockState = settings.block.get().defaultState
        ?.takeIf { it.contains(FluidBlock.LEVEL) }
        ?.with(FluidBlock.LEVEL, getBlockStateLevel(state))
        ?: settings.block.get().defaultState

    override fun getFlowing(): Fluid = settings.flowing.get()

    override fun getStill(): Fluid = settings.still.get()

    override fun isInfinite(): Boolean = settings.infinite

    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
        world.getBlockEntity(pos)?.let { Block.dropStacks(state, world, pos, it) }
    }

    override fun getFlowSpeed(world: WorldView?): Int = settings.flowSpeed

    override fun getLevelDecreasePerBlock(world: WorldView?): Int = settings.levelPerDecrease

    class Flowing(settings: Settings) : HTFlowableFluid(settings) {
        override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
            super.appendProperties(builder)
            builder.add(LEVEL)
        }

        override fun getLevel(state: FluidState): Int = state.get(LEVEL)

        override fun isStill(state: FluidState): Boolean = false
    }

    class Still(settings: Settings) : HTFlowableFluid(settings) {
        override fun getLevel(state: FluidState): Int = 8

        override fun isStill(state: FluidState): Boolean = true
    }
}
