package hiiragi283.material.compat.rei.client

import hiiragi283.material.RagiMaterials
import hiiragi283.material.util.hiiragiId
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

abstract class HiiragiDisplayCategory<T : Display> : DisplayCategory<T> {

    abstract val categoryName: String

    @JvmField
    val categoryIdentifier = CategoryIdentifier<T> { hiiragiId(categoryName) }

    override fun getCategoryIdentifier(): CategoryIdentifier<T> = categoryIdentifier

    override fun getTitle(): Text = TranslatableText("gui.${RagiMaterials.MOD_ID}.$categoryName.name")

    fun setupDisplay(init: MutableList<Widget>.() -> Unit): MutableList<Widget> {
        val list: MutableList<Widget> = mutableListOf()
        list.init()
        return list
    }

}