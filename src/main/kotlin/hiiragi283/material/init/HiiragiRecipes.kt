package hiiragi283.material.init

import com.google.gson.JsonElement
import net.minecraft.recipe.Recipe
import net.minecraft.util.Identifier

object HiiragiRecipes {

    //    Registration    //

    @JvmField
    val JSON_RECIPES: MutableMap<Identifier, JsonElement> = mutableMapOf()

    fun <T : Recipe<*>> register(recipe: T, serializer: (T) -> JsonElement) {
        JSON_RECIPES.putIfAbsent(recipe.id, serializer(recipe))
    }

}