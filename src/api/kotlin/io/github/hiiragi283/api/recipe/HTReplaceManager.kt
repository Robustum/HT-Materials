package io.github.hiiragi283.api.recipe

import io.github.hiiragi283.api.extention.nonAirOrNull
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

object HTReplaceManager {
    private val inputReplaceMap: MutableMap<Item, Boolean> = hashMapOf()

    private val outputReplaceMap: MutableMap<Item, Boolean> = hashMapOf()

    fun enabledInputReplace(itemConvertible: ItemConvertible): Boolean = inputReplaceMap.getOrDefault(itemConvertible.asItem(), true)

    fun enabledOutputReplace(itemConvertible: ItemConvertible): Boolean = outputReplaceMap.getOrDefault(itemConvertible.asItem(), true)

    fun setReplaceCondition(itemConvertible: ItemConvertible, inputReplace: Boolean, outputReplace: Boolean) {
        itemConvertible.nonAirOrNull()?.run {
            inputReplaceMap[this] = inputReplace
            outputReplaceMap[this] = outputReplace
        }
    }
}
