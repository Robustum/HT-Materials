package io.github.hiiragi283.material.api.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler

interface HTCustomFluidRenderFluid {
    @Environment(EnvType.CLIENT)
    fun getFluidRenderHandler(): FluidRenderHandler
}
