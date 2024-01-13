package io.github.hiiragi283.material.api.util

import io.github.hiiragi283.material.api.material.HTMaterialKey
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
@Suppress("UnstableApiUsage")
class HTFluidVariantHandler(val materialKey: HTMaterialKey) : FluidVariantRenderHandler {

    override fun getName(fluidVariant: FluidVariant): Text = materialKey.getTranslatedText()

}