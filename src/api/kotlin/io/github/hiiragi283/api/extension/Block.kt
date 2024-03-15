package io.github.hiiragi283.api.extension

import net.minecraft.block.Block
import net.minecraft.block.Blocks

val Block.isAir: Boolean
    get() = this == Blocks.AIR

fun Block.nonAirOrNull(): Block? = takeUnless { isAir }
