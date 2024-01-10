package io.github.hiiragi283.material.client

import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.ItemConvertible

interface HTColoredMaterialItem : ItemConvertible {

    fun getColorProvider(): ItemColorProvider

}