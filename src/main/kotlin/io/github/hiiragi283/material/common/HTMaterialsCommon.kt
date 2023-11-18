package io.github.hiiragi283.material.common

import io.github.hiiragi283.material.api.block.HTMaterialBlock
import io.github.hiiragi283.material.api.entorypoint.HTMaterialPlugin
import io.github.hiiragi283.material.api.item.HTMaterialBlockItem
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.util.suffix
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
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

    private var loadState: HTLoadState = HTLoadState.CONSTRUCT

    override fun onInitialize() {

        //Initialize material/shape
        HTShape
        HTElementMaterials
        HTVanillaMaterials

        //Pre Initialization for registering material/shape from other mods
        loadState = HTLoadState.PRE_INIT
        HTMaterialPlugin.Pre.preInitializes()

        //Verify Material Properties and Flags
        HTMaterial.REGISTRY.forEach(HTMaterial::verify)

        //Disable Material Modification
        loadState = HTLoadState.INIT

        //Initialize Game Objects
        HTItemGroup
        registerMaterialBlocks()
        registerMaterialFluids()
        registerMaterialItems()

        //Post Initialization for access material/shape from other mods
        loadState = HTLoadState.POST_INIT
        HTMaterialPlugin.Post.postInitializes()
        loadState = HTLoadState.COMPLETE

        //Register Common Events
        registerEvents()

        //Register Json Tags
        HTTagManager.register()

        //Register Json Recipes
        //registerRecipes()

        //Register Resource Pack
        RRPCallback.AFTER_VANILLA.register { it.add(RESOURCE_PACK) }

    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

    @JvmStatic
    fun getLoadState(): HTLoadState = loadState

    private fun registerMaterialBlocks() {
        HTShape.REGISTRY
            .forEach { shape: HTShape ->
            HTMaterial.REGISTRY
                .filter { it.hasProperty(HTPropertyKey.SOLID) }
                .filter(shape::canGenerateBlock)
                .forEach material@{ material: HTMaterial ->
                    val identifier: Identifier = shape.getIdentifier(MOD_ID, material)
                    val settings: FabricBlockSettings =
                        material.getProperty(HTPropertyKey.SOLID)?.blockSettings ?: return@material
                    val block = HTMaterialBlock(material, shape, settings)
                    //Register Block
                    Registry.register(Registry.BLOCK, identifier, block)
                    //Register as Default Item
                    HTPartManager.forceRegister(material, shape, block)
                    //Register BlockItem
                    val blockItem = HTMaterialBlockItem(block)
                    Registry.register(Registry.ITEM, identifier, blockItem)
                    //Register item Tags
                    HTTagManager.registerItemTags(shape.getCommonTag(material), blockItem)
                }
        }
    }

    private fun registerMaterialItems() {
        HTShape.REGISTRY.forEach { shape: HTShape ->
            HTMaterial.REGISTRY
                .filter(shape::canGenerateItem)
                .forEach { material: HTMaterial ->
                    val identifier: Identifier = shape.getIdentifier(MOD_ID, material)
                    val item = HTMaterialItem(material, shape)
                    //Register Item
                    Registry.register(Registry.ITEM, identifier, item)
                    //Register as Default Item
                    HTPartManager.forceRegister(material, shape, item)
                    //Register Item Tags
                    HTTagManager.registerItemTags(shape.getForgeTag(material), item)
                    HTTagManager.registerItemTags(shape.getCommonTag(material), item)
                }
        }
    }

    private fun registerMaterialFluids() {
        HTMaterial.REGISTRY.forEach { material -> material.getProperty(HTPropertyKey.FLUID)?.init(material) }
    }

    private fun registerEvents() {
        HTPartManager.registerEvent()
    }

    private fun registerRecipes() {
        HTMaterial.REGISTRY.forEach { material: HTMaterial ->
            materialRecipe(material)
            HTPartManager.getDefaultItemTable().get(material, HTShape.BLOCK)?.run { blockRecipe(material, this) }
            HTPartManager.getDefaultItemTable().get(material, HTShape.INGOT)?.run { ingotRecipe(material, this) }
            HTPartManager.getDefaultItemTable().get(material, HTShape.NUGGET)?.run { nuggetRecipe(material, this) }
        }
    }

    private fun materialRecipe(material: HTMaterial) {
        //1x Block -> 9x Ingot/Gem/Dust
        val defaultShape: HTShape = material.getDefaultShape() ?: return
        val resultItem: ItemConvertible = HTPartManager.getDefaultItemTable().get(material, defaultShape) ?: return
        HTRecipeManager.createVanillaRecipe(
            ShapelessRecipeJsonBuilder.create(resultItem, 9)
                .input(HTShape.BLOCK.getCommonTag(material))
                .setBypassesValidation(true),
            HTShape.INGOT.getIdentifier(MOD_ID, material).suffix("_shapeless")
        )
    }

    private fun blockRecipe(material: HTMaterial, item: ItemConvertible) {
        //9x Ingot/Gem/Dust -> 1x Block
        val defaultShape: HTShape = material.getDefaultShape() ?: return
        HTRecipeManager.createVanillaRecipe(
            ShapedRecipeJsonBuilder.create(item)
                .patterns("AAA", "AAA", "AAA")
                .input('A', defaultShape.getCommonTag(material))
                .setBypassesValidation(true),
            HTShape.BLOCK.getIdentifier(MOD_ID, material).suffix("_shaped")
        )
    }

    private fun ingotRecipe(material: HTMaterial, item: ItemConvertible) {
        //9x Nugget -> 1x Ingot
        HTRecipeManager.createVanillaRecipe(
            ShapedRecipeJsonBuilder.create(item)
                .patterns("AAA", "AAA", "AAA")
                .input('A', HTShape.NUGGET.getCommonTag(material))
                .setBypassesValidation(true),
            HTShape.INGOT.getIdentifier(MOD_ID, material).suffix("_shaped")
        )
    }

    private fun nuggetRecipe(material: HTMaterial, item: ItemConvertible) {
        //1x Ingot -> 9x Nugget
        HTRecipeManager.createVanillaRecipe(
            ShapelessRecipeJsonBuilder.create(item, 9)
                .input(HTShape.INGOT.getCommonTag(material))
                .setBypassesValidation(true),
            HTShape.NUGGET.getIdentifier(MOD_ID, material).suffix("_shapeless")
        )
    }

}