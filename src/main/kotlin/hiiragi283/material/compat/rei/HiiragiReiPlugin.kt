package hiiragi283.material.compat.rei

import hiiragi283.material.compat.rei.category.HiiragiMaterialCategory
import hiiragi283.material.compat.rei.display.HiiragiMaterialDisplay
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.util.hiiragiId
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object HiiragiReiPlugin : REIClientPlugin {

    val MATERIAL = CategoryIdentifier<HiiragiMaterialDisplay> { hiiragiId("material") }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(HiiragiMaterialCategory)
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        //Material
        HiiragiRegistries.MATERIAL.getValues()
            .map(::HiiragiMaterialDisplay)
            .forEach(registry::add)
    }

}