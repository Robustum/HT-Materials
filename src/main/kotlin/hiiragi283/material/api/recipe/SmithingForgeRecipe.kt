package hiiragi283.material.api.recipe

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import hiiragi283.material.init.HiiragiRecipes
import hiiragi283.material.util.toJson
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.SmithingRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList

class SmithingForgeRecipe(
    id: Identifier,
    base: Ingredient,
    tool: Ingredient,
    result: ItemStack
) : SmithingRecipe(id, base, tool, result) {

    fun register() {
        HiiragiRecipes.register(this, HiiragiRecipes.SMITHING_SERIALIZER::toJson)
    }

    //    SmithingRecipe    //

    override fun getSerializer(): RecipeSerializer<*> = HiiragiRecipes.SMITHING_SERIALIZER

    override fun getType(): RecipeType<*> = HiiragiRecipes.SMITHING_TYPE

    override fun getRemainder(inventory: Inventory): DefaultedList<ItemStack> {
        val defaultedList: DefaultedList<ItemStack> = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY)
        (0 until inventory.size()).forEach { index: Int ->
            val stack: ItemStack = inventory.getStack(index)
            if (stack.item.hasRecipeRemainder()) {
                defaultedList[index] = stack.recipeRemainder
            }
        }
        return defaultedList
    }

    //    Serializer    //

    object Serializer : SmithingRecipe.Serializer() {

        fun toJson(smithingForgeRecipe: SmithingForgeRecipe): JsonElement {
            val root = JsonObject()
            root.addProperty("type", Identifier(HiiragiRecipes.SMITHING_NAME).toString())
            root.add("base", smithingForgeRecipe.ingredients[0].toJson())
            root.add("addition", smithingForgeRecipe.ingredients[1].toJson())
            root.add("result", smithingForgeRecipe.output.toJson())
            return root
        }

    }

}