package io.github.hiiragi283.forge.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.api.util.resource.HTRuntimeResourcePack
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier
import net.minecraft.fluid.Fluid as MCFluid

class HTForgeFluidContent : HTMaterialContent.Fluid(HTShapeKey("fluid")) {
    private lateinit var bucket: Supplier<BucketItem>
    private lateinit var properties: ForgeFlowingFluid.Properties

    override fun init(materialKey: HTMaterialKey) {
        val still: Supplier<MCFluid> =
            RegistryObject.of(HTMaterialsAPI.id(materialKey.name), ForgeRegistries.FLUIDS)
        val flowing: Supplier<MCFluid> =
            RegistryObject.of(HTMaterialsAPI.id("flowing_" + materialKey.name), ForgeRegistries.FLUIDS)
        bucket = RegistryObject.of(HTMaterialsAPI.id(materialKey.name + "_bucket"), ForgeRegistries.ITEMS)
        properties = ForgeFlowingFluid.Properties(
            still,
            flowing,
            FluidAttributes.builder(Identifier("block/white_concrete"), Identifier("block/white_concrete"))
                .color(materialKey.getMaterial().color().rgb)
                .translationKey(materialKey.translationKey),
        ).bucket(bucket)
        HTPlatformHelper.INSTANCE.registerFluid(materialKey.name, ForgeFlowingFluid.Source(properties))
        HTPlatformHelper.INSTANCE.registerFluid("flowing_" + materialKey.name, ForgeFlowingFluid.Flowing(properties))
        HTPlatformHelper.INSTANCE.registerItem(materialKey.name + "_bucket", BucketImpl(still, materialKey))
    }

    override fun postInit(materialKey: HTMaterialKey) {
        // Client-only
        HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
            // ItemColor
            HTPlatformHelper.INSTANCE.registerItemColor(
                { _: ItemStack, tintIndex: Int ->
                    if (tintIndex == 1) materialKey.getMaterial().color().rgb else -1
                },
                bucket.get(),
            )
            // Model
            HTRuntimeResourcePack.addModel(
                bucket.get(),
                buildJson {
                    addProperty("parent", "item/generated")
                    addObject("textures") {
                        addProperty("layer0", "item/bucket")
                        addProperty("layer1", "ht_materials:item/bucket")
                    }
                },
            )
        }
    }

    //    Bucket    //

    private class BucketImpl(fluid: Supplier<MCFluid>, private val materialKey: HTMaterialKey) : BucketItem(
        fluid,
        Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).maxCount(1).recipeRemainder(Items.BUCKET),
    ) {
        override fun getName(): Text = BUCKET_SHAPE_KEY.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = BUCKET_SHAPE_KEY.getTranslatedText(materialKey)
    }
}

private val BUCKET_SHAPE_KEY: HTShapeKey = HTShapeKey("bucket")
