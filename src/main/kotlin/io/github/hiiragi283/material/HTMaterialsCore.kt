package io.github.hiiragi283.material

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.HTPlatformHelper
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
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.api.util.collection.HashDefaultedMap
import io.github.hiiragi283.api.util.collection.buildDefaultedMap
import io.github.hiiragi283.api.util.prefix
import io.github.hiiragi283.api.util.resource.HTRuntimeDataPack
import io.github.hiiragi283.material.impl.HTMaterialsAPIImpl
import io.github.hiiragi283.material.impl.fluid.HTFluidManagerImpl
import io.github.hiiragi283.material.impl.material.HTMaterialRegistryImpl
import io.github.hiiragi283.material.impl.part.HTPartManagerImpl
import io.github.hiiragi283.material.impl.shape.HTShapeRegistryImpl
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.data.server.RecipesProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

internal object HTMaterialsCore {
    private lateinit var entryPoints: Iterable<HTMaterialsAddon>

    fun initEntryPoints() {
        entryPoints = FabricLoader.getInstance()
            .getEntrypoints(HTMaterialsAPI.MOD_ID, HTMaterialsAddon::class.java)
            .filter { HTPlatformHelper.INSTANCE.isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
        // Print sorted addons
        HTMaterialsAPI.log("HTMaterialsAddon collected!")
        HTMaterialsAPI.log("=== List ===")
        entryPoints.forEach {
            HTMaterialsAPI.log("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.log("============")
    }

    //    Initialize - HTShape    //

    fun createShape() {
        // Register shape keys
        val shapeKeySet: Set<HTShapeKey> = ImmutableSet.builder<HTShapeKey>().apply {
            entryPoints.forEach { it.registerShape(this) }
        }.build()
        // Register shape id path
        val idPathMap: Map<HTShapeKey, String> = buildMap {
            entryPoints.forEach { it.modifyShapeIdPath(this) }
        }
        // Register shape tag path
        val tagPathMap: Map<HTShapeKey, String> = buildMap {
            entryPoints.forEach { it.modifyShapeTagPath(this) }
        }
        // Create and register shape
        HTMaterialsAPIImpl.shapeRegistry = buildMap {
            shapeKeySet.forEach { key: HTShapeKey ->
                val idPath: String = idPathMap.getOrDefault(key, "%s_${key.name}")
                val tagPath: String = tagPathMap.getOrDefault(key, "${idPath}s")
                putIfAbsent(key, HTShape(key, idPath, tagPath))
                HTMaterialsAPI.log("Shape: ${key.name} registered!")
            }
        }.let(::HTShapeRegistryImpl)
        HTMaterialsAPI.log("HTShapeRegistry initialized!")
    }

    //    Initialize - HTMaterial    //

    private val contentMap: DefaultedMap<HTMaterialKey, HTMaterialContentMap> = HashDefaultedMap.create(::HTMaterialContentMap)

    fun createMaterial() {
        // Register material keys
        val materialKeySet: ImmutableSet<HTMaterialKey> = ImmutableSet.builder<HTMaterialKey>().apply {
            entryPoints.forEach { it.registerMaterialKey(this) }
        }.build()
        // Register material contents
        entryPoints.forEach {
            it.modifyMaterialContent(contentMap)
        }
        // Register material compositions
        val compositionMap: Map<HTMaterialKey, HTMaterialComposition> = buildMap {
            entryPoints.forEach { it.modifyMaterialComposition(this) }
        }
        // Register material flags
        val flagMap: DefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder> =
            buildDefaultedMap(HTMaterialFlagSet::Builder) { entryPoints.forEach { it.modifyMaterialFlag(this) } }
        // Register material properties
        val propertyMap: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> =
            buildDefaultedMap(HTMaterialPropertyMap::Builder) { entryPoints.forEach { it.modifyMaterialProperty(this) } }
        // Register material types
        val typeMap: Map<HTMaterialKey, HTMaterialType> = buildMap {
            entryPoints.forEach { it.modifyMaterialType(this) }
        }
        // create and register material
        HTMaterialsAPIImpl.materialRegistry = buildMap {
            materialKeySet.forEach { key: HTMaterialKey ->
                val composition: HTMaterialComposition = compositionMap.getOrDefault(key, HTMaterialComposition.EMPTY)
                val flags: HTMaterialFlagSet = flagMap.getOrCreate(key).build()
                val property: HTMaterialPropertyMap = propertyMap.getOrCreate(key).build()
                val type: HTMaterialType = typeMap.getOrDefault(key, HTMaterialType.Undefined)
                put(key, HTMaterial(key, composition, flags, property, type))
                HTMaterialsAPI.log("Material: $key registered!")
            }
        }.let(::HTMaterialRegistryImpl)
        HTMaterialsAPI.log("HTMaterialRegistry initialized")
    }

    fun verifyMaterial() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getValues().forEach { material ->
            material.forEachProperty { it.verify(material) }
            material.forEachFlag { it.verify(material) }
        }
    }

    //    Initialization    //

    fun <T : Any> createContent(registryKey: RegistryKey<Registry<T>>) {
        for (materialKey: HTMaterialKey in HTMaterialsAPI.INSTANCE.materialRegistry().getKeys()) {
            for (content: HTMaterialContent<T> in contentMap.getOrCreate(materialKey).getContents(registryKey)) {
                when (content) {
                    is HTMaterialContent.BLOCK -> createBlock(content, materialKey)
                    is HTMaterialContent.FLUID -> createFluid(content, materialKey)
                    is HTMaterialContent.ITEM -> createItem(content, materialKey)
                }
            }
        }
    }

    private fun createBlock(content: HTMaterialContent.BLOCK, materialKey: HTMaterialKey) {
        // Register block
        content.block(materialKey)
            ?.let { Registry.register(Registry.BLOCK, content.id(materialKey), it) }
            ?.run {
                // Register block item if exists
                content.blockItem(this, materialKey)
                    ?.let { Registry.register(Registry.ITEM, content.id(materialKey), it) }
                content.onCreate(materialKey, this)
            }
    }

    private fun createFluid(content: HTMaterialContent.FLUID, materialKey: HTMaterialKey) {
        // Register flowing fluid if exists
        content.flowing(materialKey)
            ?.let { Registry.register(Registry.FLUID, content.flowingId(materialKey), it) }
        // Register still fluid
        Registry.register(
            Registry.FLUID,
            content.id(materialKey),
            content.still(materialKey),
        ).run {
            // Register fluid block if exists
            content.block(this, materialKey)?.let {
                Registry.register(Registry.BLOCK, content.blockId(materialKey), it)
            }
            // Register bucket item if exists
            content.bucket(this, materialKey)?.let {
                Registry.register(Registry.ITEM, content.bucketId(materialKey), it)
            }
            content.onCreate(materialKey, this)
        }
    }

    private fun createItem(content: HTMaterialContent.ITEM, materialKey: HTMaterialKey) {
        // Register item
        content.item(materialKey)
            ?.let { Registry.register(Registry.ITEM, content.id(materialKey), it) }
            ?.run { content.onCreate(materialKey, this) }
    }

    //    Post Initialization    //

    fun postInitialize(envType: EnvType) {
        // Bind game objects to HTPart
        bindFluidToPart()
        HTMaterialsAPI.log("HTFluidManager initialized!")
        bindItemToPart()
        HTMaterialsAPI.log("HTPartManager initialized!")
        // Post initialize from addons
        entryPoints.forEach { it.postInitialize(envType) }
        HTMaterialsAPI.log("Post-initialize completed!")
        // Register material recipes
        registerRecipes()
        HTMaterialsAPI.log("Added material recipes!")
    }

    private fun bindItemToPart() {
        HTPartManagerImpl.Builder().run {
            entryPoints.forEach { it.bindItemToPart(this) }
            HTMaterialsAPIImpl.partManager = HTPartManagerImpl(this)
        }
    }

    private fun bindFluidToPart() {
        HTFluidManagerImpl.Builder().run {
            entryPoints.forEach { it.bindFluidToPart(this) }
            HTMaterialsAPIImpl.fluidManager = HTFluidManagerImpl(this)
        }
    }

    private fun registerRecipes() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { key ->
            HTMaterialsAPI.INSTANCE.partManager().run {
                getDefaultItem(key, HTShapeKeys.INGOT)?.let { ingotRecipe(this, key, it) }
                getDefaultItem(key, HTShapeKeys.NUGGET)?.let { nuggetRecipe(this, key, it) }
            }
        }
    }

    private fun ingotRecipe(partManager: HTPartManager, material: HTMaterialKey, item: Item) {
        // 9x Nugget -> 1x Ingot
        if (!partManager.hasItem(material, HTShapeKeys.NUGGET)) return
        val nuggetTag: Tag<Item> = HTPart(material, HTShapeKeys.NUGGET).getPartTag()
        HTRuntimeDataPack.addRecipe { exporter ->
            ShapedRecipeJsonFactory.create(item)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', nuggetTag)
                .criterion("has_nugget", RecipesProvider.conditionsFromTag(nuggetTag))
                .offerTo(exporter, HTShapeKeys.INGOT.getShape().getIdentifier(material).prefix("shaped/"))
        }
    }

    private fun nuggetRecipe(partManager: HTPartManager, material: HTMaterialKey, item: Item) {
        // 1x Ingot -> 9x Nugget
        if (!partManager.hasItem(material, HTShapeKeys.INGOT)) return
        val ingotTag: Tag<Item> = HTPart(material, HTShapeKeys.INGOT).getPartTag()
        HTRuntimeDataPack.addRecipe { exporter ->
            ShapelessRecipeJsonFactory.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipesProvider.conditionsFromTag(ingotTag))
                .offerTo(exporter, HTShapeKeys.NUGGET.getShape().getIdentifier(material).prefix("shapeless/"))
        }
    }
}
