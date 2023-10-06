package hiiragi283.material.compat.rei.category

import hiiragi283.material.compat.rei.HiiragiReiPlugin
import hiiragi283.material.compat.rei.display.HiiragiMaterialDisplay
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.item.Items

object HiiragiMaterialCategory : HiiragiDisplayCategory<HiiragiMaterialDisplay>() {

    override fun getCategoryIdentifier(): CategoryIdentifier<HiiragiMaterialDisplay> = HiiragiReiPlugin.MATERIAL

    override fun getIcon(): Renderer = EntryStacks.of(Items.IRON_INGOT)

    override fun setupDisplay(display: HiiragiMaterialDisplay, bounds: Rectangle): MutableList<Widget> = setupDisplay {

    }

}