package io.github.hiiragi283.material.common

import io.github.hiiragi283.material.api.addon.HTMaterialsAddons
import io.github.hiiragi283.material.api.block.HTMaterialBlock
import io.github.hiiragi283.material.api.item.HTMaterialBlockItem
import io.github.hiiragi283.material.api.item.HTMaterialItem
import io.github.hiiragi283.material.api.material.HTMaterial
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
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.fabric.api.RRPCallback

@Suppress("unused")
object HTMaterialsCommon : ModInitializer {

    const val MOD_ID: String = "ht_materials"
    const val MOD_NAME: String = "HT Materials"

    internal val RESOURCE_PACK: RuntimeResourcePack = RuntimeResourcePack.create(id("runtime"))

    private val logger: Logger = LogManager.getLogger(MOD_NAME)

    private var loadState: HTLoadState = HTLoadState.CONSTRUCT

    override fun onInitialize() {

        //Collect Addons
        HTMaterialsAddons

        //Pre Initialization for registering material/shape from other mods
        loadState = HTLoadState.PRE_INIT
        HTMaterialsAddons.registerShapes()
        logger.info("HTShape loaded!")
        HTMaterialsAddons.registerMaterials()
        logger.info("HTMaterial loaded!")

        //Verify Material Properties and Flags
        HTMaterial.REGISTRY.forEach(HTMaterial::verify)
        logger.info("All Materials Verified!")

        //Disable Material Modification
        loadState = HTLoadState.INIT

        //Initialize Game Objects
        HTItemGroup
        registerMaterialBlocks()
        logger.info("All Material Blocks Registered!")
        registerMaterialFluids()
        logger.info("All Material Fluids Registered!")
        registerMaterialItems()
        logger.info("All Material Items Registered!")

        //Post Initialization for access material/shape from other mods
        loadState = HTLoadState.POST_INIT

        loadState = HTLoadState.COMPLETE
        logger.info("Post-Init Plugins loaded!")

        //Register Common Events
        registerEvents()
        logger.info("Common Events Registered!")

        RRPCallback.AFTER_VANILLA.register {

            //Register Json Recipes
            registerRecipes()
            logger.info("Json Recipes Registered!")

            //Register Resource Pack
            it.add(RESOURCE_PACK)
            logger.info("Runtime ResourcePack Registered!")

        }

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
                    val identifier: Identifier = shape.getIdentifier(material)
                    val settings: FabricBlockSettings =
                        material.getProperty(HTPropertyKey.SOLID)?.blockSettings ?: return@material
                    val block = HTMaterialBlock(material, shape, settings)
                    //Register Block
                    Registry.register(Registry.BLOCK, identifier, block)
                    //Register BlockItem
                    HTMaterialBlockItem(block).run {
                        Registry.register(Registry.ITEM, identifier, this)
                        //Register as Default Item
                        HTPartManager.forceRegister(material, shape, this)
                    }
                }
        }
    }

    private fun registerMaterialItems() {
        HTShape.REGISTRY.forEach { shape: HTShape ->
            HTMaterial.REGISTRY
                .filter(shape::canGenerateItem)
                .forEach { material: HTMaterial ->
                    val identifier: Identifier = shape.getIdentifier(material)
                    //Register Item
                    HTMaterialItem(material, shape).run {
                        Registry.register(Registry.ITEM, identifier, this)
                        //Register as Default Item
                        HTPartManager.forceRegister(material, shape, this)
                    }
                }
        }
    }

    private fun registerMaterialFluids() {
        HTMaterial.REGISTRY.forEach { material -> material.getProperty(HTPropertyKey.FLUID)?.init(material) }
    }

    private fun registerEvents() {

    }

    private fun registerRecipes() {
        HTMaterial.REGISTRY.forEach { material: HTMaterial ->
            materialRecipe(material)
            HTPartManager.getDefaultItem(material, HTShape.BLOCK)?.run { blockRecipe(material, this) }
            HTPartManager.getDefaultItem(material, HTShape.INGOT)?.run { ingotRecipe(material, this) }
            HTPartManager.getDefaultItem(material, HTShape.NUGGET)?.run { nuggetRecipe(material, this) }
        }
    }

    private fun materialRecipe(material: HTMaterial) {
        //1x Block -> 9x Ingot/Gem/Dust
        val defaultShape: HTShape = material.getDefaultShape() ?: return
        val resultItem: ItemConvertible = HTPartManager.getDefaultItem(material, defaultShape) ?: return
        if (!HTPartManager.hasDefaultItem(material, HTShape.BLOCK)) return
        HTRecipeManager.createVanillaRecipe(
            ShapelessRecipeJsonBuilder.create(resultItem, 9)
                .input(HTShape.BLOCK.getCommonTag(material))
                .setBypassesValidation(true),
            defaultShape.getIdentifier(material).suffix("_shapeless")
        )
    }

    private fun blockRecipe(material: HTMaterial, item: ItemConvertible) {
        //9x Ingot/Gem -> 1x Block
        val defaultShape: HTShape = material.getDefaultShape() ?: return
        if (!HTPartManager.hasDefaultItem(material, defaultShape)) return
        HTRecipeManager.createVanillaRecipe(
            ShapedRecipeJsonBuilder.create(item)
                .patterns("AAA", "AAA", "AAA")
                .input('A', defaultShape.getCommonTag(material))
                .setBypassesValidation(true),
            HTShape.BLOCK.getIdentifier(material).suffix("_shaped")
        )
    }

    private fun ingotRecipe(material: HTMaterial, item: ItemConvertible) {
        //9x Nugget -> 1x Ingot
        if (!HTPartManager.hasDefaultItem(material, HTShape.NUGGET)) return
        HTRecipeManager.createVanillaRecipe(
            ShapedRecipeJsonBuilder.create(item)
                .patterns("AAA", "AAA", "AAA")
                .input('A', HTShape.NUGGET.getCommonTag(material))
                .setBypassesValidation(true),
            HTShape.INGOT.getIdentifier(material).suffix("_shaped")
        )
    }

    private fun nuggetRecipe(material: HTMaterial, item: ItemConvertible) {
        //1x Ingot -> 9x Nugget
        if (!HTPartManager.hasDefaultItem(material, HTShape.INGOT)) return
        HTRecipeManager.createVanillaRecipe(
            ShapelessRecipeJsonBuilder.create(item, 9)
                .input(HTShape.INGOT.getCommonTag(material))
                .setBypassesValidation(true),
            HTShape.NUGGET.getIdentifier(material).suffix("_shapeless")
        )
    }

}