package io.github.hiiragi283.material.api.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
interface HTCustomModelItem {
    fun getModelId(): Identifier
}
