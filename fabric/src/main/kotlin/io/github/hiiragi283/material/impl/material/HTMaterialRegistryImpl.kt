package io.github.hiiragi283.material.impl.material

import com.google.common.collect.ImmutableMap
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialRegistry

internal class HTMaterialRegistryImpl(map: Map<HTMaterialKey, HTMaterial>) : HTMaterialRegistry {
    override val registry: ImmutableMap<HTMaterialKey, HTMaterial> = ImmutableMap.copyOf(map)
}
