package io.github.hiiragi283.material.common

import com.google.gson.JsonObject
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.util.Identifier
import java.util.function.Supplier

object HTJsonRecipeManager {

    @JvmField
    internal val REGISTRY: MutableMap<Identifier, JsonObject> = mutableMapOf()

    //    Vanilla    //

    @JvmStatic
    fun createVanillaRecipe(jsonBuilder: CraftingRecipeJsonBuilder, recipeId: Identifier) {
        jsonBuilder.offerTo { provider: RecipeJsonProvider ->
            REGISTRY.putIfAbsent(recipeId, provider.toJson())
        }
    }

    //    Modern Industrialization    //

    @JvmStatic
    fun createMIRecipe(supplier: Supplier<*>) {

    }

    //    TechReborn    //

    @JvmStatic
    fun createTRRecipe(supplier: Supplier<*>) {

    }

}