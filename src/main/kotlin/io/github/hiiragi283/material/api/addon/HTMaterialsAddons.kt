package io.github.hiiragi283.material.api.addon

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.isModLoaded
import net.fabricmc.loader.api.FabricLoader

object HTMaterialsAddons : HTMaterialsAddon {

    private var cache: List<HTMaterialsAddon> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterialsCommon.MOD_ID, HTMaterialsAddon::class.java)
        .filter { isModLoaded(it.modId) }

    //    HTMaterialsAddon    //

    override val modId: String = HTMaterialsCommon.MOD_ID

    override fun registerShapes() {
        HTShape
        cache.forEach(HTMaterialsAddon::registerShapes)
    }

    override fun registerMaterials() {
        HTMaterial
        cache.forEach(HTMaterialsAddon::registerMaterials)
    }

    override fun commonSetup() {
        cache.forEach(HTMaterialsAddon::commonSetup)
    }

    override fun clientSetup() {
        cache.forEach(HTMaterialsAddon::clientSetup)
    }

}