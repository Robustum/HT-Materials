package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.material.common.HTMaterialsCommon
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.text.LiteralText
import net.minecraft.text.Text

object HTMaterialCategory : DisplayCategory<HTMaterialDisplay> {

    override fun getCategoryIdentifier(): CategoryIdentifier<HTMaterialDisplay> = HTReiPlugin.MATERIAL_ID

    override fun getTitle(): Text = LiteralText(HTMaterialsCommon.MOD_NAME)

    override fun getIcon(): Renderer = EntryStacks.of(HTMaterialsCommon.ICON)

    override fun setupDisplay(display: HTMaterialDisplay, bounds: Rectangle): MutableList<Widget> {
        val startPoint = Point(bounds.centerX, bounds.centerY)
        val widgets: MutableList<Widget> = mutableListOf()
        return widgets
    }

}