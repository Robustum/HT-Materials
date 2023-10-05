package hiiragi283.material.compat.rei.client

import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object HiiragiReiClientPlugin : REIClientPlugin {

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(HiiragiMaterialCategory)
    }

}