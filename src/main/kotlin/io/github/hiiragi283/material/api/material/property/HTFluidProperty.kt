package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class HTFluidProperty : HTMaterialProperty<HTFluidProperty> {

    lateinit var fluid: Fluid

    var attribute = AttributeHandler()

    override val key: HTPropertyKey<HTFluidProperty> = HTPropertyKey.FLUID

    override fun verify(material: HTMaterial) {

    }

    override fun appendTooltip(part: HTPart, stack: ItemStack, lines: MutableList<Text>) {
        //Temperature
        lines.add(
            TranslatableText(
                "tooltip.ht_materials.material.temperature",
                attribute.temperature
            )
        )
        //Liquid or gas
        val key: String = "tooltip.ht_materials.material.state.%s".let {
            if (attribute.isGas) it.format("gas") else it.format("fluid")
        }
        lines.add(TranslatableText("tooltip.ht_materials.material.state", TranslatableText(key)))
    }

    internal fun init(material: HTMaterial) {
        if (this::fluid.isInitialized) return
        HTMaterialFluid.Flowing(material)
        HTMaterialFluid.Still(material).run {
            HTMaterialFluid.Bucket(this)
            HTMaterialFluid.Block(this)
            fluid = this
        }
    }

    //    Attribute    //

    class AttributeHandler internal constructor() {

        var temperature: Int = 273
        var isGas: Boolean = false

        /*override fun getFillSound(variant: FluidVariant): Optional<SoundEvent> =
            Optional.of(SoundEvents.ITEM_BUCKET_FILL_LAVA).filter { temperature > FluidConstants.WATER_TEMPERATURE }

        override fun getEmptySound(variant: FluidVariant): Optional<SoundEvent> =
            Optional.of(SoundEvents.ITEM_BUCKET_EMPTY_LAVA).filter { temperature > FluidConstants.WATER_TEMPERATURE }

        override fun getTemperature(variant: FluidVariant): Int = temperature

        override fun getViscosity(variant: FluidVariant, world: World?): Int = viscosity

        override fun isLighterThanAir(variant: FluidVariant): Boolean = isGas*/

    }

}