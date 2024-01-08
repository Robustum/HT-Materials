@file:Environment(EnvType.CLIENT)
@file:JvmName("HTUtilClient")

package io.github.hiiragi283.material.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.model.json.*
import net.minecraft.resource.Resource
import net.minecraft.util.Identifier
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

//    Model    //

/**
 * Reference: <a href="https://github.com/TechReborn/TechReborn/blob/1.18/src/main/java/techreborn/client/render/ModelHelper.java">TechReborn - GitHub</a>
 */

val DEFAULT_ITEM_TRANSFORMS: ModelTransformation =
    JsonUnbakedModel.deserialize(getReader(Identifier("models/item/generated"))).transformations

@Throws(IOException::class)
fun getReader(id: Identifier): BufferedReader {
    val resource: Resource = MinecraftClient.getInstance().resourceManager.getResource(id.suffix(".json"))
    return BufferedReader(InputStreamReader(resource.inputStream, Charsets.UTF_8))
}