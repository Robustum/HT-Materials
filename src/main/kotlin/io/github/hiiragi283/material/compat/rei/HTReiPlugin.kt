package io.github.hiiragi283.material.compat.rei

import dev.architectury.fluid.FluidStack
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.common.HTMaterialsCommon
import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry
import me.shedaniel.rei.api.client.gui.widgets.Tooltip
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.registry.Registry

@Environment(EnvType.CLIENT)
@Suppress("UnstableApiUsage")
object HTReiPlugin : REIClientPlugin {

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

}