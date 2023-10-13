package hiiragi283.material.client.model

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

@Environment(EnvType.CLIENT)
object TestBlockModel : HiiragiBakedModel() {

    override val spriteIds: MutableList<SpriteIdentifier> = mutableListOf(
        SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier("block/furnace_front_on")),
        SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier("block/furnace_top"))
    )

    //    HiiragiBakedModel    //

    override fun emit(quadEmitter: QuadEmitter, direction: Direction) {
        val spriteIndex: Int = if (direction.axis == Direction.Axis.Y) 1 else 0
        quadEmitter.square(direction, 0f, 0f, 1f, 1f, 0f)
        quadEmitter.spriteBake(0, sprites[spriteIndex], MutableQuadView.BAKE_LOCK_UV)
        quadEmitter.spriteColor(0, -1, -1, -1, -1)
        quadEmitter.emit()
    }

}