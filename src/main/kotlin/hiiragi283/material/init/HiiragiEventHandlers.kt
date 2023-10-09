package hiiragi283.material.init

import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.gui.MaterialTooltipComponent
import hiiragi283.material.api.item.MaterialTooltipData
import hiiragi283.material.api.part.getParts
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.TooltipData
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

object HiiragiEventHandlers {

    fun register() {

        RagiMaterials.LOGGER.info("All Common Events have been registered!")
    }

    @Environment(EnvType.CLIENT)
    fun registerClient() {
        //Material Item Tooltip
        ItemTooltipCallback.EVENT.register(
            ItemTooltipCallback { stack: ItemStack, _: TooltipContext, lines: MutableList<Text> ->
                stack.getParts().forEach { it.appendTooltip(lines) }
            }
        )
        //Material Item TooltipData
        TooltipComponentCallback.EVENT.register(TooltipComponentCallback(::getTooltipComponent))
        RagiMaterials.LOGGER.info("All Client Events have been registered!")
    }

    private fun getTooltipComponent(data: TooltipData): TooltipComponent? = when (data) {
        is MaterialTooltipData -> MaterialTooltipComponent(data.part)
        else -> null
    }

}