package hiiragi283.material.api.gui

import hiiragi283.material.api.part.HiiragiPart
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.tooltip.TooltipComponent

@Environment(EnvType.CLIENT)
class MaterialTooltipComponent(val part: HiiragiPart) : TooltipComponent {

    override fun getHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun getWidth(textRenderer: TextRenderer?): Int {
        TODO("Not yet implemented")
    }

}