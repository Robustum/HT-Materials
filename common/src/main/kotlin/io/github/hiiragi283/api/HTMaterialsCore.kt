package io.github.hiiragi283.api

import com.google.common.collect.ImmutableSet
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
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.api.util.collection.HashDefaultedMap
import io.github.hiiragi283.api.util.collection.buildDefaultedMap
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
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
        val compositionMap: Map<HTMaterialKey, HTMaterialComposition> = buildMap {
            addons.forEach { it.modifyMaterialComposition(this) }
        }
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
                val flags: HTMaterialFlagSet = flagMap.getOrCreate(key).build()
                val property: HTMaterialPropertyMap = propertyMap.getOrCreate(key).build()
                val type: HTMaterialType = typeMap.getOrDefault(key, HTMaterialType.Undefined)
                put(key, HTMaterial(key, composition, flags, property, type))
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

    private val contentMap by lazy {
        HashDefaultedMap.create<HTMaterialKey, HTMaterialContentMap>(::HTMaterialContentMap).apply {
            addons.forEach { it.modifyMaterialContent(this) }
        }
    }

    protected fun <T : Any> forEachContent(clazz: Class<T>, consumer: BiConsumer<HTMaterialContent<T>, HTMaterialKey>) {
        for (materialKey: HTMaterialKey in HTMaterialsAPI.INSTANCE.materialRegistry().getKeys()) {
            for (content: HTMaterialContent<T> in contentMap.getOrCreate(materialKey).getContents(clazz)) {
                consumer.accept(content, materialKey)
            }
        }
    }

    fun initContents() {
        forEachContent(Block::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Blocks registered!")
        forEachContent(Fluid::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Fluids registered!")
        forEachContent(Item::class.java) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Items registered!")
    }

    //    Post Initialization    //

    fun postInitialize(side: HTPlatformHelper.Side) {
        // Process postInit on HTMaterialContent
        forEachContent(Block::class.java) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Blocks completed!")
        forEachContent(Fluid::class.java) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Fluids completed!")
        forEachContent(Item::class.java) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Items completed!")
        // Bind game objects to HTPart
        bindFluidToPart()
        HTMaterialsAPI.log("HTFluidManager initialized!")
        bindItemToPart()
        HTMaterialsAPI.log("HTPartManager initialized!")
        // Post initialize from addons
        addons.forEach { it.postInitialize(side) }
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
