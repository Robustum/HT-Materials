package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extention.nonAirOrNull
import io.github.hiiragi283.api.extention.runWhenOn
import io.github.hiiragi283.api.fluid.HTFlowableFluid
import io.github.hiiragi283.api.fluid.HTFluidRenderHandler
import io.github.hiiragi283.api.fluid.HTMaterialFluidRenderHandler
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.resource.HTRuntimeResourcePack
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.fluid.Fluid as MCFluid

@Suppress("UnstableApiUsage")
class HTSimpleFluidContent : HTMaterialContent.Fluid(FLUID_KEY) {
    override fun fluidId(materialKey: HTMaterialKey): String = materialKey.name

    override fun settings(settings: HTFlowableFluid.Settings, materialKey: HTMaterialKey) {
        settings.bucket = HTMaterialsAPI.INSTANCE.registerItem("${materialKey.name}_bucket") { BucketImpl(still.get(), materialKey) }
    }

    override fun postInit(materialKey: HTMaterialKey) {
        // Client-only
        EnvType.CLIENT.runWhenOn {
            still.get().bucketItem.nonAirOrNull()?.run {
                // ItemColor
                ColorProviderRegistry.ITEM.register(
                    { _: ItemStack, tintIndex: Int ->
                        if (tintIndex == 1) materialKey.getMaterial().color().rgb else -1
                    },
                    this,
                )
                // Model
                HTRuntimeResourcePack.addModel(
                    this,
                    buildJson {
                        addProperty("parent", "item/generated")
                        addObject("textures") {
                            addProperty("layer0", "item/bucket")
                            addProperty("layer1", "ht_materials:item/bucket")
                        }
                    },
                )
            }
            // Renderer
            FluidRenderHandlerRegistry.INSTANCE.register(
                still.get(),
                HTFluidRenderHandler(materialKey.getMaterial()),
            )
            FluidRenderHandlerRegistry.INSTANCE.register(
                flowing.get(),
                HTFluidRenderHandler(materialKey.getMaterial()),
            )
            FluidVariantRendering.register(still.get(), HTMaterialFluidRenderHandler(materialKey))
            FluidVariantRendering.register(flowing.get(), HTMaterialFluidRenderHandler(materialKey))
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

private val FLUID_KEY = HTShapeKey("fluid")

private val BUCKET_SHAPE_KEY = HTShapeKey("bucket")
