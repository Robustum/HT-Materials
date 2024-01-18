package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.util.HTCustomColoredItem
import io.github.hiiragi283.material.api.util.HTCustomFluidRenderFluid
import io.github.hiiragi283.material.api.util.HTCustomModelItem
import io.github.hiiragi283.material.api.util.HTFluidRenderHandler
import io.github.hiiragi283.material.util.prefix
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvironmentInterface
import net.fabricmc.api.EnvironmentInterfaces
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Block.dropStacks
import net.minecraft.block.BlockState
import net.minecraft.block.FluidBlock
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.state.StateManager
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class HTSimpleFluidContent : HTMaterialContent.FLUID(HTShapeKey("fluid")) {
    override fun createStill(materialKey: HTMaterialKey): FlowableFluid = FluidImpl.Still(this, materialKey)

    override fun getFlowingFluidIdentifier(materialKey: HTMaterialKey): Identifier = getIdentifier(materialKey).prefix("flowing_")

    override fun createFlowing(materialKey: HTMaterialKey): FlowableFluid = FluidImpl.Flowing(this, materialKey)

    override fun getBlockIdentifier(materialKey: HTMaterialKey): Identifier = getIdentifier(materialKey)

    override fun createFluidBlock(fluid: FlowableFluid, materialKey: HTMaterialKey): FluidBlock? = null

    override fun getBucketIdentifier(materialKey: HTMaterialKey): Identifier = bucketShapeKey.getIdentifier(materialKey)

    override fun createFluidBucket(fluid: FlowableFluid, materialKey: HTMaterialKey): BucketItem = BucketImpl(fluid, materialKey)

    override fun getIdentifier(materialKey: HTMaterialKey): Identifier = materialKey.getIdentifier()

    override fun onCreate(materialKey: HTMaterialKey, created: Fluid) {
        if (created is FlowableFluid) {
            HTFluidManager.forceRegister(materialKey, created.flowing)
            HTFluidManager.forceRegister(materialKey, created.still)
        }
    }

    //    Fluid    //

    private abstract class FluidImpl(
        private val content: HTSimpleFluidContent,
        private val materialKey: HTMaterialKey,
    ) : FlowableFluid(), HTCustomFluidRenderFluid {
        override fun matchesType(fluid: Fluid): Boolean = fluid == still || fluid == flowing

        override fun isInfinite(): Boolean = false

        override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
            dropStacks(state, world, pos, world.getBlockEntity(pos))
        }

        override fun canBeReplacedWith(
            state: FluidState,
            world: BlockView,
            pos: BlockPos,
            fluid: Fluid,
            direction: Direction,
        ): Boolean = false

        override fun getFlowSpeed(world: WorldView): Int = 4

        override fun getLevelDecreasePerBlock(world: WorldView): Int = 1

        override fun getTickRate(world: WorldView): Int = 5

        override fun getBlastResistance(): Float = 100.0f

        private lateinit var stillCache: Fluid

        override fun getStill(): Fluid {
            if (!::stillCache.isInitialized) {
                stillCache = Registry.FLUID.get(content.getIdentifier(materialKey))
            }
            return stillCache
        }

        private lateinit var flowingCache: Fluid

        override fun getFlowing(): Fluid {
            if (!::flowingCache.isInitialized) {
                flowingCache = Registry.FLUID.get(content.getIdentifier(materialKey))
            }
            return flowingCache
        }

        private lateinit var blockCache: Block

        override fun toBlockState(state: FluidState): BlockState {
            if (!::blockCache.isInitialized) {
                blockCache = Registry.BLOCK.get(content.getBlockIdentifier(materialKey))
            }
            return blockCache.defaultState
        }

        private lateinit var bucketCache: Item

        override fun getBucketItem(): Item {
            if (!::bucketCache.isInitialized) {
                bucketCache = Registry.ITEM.get(content.getBucketIdentifier(materialKey))
            }
            return bucketCache
        }

        override fun getFluidRenderHandler(): FluidRenderHandler = HTFluidRenderHandler(materialKey.getMaterial())

        //    Flowing    //

        class Flowing(content: HTSimpleFluidContent, materialKey: HTMaterialKey) : FluidImpl(content, materialKey) {
            override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
                super.appendProperties(builder)
                builder.add(LEVEL)
            }

            override fun isStill(state: FluidState): Boolean = false

            override fun getLevel(state: FluidState): Int = state.get(LEVEL)
        }

        //    Still    //

        class Still(content: HTSimpleFluidContent, materialKey: HTMaterialKey) : FluidImpl(content, materialKey) {
            override fun isStill(state: FluidState): Boolean = true

            override fun getLevel(state: FluidState): Int = 0
        }
    }

    //    Bucket    //

    @EnvironmentInterfaces(
        value = [
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomColoredItem::class),
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomModelItem::class),
        ],
    )
    private class BucketImpl(fluid: Fluid, private val materialKey: HTMaterialKey) :
        BucketItem(fluid, itemSettings),
        HTCustomColoredItem,
        HTCustomModelItem {
        override fun getName(): Text = bucketShapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = bucketShapeKey.getTranslatedText(materialKey)

        @Environment(EnvType.CLIENT)
        override fun getColorProvider(): ItemColorProvider = ItemColorProvider { _: ItemStack, tintIndex: Int ->
            if (tintIndex == 1) materialKey.getMaterial().color.rgb else -1
        }

        @Environment(EnvType.CLIENT)
        override fun getModelId(): Identifier = HTMaterials.id("models/item/bucket.json")
    }
}

private val bucketShapeKey: HTShapeKey = HTShapeKey("bucket")

private val itemSettings = FabricItemSettings().group(HTMaterials.ITEM_GROUP).maxCount(1).recipeRemainder(Items.BUCKET)