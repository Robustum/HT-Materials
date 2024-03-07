package io.github.hiiragi283.api.extension

import io.github.hiiragi283.api.fluid.Fluid2ObjectMap
import net.fabricmc.fabric.api.util.Block2ObjectMap
import net.fabricmc.fabric.api.util.Item2ObjectMap
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.Tag

//    Block    //

operator fun <V> Block2ObjectMap<V>.set(block: Block, value: V) = add(block, value)

operator fun <V> Block2ObjectMap<V>.set(tag: Tag<Block>, value: V) = add(tag, value)

//    Item    //

operator fun <V> Item2ObjectMap<V>.set(item: ItemConvertible, value: V) = add(item, value)

operator fun <V> Item2ObjectMap<V>.set(tag: Tag<Item>, value: V) = add(tag, value)

//    Fluid    //

operator fun <V> Fluid2ObjectMap<V>.set(fluid: Fluid, value: V) = add(fluid, value)

operator fun <V> Fluid2ObjectMap<V>.set(tag: Tag<Fluid>, value: V) = add(tag, value)
