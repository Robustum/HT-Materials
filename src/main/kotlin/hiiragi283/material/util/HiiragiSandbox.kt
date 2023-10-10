package hiiragi283.material.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.item.Item

@Environment(EnvType.CLIENT)
fun Item.putAltModel(modelIdentifier: ModelIdentifier) {
    MinecraftClient.getInstance().itemRenderer.models.putModel(this, modelIdentifier)
}