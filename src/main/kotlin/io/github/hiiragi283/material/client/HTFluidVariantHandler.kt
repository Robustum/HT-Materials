package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.material.HTMaterialKey
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.text.Text

@Suppress("UnstableApiUsage")
class HTFluidVariantHandler(val materialKey: HTMaterialKey) : FluidVariantRenderHandler {

    override fun getName(fluidVariant: FluidVariant): Text = materialKey.getTranslatedText()

}