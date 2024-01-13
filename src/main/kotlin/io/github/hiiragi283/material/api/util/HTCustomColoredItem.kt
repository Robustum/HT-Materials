package io.github.hiiragi283.material.api.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.color.item.ItemColorProvider

@Environment(EnvType.CLIENT)
interface HTCustomColoredItem {

    fun getColorProvider(): ItemColorProvider

}