package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeDisplay
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class HTMaterialDisplay(val material: HTMaterial) : RecipeDisplay {
    private val key: HTMaterialKey = material.key
    val entries: List<EntryStack> = buildList {
        addAll(getItemEntries().map(EntryStack::create))
        addAll(getFluidEntries().map(EntryStack::create))
    }

    override fun getInputEntries(): List<List<EntryStack>> = listOf(entries)

    override fun getResultingEntries(): List<List<EntryStack>> = listOf(entries)

    override fun getRecipeCategory(): Identifier = HMReiPlugin.MATERIAL

    private fun getFluidEntries(): Collection<Fluid> = HTMaterialsAPI.INSTANCE.fluidManager()
        .getFluids(key)

    private fun getItemEntries(): Collection<Item> = HTMaterialsAPI.INSTANCE.partManager()
        .getAllEntries()
        .filter { it.materialKey == key }
        .map { it.item }
}