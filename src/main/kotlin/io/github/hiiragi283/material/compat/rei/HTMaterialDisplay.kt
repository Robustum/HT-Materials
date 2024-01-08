package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeDisplay
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class HTMaterialDisplay(val key: HTMaterialKey, val material: HTMaterial) : RecipeDisplay {

    constructor(pair: Map.Entry<HTMaterialKey, HTMaterial>) : this(pair.key, pair.value)

    val entries: List<EntryStack> = buildList {
        addAll(getItemEntries().map(EntryStack::create))
        addAll(getFluidEntries().map(EntryStack::create))
    }

    override fun getInputEntries(): List<List<EntryStack>> = listOf(entries)

    override fun getResultingEntries(): List<List<EntryStack>> = listOf(entries)

    override fun getRecipeCategory(): Identifier = HMReiPlugin.MATERIAL

    fun getEntries(): Collection<EntryStack> = buildList {
        addAll(getItemEntries().map(EntryStack::create))
        addAll(getFluidEntries().map(EntryStack::create))
    }

    fun getFluidEntries(): Collection<Fluid> = HTFluidManager.getFluids(key)

    fun getItemEntries(): Collection<Item> = HTPartManager.getPartToItemTable().row(key).values.flatten().toSet()

}