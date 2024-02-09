package io.github.hiiragi283.material.impl.shape

import com.google.common.collect.ImmutableMap
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeRegistry

internal class HTShapeRegistryImpl(map: Map<HTShapeKey, HTShape>) : HTShapeRegistry {
    override val registry: ImmutableMap<HTShapeKey, HTShape> = ImmutableMap.copyOf(map)
}
