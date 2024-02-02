package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.material.HTMaterials
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeHelper
import me.shedaniel.rei.api.plugins.REIPluginV0
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
object HMReiPlugin : REIPluginV0 {
    val MATERIAL: Identifier = HTMaterialsAPI.id("io/github/hiiragi283/material")

    override fun getPluginIdentifier(): Identifier = HTMaterialsAPI.id("plugin")

    override fun registerPluginCategories(recipeHelper: RecipeHelper) {
        recipeHelper.registerCategory(HTMaterialCategory)
    }

    override fun registerRecipeDisplays(recipeHelper: RecipeHelper) {
        HTMaterialsAPI.getInstance().materialRegistry().getValues()
            .map(::HTMaterialDisplay)
            .filterNot { it.entries.isEmpty() }
            .forEach(recipeHelper::registerDisplay)
    }

    override fun registerOthers(recipeHelper: RecipeHelper) {
        recipeHelper.registerWorkingStations(MATERIAL, EntryStack.create(HTMaterials.iconItem()))
    }
}
