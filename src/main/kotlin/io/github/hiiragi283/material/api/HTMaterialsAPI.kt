package io.github.hiiragi283.material.api

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.util.registry.SimpleRegistry

object HTMaterialsAPI {

    @JvmField
    val materialRegistry: SimpleRegistry<HTMaterial> = HTMaterial.REGISTRY


}