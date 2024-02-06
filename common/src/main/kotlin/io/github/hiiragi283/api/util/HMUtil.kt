@file:JvmName("HTUtil")

package io.github.hiiragi283.api.util

import com.google.common.collect.ImmutableCollection
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluid
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import java.util.*
import java.util.function.Function

//    Block    //

fun Item.asBlock(): Block = (this as? BlockItem)?.block ?: Blocks.AIR

fun Block.isAir(): Boolean = this == Blocks.AIR

//    Collection    //

fun <T : Any> ImmutableCollection.Builder<T>.addAll(vararg element: T) {
    element.forEach(this::add)
}

//    Fluid    //

fun Fluid.asBlock(): Block = this.defaultState.blockState.block

//    Item   //

fun Item.isAir(): Boolean = this == Items.AIR

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun Identifier.prefix(prefix: String) = Identifier(this.namespace, prefix + this.path)

fun Identifier.suffix(suffix: String) = Identifier(this.namespace, this.path + suffix)

fun Identifier.modify(function: Function<String, String>) = Identifier(this.namespace, function.apply(this.path))

//    Json    //

fun buildJson(builderAction: JsonObject.() -> Unit): JsonObject = JsonObject().apply(builderAction)

fun JsonObject.addArray(property: String, builderAction: JsonArray.() -> Unit) {
    this.add(property, JsonArray().apply(builderAction))
}

fun JsonObject.addObject(property: String, builderAction: JsonObject.() -> Unit) {
    this.add(property, buildJson(builderAction))
}

//    Service    //

inline fun <reified T> getInstance(): T {
    val list: List<T> = ServiceLoader.load(T::class.java).toList()
    return when {
        list.isEmpty() -> throw IllegalStateException()
        list.size != 1 -> throw IllegalStateException()
        else -> list[0]
    }
}
