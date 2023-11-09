package io.github.hiiragi283.material.api

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.util.registry.DefaultedRegistry

object HTMaterialsAPI {

    @JvmField
    val materialRegistry: DefaultedRegistry<HTMaterial> = HTMaterial.REGISTRY


}