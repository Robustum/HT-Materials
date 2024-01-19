package io.github.hiiragi283.material.api.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.color.item.ItemColorProvider

interface HTCustomColoredItem {
    @Environment(EnvType.CLIENT)
    fun getColorProvider(): ItemColorProvider
}
