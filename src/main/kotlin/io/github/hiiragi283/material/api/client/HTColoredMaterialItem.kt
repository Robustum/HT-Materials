package io.github.hiiragi283.material.api.client

import net.minecraft.client.color.item.ItemColorProvider

interface HTColoredMaterialItem {

    fun getColorProvider(): ItemColorProvider

}