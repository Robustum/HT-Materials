package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extention.id
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.recipe.HTReplaceManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import reborncore.common.crafting.RebornRecipeType
import techreborn.init.ModRecipes
import techreborn.init.TRContent

@Suppress("unused")
object HMTRAddon : HTMaterialsAddon {
    override val modId: String = "techreborn"
    override val priority: Int = 0

    @JvmField
    val SMALL_DUST = HTShapeKey("small_dust")

    override fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {
        registry.add(SMALL_DUST)
    }

    override fun bindItemToPart(builder: HTPartManager.Builder) {
        builder.add(HTMaterialKeys.RUBY, HTShapeKeys.GEM, TRContent.Gems.RUBY)
        builder.add(HTMaterialKeys.SAPPHIRE, HTShapeKeys.GEM, TRContent.Gems.SAPPHIRE)
        builder.add(HTMaterialKeys.PHOSPHORUS, HTShapeKeys.DUST, TRContent.Dusts.PHOSPHOROUS)
        builder.add(HTMaterialKeys.PHOSPHORUS, SMALL_DUST, TRContent.SmallDusts.PHOSPHOROUS)
    }

    override fun replaceJsonRecipe(id: Identifier, serializer: RecipeSerializer<*>, jsonObject: JsonObject) {
        if (serializer is RebornRecipeType<*>) {
            if (serializer == ModRecipes.ROLLING_MACHINE) return
            // Replace outputs
            JsonHelper.getArray(jsonObject, "results").forEach { element: JsonElement ->
                if (element is JsonObject) {
                    HTMaterialsAPI.INSTANCE.partManager()
                        .convertDefaultItem(JsonHelper.getItem(element, "item"))
                        ?.takeIf(HTReplaceManager::enabledOutputReplace)
                        ?.run {
                            element.addProperty("item", this.id.toString())
                            HTMaterialsAPI.log("Replaced recipe outputs!; $id")
                        }
                }
            }
        }
    }
}
