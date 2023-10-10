package hiiragi283.material.api.fluid

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiRegistries
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.*

@Suppress("UnstableApiUsage")
class MaterialFluid(val material: HiiragiMaterial) : HiiragiFlowableFluid() {

    override fun getStill(): Fluid = this

    override fun getFlowing(): Fluid = this

    override fun isStill(state: FluidState): Boolean = true

    override fun getLevel(state: FluidState): Int = 0

    //    Entry    //

    fun register() {
        HiiragiRegistries.FLUID.register(material.name, this)
    }

    override fun onRegister(name: String) {
        //Register Fluid Attributes
        FluidVariantAttributes.register(this, object : FluidVariantAttributeHandler {

            override fun getName(fluidVariant: FluidVariant): Text = material.getText()

            override fun getFillSound(variant: FluidVariant): Optional<SoundEvent> = Optional.of(
                if (material.tempMelt > FluidConstants.WATER_TEMPERATURE)
                    SoundEvents.ITEM_BUCKET_FILL_LAVA
                else SoundEvents.ITEM_BUCKET_FILL
            )

            override fun getEmptySound(variant: FluidVariant): Optional<SoundEvent> = Optional.of(
                if (material.tempMelt > FluidConstants.WATER_TEMPERATURE)
                    SoundEvents.ITEM_BUCKET_EMPTY_LAVA
                else SoundEvents.ITEM_BUCKET_EMPTY
            )

            override fun getTemperature(variant: FluidVariant): Int {
                return if (material.isSolid()) {
                    if (material.hasTempMelt()) {
                        material.tempMelt
                    } else FluidConstants.LAVA_TEMPERATURE
                } else super.getTemperature(variant)
            }

            override fun isLighterThanAir(variant: FluidVariant): Boolean = material.isGas()

        })
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