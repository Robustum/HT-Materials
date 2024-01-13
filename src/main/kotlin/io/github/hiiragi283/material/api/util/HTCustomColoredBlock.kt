package io.github.hiiragi283.material.api.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.color.block.BlockColorProvider

@Environment(EnvType.CLIENT)
interface HTCustomColoredBlock {
    fun getColorProvider(): BlockColorProvider
}
