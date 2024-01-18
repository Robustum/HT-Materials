package io.github.hiiragi283.material.api.util

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler

interface HTCustomFluidRenderFluid {
    fun getFluidRenderHandler(): FluidRenderHandler
}
