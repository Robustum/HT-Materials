package hiiragi283.material.client.model

import hiiragi283.material.util.hiiragiId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
object HiiragiModelResourceProvider : ModelResourceProvider {

    @JvmField
    val FOUR_SIDED_MODEL: Identifier = hiiragiId("four_sided_furnace")

    override fun loadModelResource(resourceId: Identifier, context: ModelProviderContext): UnbakedModel? =
        when (resourceId) {
            FOUR_SIDED_MODEL -> TestBlockModel
            else -> null
        }

}