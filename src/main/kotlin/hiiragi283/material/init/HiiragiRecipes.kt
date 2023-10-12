package hiiragi283.material.init

import com.google.gson.JsonElement
import hiiragi283.material.api.recipe.MillRecipe
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier

object HiiragiRecipes {

    //    Registration    //

    @JvmField
    val JSON_RECIPES: MutableMap<Identifier, JsonElement> = mutableMapOf()

    fun <T : Recipe<*>> register(recipe: T, serializer: (T) -> JsonElement) {
        JSON_RECIPES.putIfAbsent(recipe.id, serializer(recipe))
    }

    object Serializer {

        @JvmField
        val MILL: MillRecipe.Serializer = RecipeSerializer.register("mill", MillRecipe.Serializer)

    }

    object Type {

        @JvmField
        val MILL: RecipeType<MillRecipe> = RecipeType.register("mill")

    }

}