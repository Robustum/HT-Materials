package hiiragi283.material.api.fluid

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiRegistries
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.util.Identifier

class MaterialFluid(val material: HiiragiMaterial) : HiiragiFlowableFluid() {

    override fun getStill(): Fluid = this

    override fun getFlowing(): Fluid = this

    override fun isStill(state: FluidState): Boolean = true

    override fun getLevel(state: FluidState): Int = 0

    //    Entry    //

    fun register() {
        HiiragiRegistries.FLUID.register(material.name, this)
    }

    @Environment(EnvType.CLIENT)
    override fun onRegisterClient() {
        FluidRenderHandlerRegistry.INSTANCE.register(
            getStill(),
            getFlowing(),
            SimpleFluidRenderHandler(
                Identifier("block/white_concrete"),
                Identifier("block/white_concrete"),
                material.color
            )
        )
    }


}