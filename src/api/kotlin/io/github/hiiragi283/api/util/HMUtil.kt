@file:JvmName("HTUtil")

package io.github.hiiragi283.api.util

import com.google.common.collect.ImmutableCollection
import com.google.gson.JsonArray
import com.google.gson.JsonObject

//    Collection    //

fun <T : Any> ImmutableCollection.Builder<T>.addAll(vararg element: T) {
    element.forEach(this::add)
}

//    Json    //

fun buildJson(builderAction: JsonObject.() -> Unit): JsonObject = JsonObject().apply(builderAction)

fun JsonObject.addArray(property: String, builderAction: JsonArray.() -> Unit) {
    this.add(property, JsonArray().apply(builderAction))
}

fun JsonObject.addObject(property: String, builderAction: JsonObject.() -> Unit) {
    this.add(property, buildJson(builderAction))
}
