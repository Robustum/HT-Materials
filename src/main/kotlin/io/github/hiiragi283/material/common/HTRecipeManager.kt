package io.github.hiiragi283.material.common

import com.google.gson.JsonObject
import net.minecraft.data.server.recipe.*
import net.minecraft.util.Identifier

object HTRecipeManager {

    @JvmField
    internal val REGISTRY: MutableMap<Identifier, JsonObject> = mutableMapOf()

    //    Vanilla    //

    @JvmStatic
    fun cooking(recipeId: Identifier, factory: CookingRecipeJsonFactory) {
        factory.offerTo({ provider: RecipeJsonProvider -> REGISTRY.putIfAbsent(recipeId, provider.toJson()) }, recipeId)
    }

    @JvmStatic
    fun shapedCrafting(recipeId: Identifier, factory: ShapedRecipeJsonFactory) {
        factory.offerTo({ provider: RecipeJsonProvider -> REGISTRY.putIfAbsent(recipeId, provider.toJson()) }, recipeId)
    }

    @JvmStatic
    fun shapelessCrafting(recipeId: Identifier, factory: ShapelessRecipeJsonFactory) {
        factory.offerTo({ provider: RecipeJsonProvider -> REGISTRY.putIfAbsent(recipeId, provider.toJson()) }, recipeId)
    }

    @JvmStatic
    fun smithing(recipeId: Identifier, factory: SmithingRecipeJsonFactory) {
        factory.offerTo({ provider: RecipeJsonProvider -> REGISTRY.putIfAbsent(recipeId, provider.toJson()) }, recipeId)
    }

    @JvmStatic
    fun singleItem(recipeId: Identifier, factory: SingleItemRecipeJsonFactory) {
        factory.offerTo({ provider: RecipeJsonProvider -> REGISTRY.putIfAbsent(recipeId, provider.toJson()) }, recipeId)
    }

}