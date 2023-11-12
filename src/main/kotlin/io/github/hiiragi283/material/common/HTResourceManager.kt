package io.github.hiiragi283.material.common

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.InputStream
import java.io.InputStreamReader

object HTResourceManager {

    private val logger: Logger = LogManager.getLogger("HTResourceManager")

    private val gson = Gson()

    fun register() {

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
            object : SimpleSynchronousResourceReloadListener {

                override fun reload(manager: ResourceManager) {

                    manager.findResources("") { it.endsWith(".json") }.forEach { identifier: Identifier ->
                        try {
                            val inputStream: InputStream = manager.getResource(identifier).inputStream
                            val inputStreamReader = InputStreamReader(inputStream)
                            val jsonObject: JsonObject = gson.fromJson(inputStreamReader, JsonObject::class.java)
                        } catch (e: Exception) {
                            logger.error(e)
                        }
                    }

                }

                override fun getFabricId(): Identifier = HTMaterialsCommon.id("material_item_manager")

            }
        )

    }

}