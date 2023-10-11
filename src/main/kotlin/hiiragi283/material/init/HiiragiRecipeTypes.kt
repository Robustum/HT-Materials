package hiiragi283.material.init

import hiiragi283.material.api.recipe.MillRecipe
import net.minecraft.recipe.RecipeType

object HiiragiRecipeTypes {

    @JvmField
    val MILL: RecipeType<MillRecipe> = RecipeType.register("mill")

}