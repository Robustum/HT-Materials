package io.github.hiiragi283.api

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.collection.DefaultedMap
import io.github.hiiragi283.api.collection.buildDefaultedMap
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import net.fabricmc.api.EnvType
import net.minecraft.item.Item
import java.util.function.BiConsumer

abstract class HTMaterialsCore {
    protected lateinit var addons: Iterable<HTMaterialsAddon>

    fun initAddons() {
        addons = collectAddons()
        // Print sorted addons
        HTMaterialsAPI.log("HTMaterialsAddon collected!")
        HTMaterialsAPI.log("=== List ===")
        addons.forEach {
            HTMaterialsAPI.log("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.log("============")
    }

    abstract fun collectAddons(): Iterable<HTMaterialsAddon>

    //    Initialize - HTShape    //

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

    fun verifyMaterial() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getValues().forEach { material ->
            material.forEachProperty { it.verify(material) }
            material.forEachFlag { it.verify(material) }
        }
    }

    //    Initialization    //

    private fun forEachContent(type: HTMaterialContent.Type, consumer: BiConsumer<HTMaterialContent, HTMaterialKey>) {
        for (material: HTMaterial in HTMaterialsAPI.INSTANCE.materialRegistry().getValues()) {
            for (content: HTMaterialContent in material.getContents(type)) {
                consumer.accept(content, material.key)
            }
        }
    }

    fun initContents() {
        forEachContent(HTMaterialContent.Type.BLOCK) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Blocks registered!")
        forEachContent(HTMaterialContent.Type.FLUID) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Fluids registered!")
        forEachContent(HTMaterialContent.Type.ITEM) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Items registered!")
    }

    //    Post Initialization    //

    fun postInitialize(envType: EnvType) {
        // Process postInit on HTMaterialContent
        forEachContent(HTMaterialContent.Type.BLOCK) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Blocks completed!")
        forEachContent(HTMaterialContent.Type.FLUID) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Fluids completed!")
        forEachContent(HTMaterialContent.Type.ITEM) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Items completed!")
        // Bind game objects to HTPart
        bindFluidToPart()
        HTMaterialsAPI.log("HTFluidManager initialized!")
        bindItemToPart()
        HTMaterialsAPI.log("HTPartManager initialized!")
        // Post initialize from addons
        addons.forEach { it.postInitialize(envType) }
        HTMaterialsAPI.log("Post-initialize completed!")
    }

    abstract fun bindItemToPart()

    abstract fun bindFluidToPart()

    fun registerRecipes() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { key ->
            HTMaterialsAPI.INSTANCE.partManager().run {
                getDefaultItem(key, HTShapeKeys.INGOT)?.let { ingotRecipe(this, key, it) }
                getDefaultItem(key, HTShapeKeys.NUGGET)?.let { nuggetRecipe(this, key, it) }
            }
        }
        HTMaterialsAPI.log("Added material recipes!")
    }

    // 9x Nugget -> 1x Ingot
    abstract fun ingotRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item)

    // 1x Ingot -> 9x Nugget
    abstract fun nuggetRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item)
}
