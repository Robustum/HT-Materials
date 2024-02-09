package io.github.hiiragi283.material.impl.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.api.util.resource.HTRuntimeResourcePack
import net.minecraft.item.BucketItem
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.ForgeFlowingFluid
import java.util.function.Supplier
import net.minecraft.item.Item as MCItem

class HTForgeFluidContent : HTMaterialContent.Fluid(HTShapeKey("fluid")) {
    private lateinit var bucket: Supplier<BucketItem>
    private lateinit var properties: ForgeFlowingFluid.Properties

    override fun init(materialKey: HTMaterialKey) {
        still = HTPlatformHelper.INSTANCE.registerFluid(materialKey.name) {
            ForgeFlowingFluid.Source(properties)
        }
        flowing = HTPlatformHelper.INSTANCE.registerFluid(materialKey.name + "_flowing") {
            ForgeFlowingFluid.Flowing(properties)
        }
        bucket = HTPlatformHelper.INSTANCE.registerItem(materialKey.name + "_bucket") {
            BucketItem(still, MCItem.Settings().group(HTMaterialsAPI.INSTANCE.itemGroup()).maxCount(1).recipeRemainder(Items.BUCKET))
        }
        properties = ForgeFlowingFluid.Properties(
            still,
            flowing,
            FluidAttributes.builder(Identifier("block/white_concrete"), Identifier("block/white_concrete")),
        ).bucket(bucket)
    }

    override fun postInit(materialKey: HTMaterialKey) {
        // Client-only
        HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
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
}
