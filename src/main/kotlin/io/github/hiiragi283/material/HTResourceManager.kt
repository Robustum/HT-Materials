package io.github.hiiragi283.material

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import java.io.InputStream
import java.io.InputStreamReader
import java.util.function.BiConsumer

object HTResourceManager {

    private val gson = Gson()

    fun register(path: String, consumer: BiConsumer<Identifier, JsonObject>) {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
            object : SimpleSynchronousResourceReloadListener {

                override fun reload(manager: ResourceManager) {

                    manager.findResources("") { it.endsWith(".json") }.forEach { identifier: Identifier ->
                        try {
                            val inputStream: InputStream = manager.getResource(identifier).inputStream
                            val inputStreamReader = InputStreamReader(inputStream)
                            val jsonObject: JsonObject = gson.fromJson(inputStreamReader, JsonObject::class.java)
                            consumer.accept(identifier, jsonObject)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                }

                override fun getFabricId(): Identifier = HTMaterials.id(path)

            }
        )
    }

}