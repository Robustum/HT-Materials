package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.content.HTMaterialContent
import io.github.hiiragi283.material.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTComponentProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.resource.HTRuntimeDataManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.util.isModLoaded
import io.github.hiiragi283.material.util.prefix
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
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
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

internal object HTMaterialsCore {
    private val LOGGER: Logger = LogManager.getLogger("${HTMaterials.MOD_NAME}/Addons")

    private val CACHE: List<HTMaterialsAddon> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterials.MOD_ID, HTMaterialsAddon::class.java)
        .filter { isModLoaded(it.modId) }
        .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })

    //    Initialize - HTShape    //

    private val shapeKeySet: HTObjectKeySet<HTShapeKey> = HTObjectKeySet.create()

    fun registerShape() {
        CACHE.forEach { it.registerShape(shapeKeySet) }
    }

    fun createShape() {
        shapeKeySet.forEach(HTShape::create)
    }

    //    Initialize - HTMaterial    //

    private val materialKeySet: HTObjectKeySet<HTMaterialKey> = HTObjectKeySet.create()

    fun registerMaterialKey() {
        CACHE.forEach { it.registerMaterialKey(materialKeySet) }
    }

    private val contentMap: HTDefaultedMap<HTMaterialKey, HTMaterialContentMap> =
        HTDefaultedMap.create { HTMaterialContentMap() }

    fun modifyMaterialContent() {
        CACHE.forEach { it.modifyMaterialContent(contentMap) }
    }

    private val propertyMap: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> =
        HTDefaultedMap.create { HTMaterialPropertyMap.Builder() }

    fun modifyMaterialProperty() {
        CACHE.forEach { it.modifyMaterialProperty(propertyMap) }
    }

    private val flagMap: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder> =
        HTDefaultedMap.create { HTMaterialFlagSet.Builder() }

    fun modifyMaterialFlag() {
        CACHE.forEach { it.modifyMaterialFlag(flagMap) }
    }

    private val colorMap: MutableMap<HTMaterialKey, ColorConvertible> = hashMapOf()

    fun modifyMaterialColor() {
        CACHE.forEach { it.modifyMaterialColor(colorMap) }
    }

    private val formulaMap: MutableMap<HTMaterialKey, FormulaConvertible> = hashMapOf()

    fun modifyMaterialFormula() {
        CACHE.forEach { it.modifyMaterialFormula(formulaMap) }
    }

    private val molarMap: MutableMap<HTMaterialKey, MolarMassConvertible> = hashMapOf()

    fun modifyMaterialMolar() {
        CACHE.forEach { it.modifyMaterialMolar(molarMap) }
    }

    private val typeMap: MutableMap<HTMaterialKey, HTMaterialType> = hashMapOf()

    fun modifyMaterialType() {
        CACHE.forEach { it.modifyMaterialType(typeMap) }
    }

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
        materialKeySet.forEach { key: HTMaterialKey ->
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
        HTMaterial.getMaterials().forEach { material ->
            material.properties.values.forEach { it.verify(material) }
            material.flags.forEach { it.verify(material) }
        }
    }

    //    Initialization    //

    fun <T> createContent(registryKey: RegistryKey<Registry<T>>) {
        for (materialKey: HTMaterialKey in HTMaterial.getMaterialKeys()) {
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
        content.createBlock(materialKey)
            ?.let { Registry.register(Registry.BLOCK, content.getIdentifier(materialKey), it) }
            ?.run {
                // Register BlockItem if exists
                content.createBlockItem(this, materialKey)
                    ?.let { Registry.register(Registry.ITEM, content.getIdentifier(materialKey), it) }
                content.onCreate(materialKey, this)
            }
    }

    private fun createFluid(content: HTMaterialContent.FLUID, materialKey: HTMaterialKey) {
        // Register Flowing Fluid if exists
        content.createFlowing(materialKey)
            ?.let { Registry.register(Registry.FLUID, content.getFlowingFluidIdentifier(materialKey), it) }
        // Register Still Fluid
        Registry.register(
            Registry.FLUID,
            content.getIdentifier(materialKey),
            content.createStill(materialKey),
        ).run {
            // Register FluidBlock if exists
            content.createFluidBlock(this, materialKey)?.let {
                Registry.register(Registry.BLOCK, content.getBlockIdentifier(materialKey), it)
            }
            // Register BucketItem if exists
            content.createFluidBucket(this, materialKey)?.let {
                Registry.register(Registry.ITEM, content.getBucketIdentifier(materialKey), it)
            }
            content.onCreate(materialKey, this)
        }
    }

    private fun createItem(content: HTMaterialContent.ITEM, materialKey: HTMaterialKey) {
        // Register Item
        content.createItem(materialKey)
            ?.let { Registry.register(Registry.ITEM, content.getIdentifier(materialKey), it) }
            ?.run { content.onCreate(materialKey, this) }
    }

    //    Post Initialization    //

    fun commonSetup() {
        // Bind Game Objects to HTPart
        bindItemToPart()
        bindFluidToPart()

        CACHE.forEach(HTMaterialsAddon::commonSetup)

        registerRecipes()
        LOGGER.info("Added Material Recipes!")

        HTFluidManager.registerAllFluids()
        LOGGER.info("All Fluids Registered to HTFluidManager!")
    }

    private val itemTable: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>> =
        HTDefaultedTable.create { _, _ -> mutableSetOf() }

    private fun bindItemToPart() {
        CACHE.forEach { it.bindItemToPart(itemTable) }
        itemTable.forEach { materialKey: HTMaterialKey, shapeKey: HTShapeKey, items: MutableCollection<ItemConvertible> ->
            items.forEach { item: ItemConvertible -> HTPartManager.register(materialKey, shapeKey, item) }
        }
    }

    private val fluidMap: HTDefaultedMap<HTMaterialKey, MutableCollection<Fluid>> =
        HTDefaultedMap.create { mutableSetOf() }

    private fun bindFluidToPart() {
        CACHE.forEach { it.bindFluidToPart(fluidMap) }
        fluidMap.forEach { materialKey: HTMaterialKey, fluids: MutableCollection<Fluid> ->
            fluids.forEach { fluid: Fluid -> HTFluidManager.register(materialKey, fluid) }
        }
    }

    private fun registerRecipes() {
        HTMaterial.getMaterialKeys().forEach { key ->
            HTPartManager.getDefaultItem(key, HTShapes.INGOT)?.let { ingotRecipe(key, it) }
            HTPartManager.getDefaultItem(key, HTShapes.NUGGET)?.let { nuggetRecipe(key, it) }
        }
    }

    private fun ingotRecipe(material: HTMaterialKey, item: Item) {
        // 9x Nugget -> 1x Ingot
        if (!HTPartManager.hasDefaultItem(material, HTShapes.NUGGET)) return
        val nuggetTag: Tag<Item> = HTShapes.NUGGET.getCommonTag(material)
        HTRuntimeDataManager.addShapedCrafting(
            HTShapes.INGOT.getIdentifier(material).prefix("shaped/"),
            ShapedRecipeJsonFactory.create(item)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', nuggetTag)
                .criterion("has_nugget", RecipesProvider.conditionsFromTag(nuggetTag)),
        )
    }

    private fun nuggetRecipe(material: HTMaterialKey, item: Item) {
        // 1x Ingot -> 9x Nugget
        if (!HTPartManager.hasDefaultItem(material, HTShapes.INGOT)) return
        val ingotTag: Tag<Item> = HTShapes.INGOT.getCommonTag(material)
        HTRuntimeDataManager.addShapelessCrafting(
            HTShapes.NUGGET.getIdentifier(material).prefix("shapeless/"),
            ShapelessRecipeJsonFactory.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipesProvider.conditionsFromTag(ingotTag)),
        )
    }

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
        commonSetup()
        CACHE.forEach(HTMaterialsAddon::clientSetup)
        LOGGER.info("BlockStates and Models Registered!")
    }
}
