package io.github.hiiragi283.api.shape

import com.google.common.collect.ImmutableMap

interface HTShapeRegistry {
    val registry: ImmutableMap<HTShapeKey, HTShape>

    fun getKeys(): Collection<HTShapeKey> = registry.keys

    fun getValues(): Collection<HTShape> = registry.values

    fun getShape(shapeKey: HTShapeKey): HTShape? = registry[shapeKey]

    fun isRegistered(shapeKey: HTShapeKey): Boolean = shapeKey in registry
}
