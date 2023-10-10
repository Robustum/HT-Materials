package hiiragi283.material.api.fluid

import hiiragi283.material.api.material.HiiragiMaterial
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import java.util.*

@Suppress("UnstableApiUsage")
class MaterialFluidAttributeHandler(val material: HiiragiMaterial) : FluidVariantAttributeHandler {

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

}