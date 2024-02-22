@file:JvmName("HTUtil")

package io.github.hiiragi283.api.util

import com.google.gson.JsonArray
import com.google.gson.JsonObject

//    Json    //

fun buildJson(builderAction: JsonObject.() -> Unit): JsonObject = JsonObject().apply(builderAction)

fun JsonObject.addArray(property: String, builderAction: JsonArray.() -> Unit) {
    this.add(property, JsonArray().apply(builderAction))
}

fun JsonObject.addObject(property: String, builderAction: JsonObject.() -> Unit) {
    this.add(property, buildJson(builderAction))
}
