package io.github.hiiragi283.material.common

import io.github.hiiragi283.material.api.HTMaterialsAPI
import io.github.hiiragi283.material.api.entorypoint.HTMaterialPlugin
import io.github.hiiragi283.material.api.item.MaterialItem
import io.github.hiiragi283.material.api.item.MaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.ItemConvertible
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

        //Disable Material Modification
        HTMaterialsAPI.disableModifyMaterial()

        //Initialize Game Objects
        registerMaterialItems()
        registerMaterialFluids()

        //Post Initialization for access material/shape from other mods
        HTMaterialPlugin.Post.postInitializes()

        //Register Common Events
        HTEventHandler.registerCommon()

        //Register Json Recipes
        registerJsonRecipes()

        //Register Resource Pack
        RRPCallback.BEFORE_USER.register { it.add(RESOURCE_PACK) }

    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

    private fun registerMaterialItems() {
        HTShape.REGISTRY.forEach { shape: HTShape ->
            HTMaterial.REGISTRY
                .filter(shape::canGenerateItem)
                .forEach { material: HTMaterial ->
                    Registry.register(
                        Registry.ITEM,
                        shape.getIdentifier(MOD_ID, material),
                        MaterialItem(material, shape, FabricItemSettings())
                    )
                }
        }
    }

    private fun registerMaterialFluids() {
        HTMaterial.REGISTRY.forEach { material -> material.getProperty(HTPropertyKey.FLUID)?.init(material) }
    }

    private fun registerJsonRecipes() {
        HTMaterial.REGISTRY.forEach { material: HTMaterial ->
            val defaultItem: ItemConvertible =
                MaterialItemConvertible.getDefaultItem(material, HTShape.INGOT) ?: return@forEach
            HTJsonRecipeManager.createVanillaRecipe(
                ShapedRecipeJsonBuilder.create(defaultItem)
                    .patterns("AAA", "AAA", "AAA")
                    .input('A', HTShape.INGOT.getTagKey(material))
                    .setBypassesValidation(true),
                id("${material}_ingots_shaped")
            )
        }
    }

}