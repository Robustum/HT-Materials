package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.fluid.Fluid

@Suppress("UnstableApiUsage")
class HTFluidProperty : HTMaterialProperty<HTFluidProperty> {

    var temperature: Int = 300
    var viscosity: Int = 1000
    var isGas: Boolean = false

    override val key: HTPropertyKey<HTFluidProperty> = HTPropertyKey.FLUID

    override fun verify(material: HTMaterial) {

    }

    private lateinit var fluid: Fluid

    private val fluidVariant: FluidVariant by lazy { FluidVariant.of(fluid) }

    /*private val handlerCache: FluidVariantAttributeHandler by lazy { FluidVariantAttributes.getHandlerOrDefault(fluid) }

    override fun appendTooltip(material: HTMaterial, shape: HTShape?, stack: ItemStack, lines: MutableList<Text>) {
        //Luminance
        handlerCache.getLuminance(fluidVariant).run {
            lines.add(TranslatableText("tooltip.ht_materials.material.luminance", this))
        }
        //Temperature
        handlerCache.getTemperature(fluidVariant).run {
            lines.add(TranslatableText("tooltip.ht_materials.material.temperature", this))
        }
        //Viscosity
        handlerCache.getViscosity(fluidVariant, null).run {
            lines.add(TranslatableText("tooltip.ht_materials.material.viscosity", this))
        }
        //Is gas
        val type: String = if (handlerCache.isLighterThanAir(fluidVariant)) "gas" else "fluid"
        val key = "tooltip.ht_materials.material.state.$type"
        lines.add(TranslatableText("tooltip.ht_materials.material.state", TranslatableText(key)))
    }*/

    internal fun init(key: HTMaterialKey) {
        if (this::fluid.isInitialized) return
        HTMaterialFluid.Flowing(key)
        HTMaterialFluid.Still(key).run {
            HTMaterialFluid.Bucket(this)
            /*FluidVariantAttributes.register(this, object : FluidVariantAttributeHandler {

                override fun getName(fluidVariant: FluidVariant): Text = key.getTranslatedText()

                override fun getFillSound(variant: FluidVariant): Optional<SoundEvent> =
                    Optional.of(SoundEvents.ITEM_BUCKET_FILL_LAVA)
                        .filter { temperature > FluidConstants.WATER_TEMPERATURE }

                override fun getEmptySound(variant: FluidVariant): Optional<SoundEvent> =
                    Optional.of(SoundEvents.ITEM_BUCKET_EMPTY_LAVA)
                        .filter { temperature > FluidConstants.WATER_TEMPERATURE }

                override fun getTemperature(variant: FluidVariant): Int = temperature

                override fun getViscosity(variant: FluidVariant, world: World?): Int = viscosity

                override fun isLighterThanAir(variant: FluidVariant): Boolean = isGas

            })*/
            fluid = HTFluidManager.getDefaultFluid(key) ?: this
        }
    }

}