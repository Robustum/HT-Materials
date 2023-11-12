package io.github.hiiragi283.material.common

import io.github.hiiragi283.material.api.entorypoint.HTMaterialPlugin
import io.github.hiiragi283.material.api.item.MaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.fabric.api.RRPCallback

@Suppress("unused")
object HTMaterialsCommon : ModInitializer {

    const val MOD_ID: String = "ht_materials"
    const val MOD_NAME: String = "HT Materials"

    internal val RESOURCE_PACK: RuntimeResourcePack = RuntimeResourcePack.create(id("runtime"))

    override fun onInitialize() {

        //Initialize material/shape
        HTShape
        HTElementMaterials
        HTVanillaMaterials

        //Pre Initialization for registering material/shape from other mods
        HTMaterialPlugin.Pre.preInitializes()

        //Initialize game objects
        HTMaterial.REGISTRY.forEach { material: HTMaterial ->
            HTShape.REGISTRY
                .filter { it.canGenerateItem(material) }
                .forEach { shape: HTShape ->
                    Registry.register(
                        Registry.ITEM,
                        shape.getIdentifier(MOD_ID, material),
                        MaterialItem(material, shape, FabricItemSettings())
                    )
                }
        }

        //Post Initialization for access material/shape from other mods
        HTMaterialPlugin.Post.postInitializes()

        //Register Common Events
        HTEventHandler.registerCommon()

        //Register Resource Pack
        RRPCallback.BEFORE_USER.register { it.add(RESOURCE_PACK) }

    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

}