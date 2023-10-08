package hiiragi283.material.api.fluid

import hiiragi283.material.api.registry.HiiragiRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

/**
 * [Reference](https://github.com/Fabricators-of-Create/Registrate-Refabricated/blob/fabric/1.18/src/main/java/com/tterrag/registrate/fabric/SimpleFlowableFluid.java)
 */

abstract class HiiragiFlowableFluid(settings: Settings = Settings()) : FlowableFluid(), HiiragiRegistry.Entry<Fluid> {

    private val blastResistance: Float = settings.blastResistance
    private val block: () -> Block = settings.block
    private val bucket: () -> Item = settings.bucket
    private val decrease: Int = settings.decrease
    private val flowing: () -> Fluid = settings.flowing
    private val flowSpeed: Int = settings.flowSpeed
    private val infinite: Boolean = settings.infinite
    private val still: () -> Fluid = settings.still
    private val tickRate: Int = settings.tickRate

    //    Fluid    //

    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
        Block.dropStacks(state, world, pos, world.getBlockEntity(pos))
    }

    override fun canBeReplacedWith(
        fluidState: FluidState,
        blockView: BlockView,
        blockPos: BlockPos,
        fluid: Fluid,
        direction: Direction
    ): Boolean = false

    override fun getBlastResistance(): Float = blastResistance

    override fun getFlowSpeed(world: WorldView): Int = flowSpeed

    override fun getLevelDecreasePerBlock(world: WorldView): Int = decrease

    override fun getTickRate(world: WorldView): Int = tickRate

    override fun isInfinite(): Boolean = infinite

    override fun matchesType(fluid: Fluid): Boolean = fluid == still || fluid == flowing

    //    FlowableFluid   //

    override fun getStill(): Fluid = still()

    override fun getFlowing(): Fluid = flowing()

    override fun getBucketItem(): Item = bucket()

    override fun toBlockState(state: FluidState): BlockState =
        block().defaultState.with(Properties.LEVEL_15, getBlockStateLevel(state))

    //    Entry    //

    override fun asItem(): Item = bucketItem

    override fun toNbt(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putString("fluid", Registry.FLUID.getId(this).toString())
        return nbt
    }

    override fun toPacket(buf: PacketByteBuf) {
        buf.writeString(Registry.FLUID.getId(this).toString())
    }

    //    Flowing    //

    class Flowing(settings: Settings) : HiiragiFlowableFluid(settings) {

        override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
            super.appendProperties(builder)
            builder.add(LEVEL)
        }

        override fun getLevel(state: FluidState): Int = state.get(LEVEL)

        override fun isStill(state: FluidState): Boolean = false

    }

    //    Still    //

    class Still(settings: Settings) : HiiragiFlowableFluid(settings) {

        override fun getLevel(state: FluidState): Int = 8

        override fun isStill(state: FluidState): Boolean = true

    }

    //    Settings    //

    data class Settings(
        val blastResistance: Float = 100.0f,
        val block: () -> Block = Blocks::AIR,
        val bucket: () -> Item = Items::AIR,
        val decrease: Int = 1,
        val flowing: () -> Fluid = Fluids::EMPTY,
        val flowSpeed: Int = 4,
        val infinite: Boolean = false,
        val still: () -> Fluid = Fluids::EMPTY,
        val tickRate: Int = 5
    )

}