package hiiragi283.material.init

import com.google.gson.JsonElement
import hiiragi283.material.api.recipe.SmithingForgeRecipe
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier

object HiiragiRecipes {

    //    Smithing Forge    //

    const val SMITHING_NAME: String = "smithing_metal_forge"

    val SMITHING_TYPE: RecipeType<SmithingForgeRecipe> = RecipeType.register(SMITHING_NAME)

    val SMITHING_SERIALIZER: SmithingForgeRecipe.Serializer = RecipeSerializer.register(
        SMITHING_NAME,
        SmithingForgeRecipe.Serializer
    )

    //    Registration    //

    @JvmField
    val JSON_RECIPES: MutableMap<Identifier, JsonElement> = mutableMapOf()

    fun <T : Recipe<*>> register(recipe: T, serializer: (T) -> JsonElement) {
        JSON_RECIPES.putIfAbsent(recipe.id, serializer(recipe))
    }

}