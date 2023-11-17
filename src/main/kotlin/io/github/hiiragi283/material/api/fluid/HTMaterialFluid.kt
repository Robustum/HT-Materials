package io.github.hiiragi283.material.api.fluid

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.prefix
import io.github.hiiragi283.material.common.util.suffix
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

@Suppress("UnstableApiUsage", "unused")
abstract class HTMaterialFluid(val material: HTMaterial) : FlowableFluid() {

    companion object {

        private val fluidFlowing: MutableMap<HTMaterial, Flowing> = mutableMapOf()

        private val fluidStill: MutableMap<HTMaterial, Still> = mutableMapOf()

        private val fluidBlock: MutableMap<HTMaterial, Block> = mutableMapOf()

        private val fluidBucket: MutableMap<HTMaterial, Bucket> = mutableMapOf()

        @JvmStatic
        fun getFlowings(): Collection<Flowing> = fluidFlowing.values

        @JvmStatic
        fun getStills(): Collection<Still> = fluidStill.values

        @JvmStatic
        fun getBlocks(): Collection<Block> = fluidBlock.values

        @JvmStatic
        fun getBuckets(): Collection<Bucket> = fluidBucket.values

        @JvmStatic
        fun getFlowing(material: HTMaterial): Flowing? = fluidFlowing[material]

        @JvmStatic
        fun getStill(material: HTMaterial): Still? = fluidStill[material]

        @JvmStatic
        fun getBlock(material: HTMaterial): Block? = fluidBlock[material]

        @JvmStatic
        fun getBucket(material: HTMaterial): Bucket? = fluidBucket[material]

    }

    //    FlowableFluid    //

    override fun matchesType(fluid: Fluid): Boolean = fluid == still || fluid == flowing

    override fun isInfinite(): Boolean = false

    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
        net.minecraft.block.Block.dropStacks(state, world, pos, world.getBlockEntity(pos))
    }

    override fun canBeReplacedWith(
        state: FluidState,
        world: BlockView,
        pos: BlockPos,
        fluid: Fluid,
        direction: Direction
    ): Boolean = false

    override fun getFlowSpeed(world: WorldView): Int = 4

    override fun getLevelDecreasePerBlock(world: WorldView): Int = 1

    override fun getTickRate(world: WorldView): Int = 5

    override fun getBlastResistance(): Float = 100.0f

    override fun getStill(): Fluid = fluidStill[material]!!

    override fun getFlowing(): Fluid = fluidFlowing[material]!!

    override fun toBlockState(state: FluidState): BlockState = fluidBlock
        .getOrDefault(material, Blocks.AIR)
        .defaultState
        .with(Properties.LEVEL_15, getBlockStateLevel(state))

    override fun getBucketItem(): Item = fluidBucket[material]!!

    //    Flowing    //

    class Flowing(material: HTMaterial) : HTMaterialFluid(material) {

        init {
            fluidFlowing.putIfAbsent(material, this)
            Registry.register(
                Registry.FLUID,
                material.getIdentifier(HTMaterialsCommon.MOD_ID).prefix("flowing_"),
                this
            )
        }

        override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
            super.appendProperties(builder)
            builder.add(LEVEL)
        }

        override fun getLevel(state: FluidState): Int = state.get(LEVEL)

        override fun isStill(state: FluidState): Boolean = false

    }

    //    Still    //

    class Still(material: HTMaterial) : HTMaterialFluid(material) {

        init {
            fluidStill.putIfAbsent(material, this)
            Registry.register(
                Registry.FLUID,
                material.getIdentifier(HTMaterialsCommon.MOD_ID),
                this
            )
            FluidVariantAttributes.register(this, AttributeHandler(material))
        }

        override fun getLevel(state: FluidState): Int = 8

        override fun isStill(state: FluidState): Boolean = true

    }

    //    Block    //

    class Block(fluid: Still) : FluidBlock(fluid, FabricBlockSettings.copyOf(Blocks.WATER)) {

        val identifier: Identifier = fluid.material.getIdentifier(HTMaterialsCommon.MOD_ID)

        init {
            fluidBlock.putIfAbsent(fluid.material, this)
            Registry.register(Registry.BLOCK, identifier, this)
        }

    }

    //    Bucket    //

    class Bucket(fluid: Still) : BucketItem(fluid, FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)) {

        val material: HTMaterial = fluid.material

        val identifier: Identifier = fluid.material.getIdentifier(HTMaterialsCommon.MOD_ID).suffix("_bucket")

        init {
            fluidBucket.putIfAbsent(fluid.material, this)
            Registry.register(Registry.ITEM, identifier, this)
        }

    }

    //    Attribute    //

    class AttributeHandler(val material: HTMaterial) : FluidVariantAttributeHandler {

        override fun getName(fluidVariant: FluidVariant): Text = material.getTranslatedText()

    }

}