package io.github.hiiragi283.material.impl

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.property.HTMaterialProperty
import io.github.hiiragi283.api.material.property.HTPropertyType
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper

object HTMaterialRegistryImpl : HTMaterialRegistry, SimpleSynchronousResourceReloadListener {
    private var internalMap: Map<HTMaterialKey, HTMaterial> = mapOf()

    init {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(this)
    }

    //    Map    //

    override val entries: Set<Map.Entry<HTMaterialKey, HTMaterial>>
        get() = internalMap.entries
    override val keys: Set<HTMaterialKey>
        get() = internalMap.keys
    override val size: Int
        get() = internalMap.size
    override val values: Collection<HTMaterial>
        get() = internalMap.values

    override fun isEmpty(): Boolean = internalMap.isEmpty()

    override fun get(key: HTMaterialKey): HTMaterial? = internalMap[key]

    override fun containsValue(value: HTMaterial): Boolean = internalMap.containsValue(value)

    override fun containsKey(key: HTMaterialKey): Boolean = internalMap.containsKey(key)

    //    SimpleSynchronousResourceReloadListener    //

    private fun registerMaterial(builder: HTMaterialRegistry.Builder) {
        val helper = HTMaterialsAddon.MaterialHelper()
        HTMaterialsAPI.INSTANCE.forEachAddon { it.registerMaterial(helper) }
        helper.materialKeys.forEach { key: HTMaterialKey ->
            val composition: HTMaterialComposition = helper.getComposition(key)
            val flags: Set<Identifier> = helper.getOrCreateFlagSet(key)
            val property: Map<HTPropertyType<*>, HTMaterialProperty<*>> = helper.getOrCreatePropertyMap(key)
            val type: HTMaterialType = helper.getType(key)
            builder.getOrCreate(key).merge(composition, flags, property, type)
        }
    }

    override fun reload(manager: ResourceManager) {
        internalMap = buildMap {
            HTMaterialRegistry.Builder(this).run {
                // Reload from data pack
                manager.findResources("material") { it.endsWith(".json") }.forEach { id: Identifier ->
                    val key = HTMaterialKey(id.path.removePrefix("material/").removeSuffix(".json"))
                    manager.getAllResources(id).forEach { resource: Resource ->
                        resource.use { resource1: Resource ->
                            resource1
                                .inputStream
                                .bufferedReader()
                                .let(JsonHelper::deserialize)
                                .run { getOrCreate(key).merge(this) }
                        }
                    }
                }
                // Reload from addons
                registerMaterial(this)
                HTMaterialsAPI.INSTANCE.forEachAddon { it.modifyMaterialRegistry(this) }
            }
        }.mapValues { (key: HTMaterialKey, builder: HTMaterial.Builder) -> HTMaterial(key, builder) }.onEach {
            HTMaterialsAPI.LOGGER.debug("Material; {} registered!", it.key)
        }
    }

    override fun getFabricId(): Identifier = HTMaterialsAPI.id("material_registry")
}
