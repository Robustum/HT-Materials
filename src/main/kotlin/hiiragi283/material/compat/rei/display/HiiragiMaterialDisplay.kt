package hiiragi283.material.compat.rei.display

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.compat.rei.HiiragiReiPlugin
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryStacks

class HiiragiMaterialDisplay(val material: HiiragiMaterial) : HiiragiDisplay() {

    override fun getInputEntries(): MutableList<EntryIngredient> =
        mutableListOf(EntryIngredient.of(material.getItems().map(EntryStacks::of)))

    override fun getOutputEntries(): MutableList<EntryIngredient> = mutableListOf()

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = HiiragiReiPlugin.MATERIAL

}