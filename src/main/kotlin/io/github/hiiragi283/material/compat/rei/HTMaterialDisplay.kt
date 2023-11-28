package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryIngredients

class HTMaterialDisplay(val material: HTMaterial) : Display {

    private val entries: EntryIngredient =
        EntryIngredients.ofItems(HTShape.REGISTRY.flatMap { HTPartManager.getItems(material, it) })

    override fun getInputEntries(): MutableList<EntryIngredient> = mutableListOf(entries)

    override fun getOutputEntries(): MutableList<EntryIngredient> = mutableListOf(entries)

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = HTReiPlugin.MATERIAL_ID

}