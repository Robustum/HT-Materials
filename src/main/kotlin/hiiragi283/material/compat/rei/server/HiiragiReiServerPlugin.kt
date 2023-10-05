package hiiragi283.material.compat.rei.server

import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry
import me.shedaniel.rei.api.common.plugins.REIServerPlugin

object HiiragiReiServerPlugin : REIServerPlugin {

    override fun registerEntryTypes(registry: EntryTypeRegistry) {
        registry.register(HiiragiEntryTypes.MATERIAL, MaterialEntryDefinition)
    }

}