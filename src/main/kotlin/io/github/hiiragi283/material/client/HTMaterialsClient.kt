package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.item.MaterialItem
import io.github.hiiragi283.material.api.item.MaterialItemConvertible
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.common.HTEventHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.ItemConvertible

object HTMaterialsClient : ClientModInitializer {

    override fun onInitializeClient() {

        HTEventHandler.registerClient()

        MaterialItemConvertible.ITEM_TO_PART.forEach { (item: ItemConvertible, part: HTPart) ->
            if (item is MaterialItem) {
                ColorProviderRegistry.ITEM.register(ItemColorProvider { stack, tintIndex ->
                    //part.shape.itemColorProvider.apply(part.material, stack, tintIndex)
                    -1
                })
            }
        }

    }

}