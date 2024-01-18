package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

class HTMaterialContentMap {
    private val blockMap: MutableMap<HTShapeKey, HTMaterialContent<*>> = hashMapOf()
    private val fluidMap: MutableMap<HTShapeKey, HTMaterialContent<*>> = hashMapOf()
    private val itemMap: MutableMap<HTShapeKey, HTMaterialContent<*>> = hashMapOf()

    private fun <T> getMap(registryKey: RegistryKey<Registry<T>>): MutableMap<HTShapeKey, HTMaterialContent<*>> = when (registryKey) {
        Registry.BLOCK_KEY -> blockMap
        Registry.FLUID_KEY -> fluidMap
        Registry.ITEM_KEY -> itemMap
        else -> mutableMapOf()
    }

    fun <T> add(content: HTMaterialContent<T>) {
        getMap(content.registryKey)[content.shapeKey] = content
    }

    fun <T> remove(registryKey: RegistryKey<Registry<T>>, shapeKey: HTShapeKey) = getMap(registryKey).remove(shapeKey)

    internal fun <T> getContents(registryKey: RegistryKey<Registry<T>>): Collection<HTMaterialContent<T>> =
        getMap(registryKey).values.filterIsInstance<HTMaterialContent<T>>()
}
