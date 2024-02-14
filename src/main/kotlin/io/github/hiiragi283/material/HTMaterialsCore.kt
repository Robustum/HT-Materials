package io.github.hiiragi283.material

import com.google.common.collect.ImmutableSet
import com.google.gson.JsonElement
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.collection.DefaultedMap
import io.github.hiiragi283.api.collection.buildDefaultedMap
import io.github.hiiragi283.api.extention.getEntrypoints
import io.github.hiiragi283.api.extention.isModLoaded
import io.github.hiiragi283.api.extention.prefix
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.resource.HTRuntimeDataPack
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.data.server.RecipesProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.Level
import java.util.function.BiConsumer

internal object HTMaterialsCore {
    private lateinit var addons: Iterable<HTMaterialsAddon>

    @JvmStatic
    fun initAddons() {
        addons = getEntrypoints<HTMaterialsAddon>(HTMaterialsAPI.MOD_ID)
            .filter { isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
        // Print sorted addons
        HTMaterialsAPI.log("HTMaterialsAddon collected!")
        HTMaterialsAPI.log("=== List ===")
        addons.forEach {
            HTMaterialsAPI.log("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.log("============")
    }

    //    Initialize - HTShape    //

    @JvmStatic
    fun createShapeMap(): Map<HTShapeKey, HTShape> {
        // Register shape keys
        val shapeKeySet: Set<HTShapeKey> = ImmutableSet.builder<HTShapeKey>().apply {
            addons.forEach { it.registerShape(this) }
        }.build()
        // Register shape id path
        val idPathMap: Map<HTShapeKey, String> = buildMap {
            addons.forEach { it.modifyShapeIdPath(this) }
        }
        // Register shape tag path
        val tagPathMap: Map<HTShapeKey, String> = buildMap {
            addons.forEach { it.modifyShapeTagPath(this) }
        }
        // Create and register shape
        val shapeMap: Map<HTShapeKey, HTShape> = buildMap {
            shapeKeySet.forEach { key: HTShapeKey ->
                val idPath: String = idPathMap.getOrDefault(key, "%s_${key.name}")
                val tagPath: String = tagPathMap.getOrDefault(key, "${idPath}s")
                putIfAbsent(key, HTShape(key, idPath, tagPath))
                HTMaterialsAPI.log("Shape: ${key.name} registered!")
            }
        }
        HTMaterialsAPI.log("HTShapeRegistry initialized!")
        return shapeMap
    }

    //    Initialize - HTMaterial    //

    @JvmStatic
    fun createMaterialMap(): Map<HTMaterialKey, HTMaterial> {
        // Register material keys
        val materialKeySet: ImmutableSet<HTMaterialKey> = ImmutableSet.builder<HTMaterialKey>().apply {
            addons.forEach { it.registerMaterialKey(this) }
        }.build()
        // Register material compositions
        val compositionMap: Map<HTMaterialKey, HTMaterialComposition> =
            buildMap { addons.forEach { it.modifyMaterialComposition(this) } }
        // Register material contents
        val contentMapMap: DefaultedMap<HTMaterialKey, HTMaterialContentMap.Builder> =
            buildDefaultedMap(HTMaterialContentMap::Builder) { addons.forEach { it.modifyMaterialContent(this) } }
        // Register material flags
        val flagMap: DefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder> =
            buildDefaultedMap(HTMaterialFlagSet::Builder) { addons.forEach { it.modifyMaterialFlag(this) } }
        // Register material properties
        val propertyMap: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> =
            buildDefaultedMap(HTMaterialPropertyMap::Builder) { addons.forEach { it.modifyMaterialProperty(this) } }
        // Register material types
        val typeMap: Map<HTMaterialKey, HTMaterialType> = buildMap {
            addons.forEach { it.modifyMaterialType(this) }
        }
        // create and register material
        val materialMap: Map<HTMaterialKey, HTMaterial> = buildMap {
            materialKeySet.forEach { key: HTMaterialKey ->
                val composition: HTMaterialComposition = compositionMap.getOrDefault(key, HTMaterialComposition.EMPTY)
                val contentMap: HTMaterialContentMap = contentMapMap.getOrCreate(key).build()
                val flags: HTMaterialFlagSet = flagMap.getOrCreate(key).build()
                val property: HTMaterialPropertyMap = propertyMap.getOrCreate(key).build()
                val type: HTMaterialType = typeMap.getOrDefault(key, HTMaterialType.Undefined)
                put(key, HTMaterial(key, composition, contentMap, flags, property, type))
                HTMaterialsAPI.log("Material: $key registered!")
            }
        }
        HTMaterialsAPI.log("HTMaterialRegistry initialized!")
        return materialMap
    }

    @JvmStatic
    fun verifyMaterial() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getValues().forEach { material ->
            material.forEachProperty { it.verify(material) }
            material.forEachFlag { it.verify(material) }
        }
    }

    //    Initialization    //

    @JvmStatic
    private fun forEachContent(type: HTMaterialContent.Type, consumer: BiConsumer<HTMaterialContent, HTMaterialKey>) {
        for (material: HTMaterial in HTMaterialsAPI.INSTANCE.materialRegistry().getValues()) {
            for (content: HTMaterialContent in material.getContents(type)) {
                consumer.accept(content, material.key)
            }
        }
    }

    @JvmStatic
    fun initContents() {
        forEachContent(HTMaterialContent.Type.BLOCK) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Blocks registered!")
        forEachContent(HTMaterialContent.Type.FLUID) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Fluids registered!")
        forEachContent(HTMaterialContent.Type.ITEM) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Items registered!")
    }

    //    Post Initialization    //

    @JvmStatic
    fun postInitialize(envType: EnvType) {
        // Process postInit on HTMaterialContent
        forEachContent(HTMaterialContent.Type.BLOCK) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Blocks completed!")
        forEachContent(HTMaterialContent.Type.FLUID) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Fluids completed!")
        forEachContent(HTMaterialContent.Type.ITEM) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Items completed!")
        // Bind game objects to HTPart
        HTFluidManager.Builder().run {
            addons.forEach { it.bindFluidToPart(this) }
            HTMaterialsAPIImpl.fluidManager = HTFluidManager(this)
        }
        HTMaterialsAPI.log("HTFluidManager initialized!")
        HTPartManager.Builder().run {
            addons.forEach { it.bindItemToPart(this) }
            HTMaterialsAPIImpl.partManager = HTPartManager(this)
        }
        ServerWorldEvents.LOAD.register(HTMaterialsCore::onWorldLoaded)
        HTMaterialsAPI.log("HTPartManager initialized!")
        // Post initialize from addons
        addons.forEach { it.postInitialize(envType) }
        HTMaterialsAPI.log("Post-initialize completed!")
        // Register recipes
        registerRecipes()
        HTMaterialsAPI.log("Added material recipes!")
    }

    @JvmStatic
    private fun registerRecipes() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { key ->
            HTMaterialsAPI.INSTANCE.partManager().run {
                getDefaultItem(key, HTShapeKeys.INGOT)?.let { ingotRecipe(this, key, it) }
                getDefaultItem(key, HTShapeKeys.NUGGET)?.let { nuggetRecipe(this, key, it) }
            }
        }
    }

    // 9x Nugget -> 1x Ingot
    @JvmStatic
    private fun ingotRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item) {
        if (!partManager.hasItem(materialKey, HTShapeKeys.NUGGET)) return
        val nuggetTag: Tag<Item> = HTPart(materialKey, HTShapeKeys.NUGGET).getPartTag()
        HTRuntimeDataPack.addRecipe { exporter ->
            ShapedRecipeJsonFactory.create(item)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', nuggetTag)
                .criterion("has_nugget", RecipesProvider.conditionsFromTag(nuggetTag))
                .offerTo(exporter, HTShapeKeys.INGOT.getShape().getIdentifier(materialKey).prefix("shaped/"))
        }
    }

    // 1x Ingot -> 9x Nugget
    @JvmStatic
    private fun nuggetRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item) {
        if (!partManager.hasItem(materialKey, HTShapeKeys.INGOT)) return
        val ingotTag: Tag<Item> = HTPart(materialKey, HTShapeKeys.INGOT).getPartTag()
        HTRuntimeDataPack.addRecipe { exporter ->
            ShapelessRecipeJsonFactory.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipesProvider.conditionsFromTag(ingotTag))
                .offerTo(exporter, HTShapeKeys.NUGGET.getShape().getIdentifier(materialKey).prefix("shapeless/"))
        }
    }

    @JvmStatic
    fun recipeUnification(map: Map<Identifier, JsonElement>) {
        map.forEach { (id: Identifier, jsonElement: JsonElement) ->
            try {
                jsonElement.asJsonObject.run json@{
                    if (has("type")) {
                        Registry.RECIPE_SERIALIZER.get(Identifier(getAsJsonPrimitive("type").asString))?.run type@{
                            addons.forEach { it.replaceJsonRecipe(id, this@type, this@json) }
                        }
                    }
                }
            } catch (e: Exception) {
                HTMaterialsAPI.log(e.localizedMessage, Level.ERROR)
            }
        }
    }

    @JvmStatic
    @Suppress("UNUSED_PARAMETER")
    private fun onWorldLoaded(server: MinecraftServer, world: ServerWorld) {
        // Reload Fluid Manager
        HTMaterialsAPIImpl.fluidManager = HTFluidManager.Builder().apply {
            // Register fluids from common tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                TagRegistry.fluid(materialKey.getCommonId()).values().forEach { fluid ->
                    add(materialKey, fluid)
                }
            }
        }.let { HTFluidManager(it) }
        // Reload Part Manager
        HTMaterialsAPIImpl.partManager = HTPartManager.Builder().apply {
            // Register items from part tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                    HTPart(materialKey, shapeKey).getPartTag().values().forEach { item ->
                        add(materialKey, shapeKey, item)
                    }
                }
            }
        }.let(::HTPartManager)
    }
}
