package io.github.hiiragi283.material

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.*
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTComponentProperty
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.api.util.collection.DefaultedTable
import io.github.hiiragi283.api.util.collection.HashDefaultedMap
import io.github.hiiragi283.api.util.collection.HashDefaultedTable
import io.github.hiiragi283.api.util.isModLoaded
import io.github.hiiragi283.api.util.prefix
import io.github.hiiragi283.api.util.resource.HTRuntimeDataManager
import io.github.hiiragi283.material.impl.HTFluidManagerImpl
import io.github.hiiragi283.material.impl.HTPartManagerImpl
import io.github.hiiragi283.material.impl.material.HTMaterialRegistryImpl
import io.github.hiiragi283.material.impl.shape.HTShapeImpl
import io.github.hiiragi283.material.impl.shape.HTShapeRegistryImpl
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.data.server.RecipesProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

internal object HTMaterialsCore {
    private lateinit var entryPoints: Iterable<HTMaterialsAddon>

    fun initEntryPoints() {
        entryPoints = FabricLoader.getInstance()
            .getEntrypoints(HTMaterialsAPI.MOD_ID, HTMaterialsAddon::class.java)
            .filter { isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
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
        val shapeMap: MutableMap<HTShapeKey, HTShape> = mutableMapOf()
        shapeKeySet.forEach { key: HTShapeKey ->
            val idPath: String = idPathMap.getOrDefault(key, "%s_${key.name}")
            val tagPath: String = tagPathMap.getOrDefault(key, "${idPath}s")
            shapeMap.putIfAbsent(key, HTShapeImpl(key, idPath, tagPath))
            HTMaterialsAPI.log("Shape: ${key.name} registered!")
        }
        // Set shape registry to HTMaterialsAPI
        HTMaterials.shapeRegistry = HTShapeRegistryImpl(shapeMap)
    }

    //    Initialize - HTMaterial    //

    private val materialKeySet: ImmutableSet.Builder<HTMaterialKey> = ImmutableSet.builder()
    private val contentMap: DefaultedMap<HTMaterialKey, HTMaterialContentMap> =
        HashDefaultedMap { HTMaterialContentMap() }
    private val propertyMap: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> =
        HashDefaultedMap { HTMaterialPropertyMap.Builder() }
    private val flagMap: DefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder> =
        HashDefaultedMap { HTMaterialFlagSet.Builder() }
    private val colorMap: MutableMap<HTMaterialKey, ColorConvertible> = hashMapOf()
    private val formulaMap: MutableMap<HTMaterialKey, FormulaConvertible> = hashMapOf()
    private val molarMap: MutableMap<HTMaterialKey, MolarMassConvertible> = hashMapOf()
    private val typeMap: MutableMap<HTMaterialKey, HTMaterialType> = hashMapOf()

    private fun getColor(key: HTMaterialKey, property: HTMaterialPropertyMap): ColorConvertible {
        var color: ColorConvertible? = property.values.filterIsInstance<HTComponentProperty<*>>().getOrNull(0)
        colorMap[key]?.let { color = it }
        return color ?: ColorConvertible.EMPTY
    }

    private fun getFormula(key: HTMaterialKey, property: HTMaterialPropertyMap): FormulaConvertible {
        var formula: FormulaConvertible? = property.values.filterIsInstance<HTComponentProperty<*>>().getOrNull(0)
        formulaMap[key]?.let { formula = it }
        return formula ?: FormulaConvertible.EMPTY
    }

    private fun getMolar(key: HTMaterialKey, property: HTMaterialPropertyMap): MolarMassConvertible {
        var molar: MolarMassConvertible? = property.values.filterIsInstance<HTComponentProperty<*>>().getOrNull(0)
        molarMap[key]?.let { molar = it }
        return molar ?: MolarMassConvertible.EMPTY
    }

    fun createMaterial() {
        // Set material registry to HTMaterialsAPI
        HTMaterials.materialRegistry = HTMaterialRegistryImpl(mapOf())
        entryPoints.forEach {
            it.registerMaterialKey(materialKeySet)
            it.modifyMaterialContent(contentMap)
            it.modifyMaterialProperty(propertyMap)
            it.modifyMaterialFlag(flagMap)
            it.modifyMaterialColor(colorMap)
            it.modifyMaterialFormula(formulaMap)
            it.modifyMaterialMolar(molarMap)
            it.modifyMaterialType(typeMap)
        }
        materialKeySet.build().forEach { key: HTMaterialKey ->
            val property: HTMaterialPropertyMap = propertyMap.getOrCreate(key).build()
            val flags: HTMaterialFlagSet = flagMap.getOrCreate(key).build()
            val color: ColorConvertible = getColor(key, property)
            val formula: FormulaConvertible = getFormula(key, property)
            val molar: MolarMassConvertible = getMolar(key, property)
            val type: HTMaterialType = typeMap.getOrDefault(key, HTMaterialType.Undefined)
            HTMaterial.create(
                key,
                property,
                flags,
                color.asColor(),
                formula.asFormula(),
                "%.1f".format(molar.asMolarMass()).toDouble(),
                type,
            )
        }
    }

    fun verifyMaterial() {
        HTMaterialsAPI.getInstance().materialRegistry().getValues().forEach { material ->
            material.properties.values.forEach { it.verify(material) }
            material.flags.forEach { it.verify(material) }
        }
    }

    //    Initialization    //

    fun <T : Any> createContent(registryKey: RegistryKey<Registry<T>>) {
        for (materialKey: HTMaterialKey in HTMaterialsAPI.getInstance().materialRegistry().getKeys()) {
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
        // Register Block
        content.block(materialKey)
            ?.let { Registry.register(Registry.BLOCK, content.id(materialKey), it) }
            ?.run {
                // Register BlockItem if exists
                content.blockItem(this, materialKey)
                    ?.let { Registry.register(Registry.ITEM, content.id(materialKey), it) }
                content.onCreate(materialKey, this)
            }
    }

    private fun createFluid(content: HTMaterialContent.FLUID, materialKey: HTMaterialKey) {
        // Register Flowing Fluid if exists
        content.flowing(materialKey)
            ?.let { Registry.register(Registry.FLUID, content.flowingId(materialKey), it) }
        // Register Still Fluid
        Registry.register(
            Registry.FLUID,
            content.id(materialKey),
            content.still(materialKey),
        ).run {
            // Register FluidBlock if exists
            content.block(this, materialKey)?.let {
                Registry.register(Registry.BLOCK, content.blockId(materialKey), it)
            }
            // Register BucketItem if exists
            content.bucket(this, materialKey)?.let {
                Registry.register(Registry.ITEM, content.bucketId(materialKey), it)
            }
            content.onCreate(materialKey, this)
        }
    }

    private fun createItem(content: HTMaterialContent.ITEM, materialKey: HTMaterialKey) {
        // Register Item
        content.item(materialKey)
            ?.let { Registry.register(Registry.ITEM, content.id(materialKey), it) }
            ?.run { content.onCreate(materialKey, this) }
    }

    //    Post Initialization    //

    fun postInitialize(envType: EnvType) {
        // Bind Game Objects to HTPart
        bindItemToPart()
        bindFluidToPart()

        entryPoints.forEach { it.postInitialize(envType) }

        registerRecipes()
        HTMaterialsAPI.log("Added Material Recipes!")
    }

    private val itemTable: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>> =
        HashDefaultedTable { _, _ -> mutableSetOf() }

    private fun bindItemToPart() {
        entryPoints.forEach { it.bindItemToPart(itemTable) }
        HTMaterials.partManager = HTPartManagerImpl(itemTable)
    }

    private val fluidMap: DefaultedMap<HTMaterialKey, MutableCollection<Fluid>> = HashDefaultedMap { mutableSetOf() }

    private fun bindFluidToPart() {
        entryPoints.forEach { it.bindFluidToPart(fluidMap) }
        HTMaterials.fluidManager = HTFluidManagerImpl(fluidMap)
    }

    private fun registerRecipes() {
        HTMaterialsAPI.getInstance().materialRegistry().getKeys().forEach { key ->
            val partManager = HTMaterialsAPI.getInstance().partManager()
            partManager.getDefaultItem(key, HTShapeKeys.INGOT)?.let { ingotRecipe(partManager, key, it) }
            partManager.getDefaultItem(key, HTShapeKeys.NUGGET)?.let { nuggetRecipe(partManager, key, it) }
        }
    }

    private fun ingotRecipe(partManager: HTPartManager, material: HTMaterialKey, item: Item) {
        // 9x Nugget -> 1x Ingot
        if (!partManager.hasItem(material, HTShapeKeys.NUGGET)) return
        val nuggetTag: Tag<Item> = HTPart(material, HTShapeKeys.NUGGET).getPartTag()
        HTRuntimeDataManager.addShapedCrafting(
            HTShapeKeys.INGOT.getShape().getIdentifier(material).prefix("shaped/"),
            ShapedRecipeJsonFactory.create(item)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', nuggetTag)
                .criterion("has_nugget", RecipesProvider.conditionsFromTag(nuggetTag)),
        )
    }

    private fun nuggetRecipe(partManager: HTPartManager, material: HTMaterialKey, item: Item) {
        // 1x Ingot -> 9x Nugget
        if (!partManager.hasItem(material, HTShapeKeys.INGOT)) return
        val ingotTag: Tag<Item> = HTPart(material, HTShapeKeys.INGOT).getPartTag()
        HTRuntimeDataManager.addShapelessCrafting(
            HTShapeKeys.NUGGET.getShape().getIdentifier(material).prefix("shapeless/"),
            ShapelessRecipeJsonFactory.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipesProvider.conditionsFromTag(ingotTag)),
        )
    }
}