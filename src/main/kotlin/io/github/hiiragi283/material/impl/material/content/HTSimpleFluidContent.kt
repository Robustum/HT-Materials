package io.github.hiiragi283.material.impl.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.fluid.HTFluidRenderHandler
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.api.util.prefix
import io.github.hiiragi283.api.util.resource.HTRuntimeResourcePack
import io.github.hiiragi283.material.HTMaterials
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
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
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class HTSimpleFluidContent : HTMaterialContent.FLUID(HTShapeKey("fluid")) {
    override fun still(materialKey: HTMaterialKey): FlowableFluid = FluidImpl.Still(this, materialKey)

    override fun flowingId(materialKey: HTMaterialKey): Identifier = id(materialKey).prefix("flowing_")

    override fun flowing(materialKey: HTMaterialKey): FlowableFluid = FluidImpl.Flowing(this, materialKey)

    override fun blockId(materialKey: HTMaterialKey): Identifier = id(materialKey)

    override fun block(fluid: FlowableFluid, materialKey: HTMaterialKey): FluidBlock? = null

    override fun bucketId(materialKey: HTMaterialKey): Identifier = HTMaterialsAPI.id("${materialKey.name}_bucket")

    override fun bucket(fluid: FlowableFluid, materialKey: HTMaterialKey): BucketItem = BucketImpl(fluid, materialKey)

    override fun id(materialKey: HTMaterialKey): Identifier = materialKey.getIdentifier()

    override fun onCreate(materialKey: HTMaterialKey, created: Fluid) {
        if (created is FlowableFluid) {
            // HTFluidManagerOld.forceRegister(materialKey, created.flowing)
            // HTFluidManagerOld.forceRegister(materialKey, created.still)
        }
        // Model
        HTRuntimeResourcePack.addModel(
            created.bucketItem,
            buildJson {
                addProperty("parent", "item/generated")
                addObject("textures") {
                    addProperty("layer0", "item/bucket")
                    addProperty("layer1", "ht_materials:item/bucket")
                }
            },
        )
    }

    //    Fluid    //

    private abstract class FluidImpl(
        private val content: HTSimpleFluidContent,
        private val materialKey: HTMaterialKey,
    ) : FlowableFluid() {
        override fun matchesType(fluid: Fluid): Boolean = fluid == still || fluid == flowing

        override fun isInfinite(): Boolean = false

        override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
            Block.dropStacks(state, world, pos, world.getBlockEntity(pos))
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
                stillCache = Registry.FLUID.get(content.id(materialKey))
            }
            return stillCache
        }

        private lateinit var flowingCache: Fluid

        override fun getFlowing(): Fluid {
            if (!::flowingCache.isInitialized) {
                flowingCache = Registry.FLUID.get(content.id(materialKey))
            }
            return flowingCache
        }

        private lateinit var blockCache: Block

        override fun toBlockState(state: FluidState): BlockState {
            if (!::blockCache.isInitialized) {
                blockCache = Registry.BLOCK.get(content.blockId(materialKey))
            }
            return blockCache.defaultState
        }

        private lateinit var bucketCache: Item

        override fun getBucketItem(): Item {
            if (!::bucketCache.isInitialized) {
                bucketCache = Registry.ITEM.get(content.bucketId(materialKey))
            }
            return bucketCache
        }

        //    Flowing    //

        class Flowing(content: HTSimpleFluidContent, materialKey: HTMaterialKey) : FluidImpl(content, materialKey) {
            init {
                HTPlatformHelper.INSTANCE.onEnv(EnvType.CLIENT) {
                    FluidRenderHandlerRegistry.INSTANCE.register(this, HTFluidRenderHandler(materialKey.getMaterial()))
                }
            }

            override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
                super.appendProperties(builder)
                builder.add(LEVEL)
            }

            override fun isStill(state: FluidState): Boolean = false

            override fun getLevel(state: FluidState): Int = state.get(LEVEL)
        }

        //    Still    //

        class Still(content: HTSimpleFluidContent, materialKey: HTMaterialKey) : FluidImpl(content, materialKey) {
            init {
                HTPlatformHelper.INSTANCE.onEnv(EnvType.CLIENT) {
                    FluidRenderHandlerRegistry.INSTANCE.register(this, HTFluidRenderHandler(materialKey.getMaterial()))
                }
            }

            override fun isStill(state: FluidState): Boolean = true

            override fun getLevel(state: FluidState): Int = 0
        }
    }

    //    Bucket    //

    private class BucketImpl(fluid: Fluid, private val materialKey: HTMaterialKey) : BucketItem(fluid, ITEM_SETTINGS) {
        init {
            HTPlatformHelper.INSTANCE.onEnv(EnvType.CLIENT) {
                ColorProviderRegistry.ITEM.register(
                    ItemColorProvider { _: ItemStack, tintIndex: Int ->
                        if (tintIndex == 1) materialKey.getMaterial().color().rgb else -1
                    },
                    this,
                )
            }
        }

        /*override fun getName(): Text = BUCKET_SHAPE_KEY.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = BUCKET_SHAPE_KEY.getTranslatedText(materialKey)*/
    }
}

private val BUCKET_SHAPE_KEY: HTShapeKey = HTShapeKey("bucket")

private val ITEM_SETTINGS =
    FabricItemSettings().group(HTMaterials.itemGroup()).maxCount(1).recipeRemainder(Items.BUCKET)