package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraftforge.event.AddReloadListenerEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber
object HTEventHandler {
    @Suppress("unused")
    @SubscribeEvent
    fun onAddReloadListener(event: AddReloadListenerEvent) {
        event.addListener(HTTagReloadListener)
        HTMaterialsAPI.log("Registered Reload Listener!")
    }

    @SubscribeEvent
    @Suppress("unused")
    fun onItemTooltip(event: ItemTooltipEvent) {
        val stack: ItemStack = event.itemStack
        if (stack.isEmpty) return
        val tooltip: MutableList<Text> = event.toolTip

        HTMaterialsAPI.INSTANCE.partManager().getPart(stack.item)?.let {
            it.getMaterial().appendTooltip(it.shapeKey, stack, tooltip)
        }

        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent { handler ->
            handler.let { (0..handler.tanks).map(handler::getFluidInTank) }
                .map(FluidStack::getFluid)
                .mapNotNull { HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(it) }
                .map(HTMaterialKey::getMaterial)
                .forEach { it.appendTooltip(null, stack, tooltip) }
        }
    }
}
