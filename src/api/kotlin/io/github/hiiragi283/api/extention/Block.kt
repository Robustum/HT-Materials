package io.github.hiiragi283.api.extention

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

val Block.id: Identifier
    get() = Registry.BLOCK.getId(this)

fun Block.isAir(): Boolean = this == Blocks.AIR

fun Block.nonAirOrNull(): Block? = takeUnless { isAir() }