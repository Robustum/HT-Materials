package io.github.hiiragi283.material.dictionary

import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class MaterialDictionaryScreen(
    handler: MaterialDictionaryScreenHandler,
    playerInventory: PlayerInventory,
    title: Text,
) : HandledScreen<MaterialDictionaryScreenHandler>(handler, playerInventory, title) {
    override fun drawBackground(
        matrices: MatrixStack,
        delta: Float,
        mouseX: Int,
        mouseY: Int,
    ) {
    }
}
