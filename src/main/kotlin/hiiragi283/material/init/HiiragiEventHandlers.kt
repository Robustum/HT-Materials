package hiiragi283.material.init

import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.part.getParts
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.client.item.TooltipContext
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
        RagiMaterials.LOGGER.info("All Client Events have been registered!")
    }

}