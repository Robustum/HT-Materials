package hiiragi283.material.util

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.part.HiiragiPart
import net.minecraft.block.BlockState
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView
import java.awt.Color

class SimpleColorProvider(private val color: Int) : BlockColorProvider, ItemColorProvider {

    constructor(color: Color) : this(color.rgb)

    constructor(material: HiiragiMaterial) : this(material.color)

    constructor(part: HiiragiPart) : this(part.material)

    override fun getColor(state: BlockState?, world: BlockRenderView?, pos: BlockPos?, tintIndex: Int): Int = color
    override fun getColor(stack: ItemStack, tintIndex: Int): Int = color

}