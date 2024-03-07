package io.github.hiiragi283.api.fluid

import net.minecraft.fluid.Fluid
import net.minecraft.tag.Tag

interface Fluid2ObjectMap<V> {
    operator fun get(fluid: Fluid): V

    fun add(fluid: Fluid, value: V)

    fun add(tag: Tag<Fluid>, value: V)

    fun remove(fluid: Fluid)

    fun remove(tag: Tag<Fluid>)

    fun clear(fluid: Fluid)

    fun clear(tag: Tag<Fluid>)
}
