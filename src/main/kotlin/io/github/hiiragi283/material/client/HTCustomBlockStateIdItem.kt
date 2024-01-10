package io.github.hiiragi283.material.client

import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier

interface HTCustomBlockStateIdItem : ItemConvertible {

    fun getBlockStateId(): Identifier

}