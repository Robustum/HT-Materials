package io.github.hiiragi283.api.material

import com.google.common.collect.ImmutableMap

interface HTMaterialRegistry {
    val registry: ImmutableMap<HTMaterialKey, HTMaterial>

    fun getKeys(): Collection<HTMaterialKey> = registry.keys

    fun getValues(): Collection<HTMaterial> = registry.values

    fun getMaterial(materialKey: HTMaterialKey): HTMaterial? = registry[materialKey]

    fun isRegistered(materialKey: HTMaterialKey): Boolean = materialKey in registry
}
