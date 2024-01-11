package io.github.hiiragi283.material.api.client

import net.minecraft.client.color.block.BlockColorProvider

interface HTColoredMaterialBlock {

    fun getColorProvider(): BlockColorProvider

}