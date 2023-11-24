package io.github.hiiragi283.material.common

import aztech.modern_industrialization.recipe.json.MIRecipeJson
import com.google.gson.JsonObject
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.util.Identifier
import reborncore.common.crafting.RebornRecipe
import reborncore.common.crafting.serde.RebornRecipeSerde
import java.util.function.Function

object HTRecipeManager {

    @JvmField
    internal val REGISTRY: MutableMap<Identifier, JsonObject> = mutableMapOf()

    //    Vanilla    //

    @JvmStatic
    fun registerVanillaRecipe(recipeId: Identifier, jsonBuilder: CraftingRecipeJsonBuilder) {
        jsonBuilder.offerTo({ provider: RecipeJsonProvider ->
            REGISTRY.putIfAbsent(recipeId, provider.toJson())
        }, recipeId)
    }

    //    Create    //

    fun registerCreateRecipe() {

    }

    //    Modern Industrialization    //

    @JvmStatic
    fun <T : MIRecipeJson<*>> registerMIRecipe(recipeId: Identifier, recipeJson: MIRecipeJson<T>) {
        REGISTRY.putIfAbsent(recipeId, recipeJson.toJsonObject())
    }

    //    TechReborn    //

    @JvmStatic
    fun <T : RebornRecipe> registerTRRecipe(
        recipeId: Identifier,
        rebornRecipeSerde: RebornRecipeSerde<T>,
        function: Function<Identifier, T>
    ) {
        val jsonObject = JsonObject()
        rebornRecipeSerde.toJson(function.apply(recipeId), jsonObject, true)
        REGISTRY.putIfAbsent(recipeId, jsonObject)
    }

    fun test() {
        /*createTRRecipe(HTMaterialsCommon.id("test"), ModRecipes.ASSEMBLING_RECIPE_SERDE) {
            AssemblingMachineRecipe(
                ModRecipes.ASSEMBLING_MACHINE,
                it,
                listOf(TagIngredient(HTShape.INGOT.getCommonTag(HTElementMaterials.IRON), Optional.empty())),
                listOf(),
                32,
                200
            )
        }*/

    }

}