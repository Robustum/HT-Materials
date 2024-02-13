package io.github.hiiragi283.api.extention

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

val Item.id: Identifier
    get() = Registry.ITEM.getId(this)

fun Item.asBlock(): Block = (this as? BlockItem)?.block ?: Blocks.AIR

fun Item.isAir(): Boolean = this == Items.AIR

fun Item.nonAirOrNull(): Item? = takeUnless { isAir() }

//    ItemConvertible    //

fun ItemConvertible.asBlock() = asItem().asBlock()

fun ItemConvertible.isAir(): Boolean = asItem().isAir()

fun ItemConvertible.nonAirOrNull(): Item? = asItem().nonAirOrNull()
