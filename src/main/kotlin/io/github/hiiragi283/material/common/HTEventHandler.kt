package io.github.hiiragi283.material.common

import io.github.hiiragi283.material.api.part.HTPartManager
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

object HTEventHandler {

    fun registerCommon() {
        HTPartManager.registerEvent()
    }

    fun registerClient() {

        ItemTooltipCallback.EVENT.register { stack: ItemStack, context: TooltipContext, lines: MutableList<Text> ->
            HTPartManager.ITEM_TO_PART[stack.item]?.let { }
        }

    }

}