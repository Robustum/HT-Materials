package io.github.hiiragi283.material

import com.google.gson.JsonElement
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.util.prefix
import net.minecraft.data.server.RecipesProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import java.util.function.Consumer

object HTRecipeManagerMixin {
    @JvmStatic
    fun addRecipes(map: MutableMap<Identifier, JsonElement>) {
        HTMaterial.getMaterialKeys().forEach { key: HTMaterialKey ->
            HTPartManager.getDefaultItem(key, HTShapes.INGOT)?.let { ingotRecipe(key, it, map) }
            HTPartManager.getDefaultItem(key, HTShapes.NUGGET)?.let { nuggetRecipe(key, it, map) }
        }
    }

    private fun exporter(map: MutableMap<Identifier, JsonElement>): Consumer<RecipeJsonProvider> = Consumer { provider ->
        map.putIfAbsent(
            provider.recipeId,
            provider.toJson(),
        )
    }

    private fun ingotRecipe(material: HTMaterialKey, item: Item, map: MutableMap<Identifier, JsonElement>) {
        // 9x Nugget -> 1x Ingot
        if (!HTPartManager.hasDefaultItem(material, HTShapes.NUGGET)) return
        val nuggetTag: Tag<Item> = HTShapes.NUGGET.getCommonTag(material)
        ShapedRecipeJsonFactory.create(item)
            .pattern("AAA")
            .pattern("AAA")
            .pattern("AAA")
            .input('A', nuggetTag)
            .criterion("has_nugget", RecipesProvider.conditionsFromTag(nuggetTag))
            .offerTo(exporter(map), HTShapes.INGOT.getIdentifier(material).prefix("shaped/"))
    }

    private fun nuggetRecipe(material: HTMaterialKey, item: Item, map: MutableMap<Identifier, JsonElement>) {
        // 1x Ingot -> 9x Nugget
        if (!HTPartManager.hasDefaultItem(material, HTShapes.INGOT)) return
        val ingotTag: Tag<Item> = HTShapes.INGOT.getCommonTag(material)
        ShapelessRecipeJsonFactory.create(item, 9)
            .input(ingotTag)
            .criterion("has_ingot", RecipesProvider.conditionsFromTag(ingotTag))
            .offerTo(exporter(map), HTShapes.NUGGET.getIdentifier(material).prefix("shapeless/"))
    }
}
