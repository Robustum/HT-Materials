package io.github.hiiragi283.material

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.content.HTMaterialContent
import io.github.hiiragi283.material.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.material.property.component.HTComponentProperty
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.api.util.collection.DefaultedMap
import io.github.hiiragi283.material.api.util.collection.DefaultedTable
import io.github.hiiragi283.material.api.util.collection.HashDefaultedMap
import io.github.hiiragi283.material.api.util.collection.HashDefaultedTable
import io.github.hiiragi283.material.api.util.isModLoaded
import io.github.hiiragi283.material.api.util.prefix
import io.github.hiiragi283.material.api.util.resource.HTRuntimeDataManager
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
            .getEntrypoints(HTMaterials.MOD_ID, HTMaterialsAddon::class.java)
            .filter { isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
    }

    //    Initialize - HTShape    //

    private val shapeKeySet: ImmutableSet.Builder<HTShapeKey> = ImmutableSet.builder()
    private val forgeRegexMap: MutableMap<HTShapeKey, Regex> = hashMapOf()
    private val fabricRegexMap: MutableMap<HTShapeKey, Regex> = hashMapOf()

    fun createShape() {
        entryPoints.forEach {
            it.registerShape(shapeKeySet)
            it.modifyShapeForgeRegex(forgeRegexMap)
            it.modifyShapeFabricRegex(fabricRegexMap)
        }
        shapeKeySet.build().forEach { key: HTShapeKey ->
            val forgeRegex: Regex = forgeRegexMap.getOrDefault(key, "".toRegex())
            val fabricRegex: Regex = fabricRegexMap.getOrDefault(key, """.*_${key.name}s""".toRegex())
            HTShape.create(key, forgeRegex, fabricRegex)
        }
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
        HTMaterials.log("Added Material Recipes!")

        HTFluidManager.registerAllFluids()
        HTMaterials.log("All Fluids Registered to HTFluidManager!")
    }

    private val itemTable: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>> =
        HashDefaultedTable { _, _ -> mutableSetOf() }

    private fun bindItemToPart() {
        entryPoints.forEach { it.bindItemToPart(itemTable) }
        itemTable.forEach { materialKey: HTMaterialKey, shapeKey: HTShapeKey, items: MutableCollection<ItemConvertible> ->
            items.forEach { item: ItemConvertible -> HTPartManager.register(materialKey, shapeKey, item) }
        }
    }

    private val fluidMap: DefaultedMap<HTMaterialKey, MutableCollection<Fluid>> = HashDefaultedMap { mutableSetOf() }

    private fun bindFluidToPart() {
        entryPoints.forEach { it.bindFluidToPart(fluidMap) }
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
        val nuggetTag: Tag<Item> = HTPart(material, HTShapes.NUGGET).getPartTag()
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
        val ingotTag: Tag<Item> = HTPart(material, HTShapes.INGOT).getPartTag()
        HTRuntimeDataManager.addShapelessCrafting(
            HTShapes.NUGGET.getIdentifier(material).prefix("shapeless/"),
            ShapelessRecipeJsonFactory.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipesProvider.conditionsFromTag(ingotTag)),
        )
    }
}