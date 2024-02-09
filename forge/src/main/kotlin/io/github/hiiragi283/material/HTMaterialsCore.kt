package io.github.hiiragi283.material

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTAddon
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
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.api.util.collection.HashDefaultedMap
import io.github.hiiragi283.api.util.collection.buildDefaultedMap
import io.github.hiiragi283.material.impl.HTMaterialsAPIImpl
import io.github.hiiragi283.material.impl.fluid.HTFluidManagerImpl
import io.github.hiiragi283.material.impl.material.HTMaterialRegistryImpl
import io.github.hiiragi283.material.impl.part.HTPartManagerImpl
import io.github.hiiragi283.material.impl.shape.HTShapeRegistryImpl
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraftforge.fml.ModList
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type
import java.util.function.BiConsumer

internal object HTMaterialsCore {
    private lateinit var addons: Iterable<HTMaterialsAddon>

    fun initAddons() {
        val type: Type = Type.getType(HTAddon::class.java)
        addons = ModList.get().allScanData.flatMap { scanData ->
            scanData.annotations
                .asSequence()
                .filter { it.annotationType == type }
                .map(ModFileScanData.AnnotationData::getMemberName)
                .map { Class.forName(it) }
                .map { it.asSubclass(HTMaterialsAddon::class.java) }
                .map { it.getConstructor() }
                .map { it.newInstance() }
                .toList()
        }
        // Print sorted addons
        HTMaterialsAPI.log("HTMaterialsAddon collected!")
        HTMaterialsAPI.log("=== List ===")
        addons.forEach {
            HTMaterialsAPI.log("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.log("============")
    }

    //    Initialize - HTShape    //

    fun createShape() {
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

    private val contentMap: DefaultedMap<HTMaterialKey, HTMaterialContentMap> =
        HashDefaultedMap.create(::HTMaterialContentMap)

    fun createMaterial() {
        // Register material keys
        val materialKeySet: ImmutableSet<HTMaterialKey> = ImmutableSet.builder<HTMaterialKey>().apply {
            addons.forEach { it.registerMaterialKey(this) }
        }.build()
        // Register material contents
        addons.forEach {
            it.modifyMaterialContent(contentMap)
        }
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
        HTMaterialsAPI.log("HTMaterialRegistry initialized!")
    }

    fun verifyMaterial() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getValues().forEach { material ->
            material.forEachProperty { it.verify(material) }
            material.forEachFlag { it.verify(material) }
        }
    }

    //    Initialization    //

    fun <T : Any> forEachContent(clazz: Class<T>, consumer: BiConsumer<HTMaterialContent<T>, HTMaterialKey>) {
        for (materialKey: HTMaterialKey in HTMaterialsAPI.INSTANCE.materialRegistry().getKeys()) {
            for (content: HTMaterialContent<T> in contentMap.getOrCreate(materialKey).getContents(clazz)) {
                consumer.accept(content, materialKey)
            }
        }
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
        // Register material recipes
        registerRecipes()
        HTMaterialsAPI.log("Added material recipes!")
    }

    private fun bindItemToPart() {
        HTPartManagerImpl.Builder().run {
            addons.forEach { it.bindItemToPart(this) }
            HTMaterialsAPIImpl.partManager = HTPartManagerImpl(this)
        }
    }

    private fun bindFluidToPart() {
        HTFluidManagerImpl.Builder().run {
            addons.forEach { it.bindFluidToPart(this) }
            HTMaterialsAPIImpl.fluidManager = HTFluidManagerImpl(this)
        }
    }

    private fun registerRecipes() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { key ->
            HTMaterialsAPI.INSTANCE.partManager().run {
                // getDefaultItem(key, HTShapeKeys.INGOT)?.let { ingotRecipe(this, key, it) }
                // getDefaultItem(key, HTShapeKeys.NUGGET)?.let { nuggetRecipe(this, key, it) }
            }
        }
    }

    /*private fun ingotRecipe(partManager: HTPartManager, material: HTMaterialKey, item: Item) {
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
    }*/
}
