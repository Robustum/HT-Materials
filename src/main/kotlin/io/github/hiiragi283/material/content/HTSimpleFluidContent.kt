package io.github.hiiragi283.material.content

import com.google.common.base.Suppliers
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extention.runWhenOn
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.resource.HTRuntimeResourcePack
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.material.fluid.HTFluidRenderHandler
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.state.StateManager
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import net.minecraft.block.Block as MCBlock
import net.minecraft.fluid.Fluid as MCFluid
import net.minecraft.item.Item as MCItem

class HTSimpleFluidContent : HTMaterialContent.Fluid(HTShapeKey("fluid")) {
    lateinit var bucketItem: MCItem
        private set

    override fun init(materialKey: HTMaterialKey) {
        still = Registry.register(
            Registry.FLUID,
            materialKey.name,
            FluidImpl.Still(this),
        ).let { Suppliers.ofInstance(it) }
        flowing = Registry.register(
            Registry.FLUID,
            HTMaterialsAPI.id("flowing_${materialKey.name}"),
            FluidImpl.Flowing(this),
        )
            .let { Suppliers.ofInstance(it) }
        bucketItem = Registry.register(
            Registry.ITEM,
            HTMaterialsAPI.id("${materialKey.name}_bucket"),
            BucketImpl(still.get(), materialKey),
        )
    }

    override fun postInit(materialKey: HTMaterialKey) {
        // Client-only
        EnvType.CLIENT.runWhenOn {
            // ItemColor
            ColorProviderRegistry.ITEM.register(
                { _: ItemStack, tintIndex: Int ->
                    if (tintIndex == 1) materialKey.getMaterial().color().rgb else -1
                },
                bucketItem,
            )
            // Model
            HTRuntimeResourcePack.addModel(
                bucketItem,
                buildJson {
                    addProperty("parent", "item/generated")
                    addObject("textures") {
                        addProperty("layer0", "item/bucket")
                        addProperty("layer1", "ht_materials:item/bucket")
                    }
                },
            )
            // Renderer
            FluidRenderHandlerRegistry.INSTANCE.register(
                still.get(),
                HTFluidRenderHandler(materialKey.getMaterial()),
            )
            FluidRenderHandlerRegistry.INSTANCE.register(
                flowing.get(),
                HTFluidRenderHandler(materialKey.getMaterial()),
            )
        }
    }

    //    Fluid    //

    private abstract class FluidImpl(private val content: HTSimpleFluidContent) : FlowableFluid() {
        override fun matchesType(fluid: MCFluid): Boolean = fluid == still || fluid == flowing

        override fun isInfinite(): Boolean = false

        override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
            MCBlock.dropStacks(state, world, pos, world.getBlockEntity(pos))
        }

        override fun canBeReplacedWith(
            state: FluidState,
            world: BlockView,
            pos: BlockPos,
            fluid: MCFluid,
            direction: Direction,
        ): Boolean = false

        override fun getFlowSpeed(world: WorldView): Int = 4

        override fun getLevelDecreasePerBlock(world: WorldView): Int = 1

        override fun getTickRate(world: WorldView): Int = 5

        override fun getBlastResistance(): Float = 100.0f

        override fun getStill(): MCFluid = content.still.get()

        override fun getFlowing(): MCFluid = content.flowing.get()

        override fun toBlockState(state: FluidState): BlockState = Blocks.AIR.defaultState

        override fun getBucketItem(): MCItem = content.bucketItem

        //    Flowing    //

        class Flowing(content: HTSimpleFluidContent) : FluidImpl(content) {
            override fun appendProperties(builder: StateManager.Builder<MCFluid, FluidState>) {
                super.appendProperties(builder)
                builder.add(LEVEL)
            }

            override fun isStill(state: FluidState): Boolean = false

            override fun getLevel(state: FluidState): Int = state.get(LEVEL)
        }

        //    Still    //

        class Still(content: HTSimpleFluidContent) : FluidImpl(content) {
            override fun isStill(state: FluidState): Boolean = true

            override fun getLevel(state: FluidState): Int = 0
        }
    }

    //    Bucket    //

    private class BucketImpl(fluid: MCFluid, private val materialKey: HTMaterialKey) : BucketItem(
        fluid,
        FabricItemSettings().group(HTMaterialsAPI.INSTANCE.itemGroup()).maxCount(1).recipeRemainder(Items.BUCKET),
    ) {
        override fun getName(): Text = BUCKET_SHAPE_KEY.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = BUCKET_SHAPE_KEY.getTranslatedText(materialKey)
    }
}

private val BUCKET_SHAPE_KEY: HTShapeKey = HTShapeKey("bucket")
