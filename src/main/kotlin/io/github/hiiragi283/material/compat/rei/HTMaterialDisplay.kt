package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPart
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeDisplay
import net.minecraft.util.Identifier

class HTMaterialDisplay(val material: HTMaterial) : RecipeDisplay {
    private val key: HTMaterialKey = material.key
    val entries: List<EntryStack> = buildList {
        addAll(
            HTMaterialsAPI.INSTANCE.fluidManager[key]
                .map(EntryStack::create),
        )
        addAll(
            HTMaterialsAPI.INSTANCE.shapeRegistry.values
                .map { HTPart(material, it) }
                .flatMap(HTMaterialsAPI.INSTANCE.partManager::get)
                .toSet()
                .map(EntryStack::create),
        )
    }

    override fun getInputEntries(): List<List<EntryStack>> = listOf(entries)

    override fun getResultingEntries(): List<List<EntryStack>> = listOf(entries)

    override fun getRecipeCategory(): Identifier = HMReiPlugin.MATERIAL
}