package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.fluid.FluidState
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView

@Suppress("DEPRECATION")
@Environment(EnvType.CLIENT)
class HTMaterialFluidRenderHandler(val material: HTMaterial) : FluidRenderHandler {

    constructor(fluid: HTMaterialFluid) : this(fluid.material)

    private var sprites: Array<Sprite> = arrayOf()

    private fun getSprite(id: Identifier): Sprite =
        MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(id)

    override fun getFluidSprites(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Array<Sprite> {
        if (sprites.isEmpty()) {
            sprites = arrayOf(
                getSprite(Identifier("minecraft:block/white_concrete")),
                getSprite(Identifier("minecraft:block/white_concrete"))
            )
        }
        return sprites
    }

    override fun getFluidColor(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Int = material.asColor().rgb

}