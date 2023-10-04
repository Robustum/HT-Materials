package hiiragi283.material.util

import net.minecraft.block.BlockState
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView
import java.awt.Color

class SimpleColorProvider(private val color: Int) : BlockColorProvider, ItemColorProvider {

    constructor(color: Color) : this(color.rgb)

    override fun getColor(state: BlockState?, world: BlockRenderView?, pos: BlockPos?, tintIndex: Int): Int = color

    override fun getColor(stack: ItemStack, tintIndex: Int): Int = color

    companion object {
        @JvmStatic
        fun <T> of(obj: T, function: (T) -> Int) = SimpleColorProvider(function(obj))

    }

}