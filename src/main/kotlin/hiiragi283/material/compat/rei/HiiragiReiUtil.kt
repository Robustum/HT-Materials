package hiiragi283.material.compat.rei

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.material.MaterialStack
import hiiragi283.material.compat.rei.server.HiiragiEntryTypes
import me.shedaniel.rei.api.common.entry.EntryStack

fun entryStackOf(materialStack: MaterialStack): EntryStack<MaterialStack> =
    EntryStack.of(HiiragiEntryTypes.MATERIAL, materialStack)

fun entryStackOf(material: HiiragiMaterial) = entryStackOf(MaterialStack(material, 0))