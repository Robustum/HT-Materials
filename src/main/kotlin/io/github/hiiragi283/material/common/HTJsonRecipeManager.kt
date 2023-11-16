package io.github.hiiragi283.material.common

import com.google.gson.JsonObject
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.util.Identifier
import java.util.function.Supplier

object HTJsonRecipeManager {

    private val registry: MutableMap<Identifier, JsonObject> = mutableMapOf()

    @JvmField
    val REGISTRY: Map<Identifier, JsonObject> = registry

    //    Vanilla    //

    @JvmStatic
    fun createVanillaRecipe(jsonBuilder: CraftingRecipeJsonBuilder, recipeId: Identifier) {
        jsonBuilder.offerTo { provider: RecipeJsonProvider ->
            registry.putIfAbsent(recipeId, provider.toJson())
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