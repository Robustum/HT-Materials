package hiiragi283.material.api.material

import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.part.HiiragiPart
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier

interface MaterialItemProvider : ItemConvertible {

    val part: HiiragiPart

    fun getIdentifier(): Identifier = RagiMaterials.id(part.tagPath)

}