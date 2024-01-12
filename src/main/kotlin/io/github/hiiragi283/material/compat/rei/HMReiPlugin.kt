package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterial
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeHelper
import me.shedaniel.rei.api.plugins.REIPluginV0
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
object HMReiPlugin : REIPluginV0 {

    val MATERIAL: Identifier = HTMaterialsCommon.id("material")

    override fun getPluginIdentifier(): Identifier = HTMaterialsCommon.id("plugin")

    override fun registerPluginCategories(recipeHelper: RecipeHelper) {
        recipeHelper.registerCategory(HTMaterialCategory)
    }

    override fun registerRecipeDisplays(recipeHelper: RecipeHelper) {
        HTMaterial.getMaterials()
            .map(::HTMaterialDisplay)
            .filterNot { it.entries.isEmpty() }
            .forEach(recipeHelper::registerDisplay)
    }

    override fun registerOthers(recipeHelper: RecipeHelper) {
        recipeHelper.registerWorkingStations(MATERIAL, EntryStack.create(HTMaterialsCommon.ICON))
    }

}