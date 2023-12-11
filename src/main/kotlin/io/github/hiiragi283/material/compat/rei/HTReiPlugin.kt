package io.github.hiiragi283.material.compat.rei

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.common.HTMaterialsCommon
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object HTReiPlugin : REIClientPlugin {

    val MATERIAL_ID: CategoryIdentifier<HTMaterialDisplay> = CategoryIdentifier.of(HTMaterialsCommon.id("material"))

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(HTMaterialCategory)
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        HTMaterial.REGISTRY.iterator().forEach { registry.add(HTMaterialDisplay(it)) }
    }

    override fun registerEntries(registry: EntryRegistry) {

    }

}