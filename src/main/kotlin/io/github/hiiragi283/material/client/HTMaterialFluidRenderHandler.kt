package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.minecraft.client.texture.Sprite
import net.minecraft.fluid.FluidState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView

@Environment(EnvType.CLIENT)
class HTMaterialFluidRenderHandler(val material: HTMaterial) : FluidRenderHandler {

    constructor(fluid: HTMaterialFluid) : this(fluid.material)

    override fun getFluidSprites(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Array<Sprite> = arrayOf()

    override fun getFluidColor(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Int = material.asColor().rgb

}