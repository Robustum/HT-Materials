package hiiragi283.material.api.material

import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.util.hiiragiId
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier

interface MaterialItemProvider : ItemConvertible {

    val part: HiiragiPart

    fun getIdentifier(): Identifier = hiiragiId(part.tagPath)

}