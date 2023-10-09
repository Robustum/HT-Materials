package hiiragi283.material.api.item

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.shape.HiiragiShape
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipData

@Environment(EnvType.CLIENT)
class MaterialTooltipData(val part: HiiragiPart) : TooltipData {

    fun getShape(): HiiragiShape = part.shape

    fun getMaterial(): HiiragiMaterial = part.material

}