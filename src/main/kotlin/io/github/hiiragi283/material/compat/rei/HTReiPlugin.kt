package io.github.hiiragi283.material.compat.rei

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
@Suppress("UnstableApiUsage")
object HTReiPlugin /*: REIClientPlugin {

    val MATERIAL_ID: CategoryIdentifier<HTMaterialDisplay> = CategoryIdentifier.of(HTMaterialsCommon.id("material"))

    override fun registerEntryRenderers(registry: EntryRendererRegistry) {
        registry.transformTooltip(VanillaEntryTypes.FLUID) { fluidStack: EntryStack<FluidStack>, _, tooltip: Tooltip? ->
            HTMaterial.getMaterial(Registry.FLUID.getId(fluidStack.value.fluid).path)?.run {
                val tooltipDummy: MutableList<Text> = mutableListOf()
                HTPart(this, HTShapes.FLUID).appendTooltip(ItemStack.EMPTY, tooltipDummy)
                tooltip?.addAllTexts(tooltipDummy)
            }
            return@transformTooltip tooltip
        }
    }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(HTMaterialCategory)
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        HTMaterial.REGISTRY.iterator().forEach { registry.add(HTMaterialDisplay(it)) }
    }

    override fun registerEntries(registry: EntryRegistry) {

    }

}*/