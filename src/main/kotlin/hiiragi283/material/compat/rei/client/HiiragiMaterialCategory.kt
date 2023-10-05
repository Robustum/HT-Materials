package hiiragi283.material.compat.rei.client

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.compat.rei.entryStackOf
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.item.Items

object HiiragiMaterialCategory : HiiragiDisplayCategory<HiiragiMaterialCategory.Wrapper>() {

    val MATERIAL = categoryIdentifier

    override val categoryName: String = "material"

    override fun getIcon(): Renderer = EntryStacks.of(Items.IRON_INGOT)

    override fun setupDisplay(display: Wrapper, bounds: Rectangle): MutableList<Widget> = setupDisplay {

    }

    class Wrapper(val material: HiiragiMaterial) : Display {

        override fun getInputEntries(): MutableList<EntryIngredient> =
            mutableListOf(EntryIngredient.of(material.getItems().map(EntryStacks::of)))

        override fun getOutputEntries(): MutableList<EntryIngredient> =
            mutableListOf(EntryIngredient.of(entryStackOf(material)))

        override fun getCategoryIdentifier(): CategoryIdentifier<*> = MATERIAL

    }

}