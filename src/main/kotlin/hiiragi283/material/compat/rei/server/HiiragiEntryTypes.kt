package hiiragi283.material.compat.rei.server

import hiiragi283.material.api.material.MaterialStack
import hiiragi283.material.util.hiiragiId
import me.shedaniel.rei.api.common.entry.type.EntryType

object HiiragiEntryTypes {

    val MATERIAL: EntryType<MaterialStack> = EntryType.deferred(hiiragiId("material"))

}