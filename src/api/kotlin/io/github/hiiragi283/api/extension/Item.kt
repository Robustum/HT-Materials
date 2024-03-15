package io.github.hiiragi283.api.extension

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items

val Item.asBlock: Block
    get() = (this as? BlockItem)?.block ?: Blocks.AIR

val Item.isAir: Boolean
    get() = this == Items.AIR

val Item.nonAirOrNull: Item?
    get() = takeUnless { isAir }

//    ItemConvertible    //

val ItemConvertible.asBlock: Block
    get() = asItem().asBlock

val ItemConvertible.isAir: Boolean
    get() = asItem().isAir

val ItemConvertible.nonAirOrNull: Item?
    get() = asItem().nonAirOrNull
