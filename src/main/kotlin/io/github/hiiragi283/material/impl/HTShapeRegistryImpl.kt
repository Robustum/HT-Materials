package io.github.hiiragi283.material.impl

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeRegistry
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier

object HTShapeRegistryImpl : HTShapeRegistry, SimpleSynchronousResourceReloadListener {
    private var internalMap: Map<String, HTShape> = mapOf()

    init {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(this)
    }

    //    Map    //

    override val entries: Set<Map.Entry<String, HTShape>>
        get() = internalMap.entries
    override val keys: Set<String>
        get() = internalMap.keys
    override val size: Int
        get() = internalMap.size
    override val values: Collection<HTShape>
        get() = internalMap.values

    override fun isEmpty(): Boolean = internalMap.isEmpty()

    override fun get(key: String): HTShape? = internalMap[key]

    override fun containsValue(value: HTShape): Boolean = internalMap.containsValue(value)

    override fun containsKey(key: String): Boolean = internalMap.containsKey(key)

    //    SimpleSynchronousResourceReloadListener    //

    override fun reload(manager: ResourceManager) {
        internalMap = buildList {
            HTShapeRegistry.Builder(this).run {
                // Reload from data pack
                manager.findResources("shape") { it.endsWith(".json") }
                    .map(Identifier::getPath)
                    .forEach<String>(::add)
                // Reload from addons
                HTMaterialsAPI.INSTANCE.forEachAddon { it.modifyShapeRegistry(this) }
            }
        }.associateWith { name -> HTShape(name) }.onEach {
            HTMaterialsAPI.LOGGER.debug("Shape; {} registered!", it.key)
        }
    }

    override fun getFabricId(): Identifier = HTMaterialsAPI.id("shape_registry")
}
