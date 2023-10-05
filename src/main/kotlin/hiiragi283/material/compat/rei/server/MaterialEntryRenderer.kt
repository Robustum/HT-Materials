package hiiragi283.material.compat.rei.server

import hiiragi283.material.api.material.MaterialStack
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer
import me.shedaniel.rei.api.client.util.SpriteRenderer
import me.shedaniel.rei.api.common.entry.EntryStack
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.texture.Sprite
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.util.Identifier

object MaterialEntryRenderer : EntryRenderer<MaterialStack> {

    override fun render(
        entry: EntryStack<MaterialStack>,
        matrices: MatrixStack,
        bounds: Rectangle,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
        val materialStack: MaterialStack = entry.value
        if (materialStack.isEmpty()) return

        val sprite: Sprite = MinecraftClient.getInstance()
            .bakedModelManager
            .getAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)
            .getSprite(Identifier("block/white_concrete_block"))

        val immediate: VertexConsumerProvider.Immediate =
            MinecraftClient.getInstance().bufferBuilders.entityVertexConsumers

        SpriteRenderer.beginPass()
            .setup(immediate, RenderLayer.getSolid())
            .sprite(sprite)
            .color(materialStack.material.color)
            .light(0x00f000f0)
            .overlay(OverlayTexture.DEFAULT_UV)
            .alpha(0xff)
            .normal(matrices.peek().normalMatrix, 0f, 0f, 0f)
            .position(
                matrices.peek().positionMatrix,
                bounds.x.toFloat(),
                bounds.x.toFloat(),
                bounds.maxX.toFloat(),
                bounds.maxY.toFloat(),
                entry.z.toFloat()
            )
            .next(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)

        immediate.draw()

    }

}