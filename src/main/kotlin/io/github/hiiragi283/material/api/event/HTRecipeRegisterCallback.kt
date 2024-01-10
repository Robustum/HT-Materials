package io.github.hiiragi283.material.api.event

import com.google.gson.JsonElement
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.util.Identifier

fun interface HTRecipeRegisterCallback {

    fun onRecipeRegister(handler: Handler)

    companion object {
        @JvmField
        val EVENT: Event<HTRecipeRegisterCallback> =
            EventFactory.createArrayBacked(HTRecipeRegisterCallback::class.java) { callbacks ->
                HTRecipeRegisterCallback { handler -> callbacks.forEach { it.onRecipeRegister(handler) } }
            }
    }

    class Handler(private val map: MutableMap<Identifier, JsonElement>) {

        fun addShapedCrafting(recipeId: Identifier, builder: ShapedRecipeJsonFactory) {
            builder.offerTo({ provider: RecipeJsonProvider ->
                map.putIfAbsent(
                    provider.recipeId,
                    provider.toJson()
                )
            }, recipeId)
        }

        fun addShapelessCrafting(recipeId: Identifier, builder: ShapelessRecipeJsonFactory) {
            builder.offerTo({ provider: RecipeJsonProvider ->
                map.putIfAbsent(
                    provider.recipeId,
                    provider.toJson()
                )
            }, recipeId)
        }

        fun addJson(recipeId: Identifier, jsonElement: JsonElement) {
            map.putIfAbsent(recipeId, jsonElement)
        }

        fun remove(recipeId: Identifier) {
            map.remove(recipeId)
        }

    }

}