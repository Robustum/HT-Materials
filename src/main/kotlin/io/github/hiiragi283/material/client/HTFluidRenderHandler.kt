package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.material.HTMaterial
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.fluid.FluidState
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView

@Suppress("DEPRECATION")
class HTFluidRenderHandler(val material: HTMaterial) : FluidRenderHandler {

    private lateinit var sprites: Array<Sprite>

    override fun getFluidSprites(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Array<Sprite> {
        if (!::sprites.isInitialized) {
            MinecraftClient.getInstance()
                .getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
                .apply(Identifier("block/white_concrete")).run { sprites = arrayOf(this, this) }
        }
        return sprites
    }

    override fun getFluidColor(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Int = material.color.rgb

}