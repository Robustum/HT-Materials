package hiiragi283.material.compat.rei.display

import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.common.display.Display

abstract class HiiragiDisplay : Display {

    fun register(registry: DisplayRegistry) {
        registry.add(this)
    }

}