@file:Suppress("UnstableApiUsage")

package hiiragi283.material.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.item.Item
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

@Environment(EnvType.CLIENT)
fun Item.putAltModel(modelIdentifier: ModelIdentifier) {
    MinecraftClient.getInstance().itemRenderer.models.putModel(this, modelIdentifier)
}

fun getTest() {
    val content: CauldronFluidContent = CauldronFluidContent.getForBlock(Blocks.WATER_CAULDRON) ?: return
    content.currentLevel(Blocks.WATER_CAULDRON.defaultState.with(Properties.LEVEL_3, 3))
}

fun getCauldronAmount(world: BlockView, pos: BlockPos): Long {
    val state: BlockState = world.getBlockState(pos)
    val content: CauldronFluidContent = CauldronFluidContent.getForBlock(state.block) ?: return 0
    return content.currentLevel(state) * content.amountPerLevel
}