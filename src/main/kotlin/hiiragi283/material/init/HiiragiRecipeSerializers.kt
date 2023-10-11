package hiiragi283.material.init

import hiiragi283.material.api.recipe.MillRecipe
import net.minecraft.recipe.RecipeSerializer

object HiiragiRecipeSerializers {

    @JvmField
    val MILL: MillRecipe.Serializer = RecipeSerializer.register("mill", MillRecipe.Serializer)

}