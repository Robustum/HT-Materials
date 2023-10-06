package hiiragi283.material.compat.rei.category

import hiiragi283.material.compat.rei.display.HiiragiDisplay
import hiiragi283.material.compat.rei.getTitle
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import net.minecraft.text.Text

abstract class HiiragiDisplayCategory<T : HiiragiDisplay> : DisplayCategory<T> {

    override fun getTitle(): Text = categoryIdentifier.getTitle()

    fun setupDisplay(init: MutableList<Widget>.() -> Unit): MutableList<Widget> {
        val list: MutableList<Widget> = mutableListOf()
        list.init()
        return list
    }

}