package io.github.hiiragi283.material.common

import io.github.hiiragi283.material.api.entorypoint.HTMaterialPlugin
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
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
        HTVanillaMaterials

        //Pre Initialization for registering material/shape from other mods
        HTMaterialPlugin.Pre.preInitializes()

        //Initialize game objects

        //Post Initialization for access material/shape from other mods
        HTMaterialPlugin.Post.postInitializes()

        //Register Common Events
        HTEventHandler.register()

        //Register Resource Pack
        RRPCallback.BEFORE_USER.register { it.add(RESOURCE_PACK) }

    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

}