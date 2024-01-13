package io.github.hiiragi283.material

import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.event.HTRecipeRegisterCallback
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.content.HTMaterialContent
import io.github.hiiragi283.material.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTComponentProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
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

    private val cache: List<HTMaterialsAddon> = FabricLoader.getInstance()
        .getEntrypoints(HTMaterials.MOD_ID, HTMaterialsAddon::class.java)
        .filter { isModLoaded(it.modId) }
        .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })

    //    Initialize - HTShape    //

    private val shapeKeySet: HTObjectKeySet<HTShapeKey> = HTObjectKeySet.create()

    fun registerShape() {
        cache.forEach { it.registerShape(shapeKeySet) }
    }

    fun createShape() {
        shapeKeySet.forEach(HTShape::create)
    }

    //    Initialize - HTMaterial    //

    private val materialKeySet: HTObjectKeySet<HTMaterialKey> = HTObjectKeySet.create()

    fun registerMaterialKey() {
        cache.forEach { it.registerMaterialKey(materialKeySet) }
    }

    private val contentMap: HTDefaultedMap<HTMaterialKey, HTMaterialContentMap> =
        HTDefaultedMap.create { HTMaterialContentMap() }

    fun modifyMaterialContent() {
        cache.forEach { it.modifyMaterialContent(contentMap) }
    }

    private val propertyMap: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> =
        HTDefaultedMap.create { HTMaterialPropertyMap.Builder() }

    fun modifyMaterialProperty() {
        cache.forEach { it.modifyMaterialProperty(propertyMap) }
    }

    private val flagMap: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder> =
        HTDefaultedMap.create { HTMaterialFlagSet.Builder() }

    fun modifyMaterialFlag() {
        cache.forEach { it.modifyMaterialFlag(flagMap) }
    }

    private val colorMap: MutableMap<HTMaterialKey, ColorConvertible> = hashMapOf()

    fun modifyMaterialColor() {
        cache.forEach { it.modifyMaterialColor(colorMap) }
    }

    private val formulaMap: MutableMap<HTMaterialKey, FormulaConvertible> = hashMapOf()

    fun modifyMaterialFormula() {
        cache.forEach { it.modifyMaterialFormula(formulaMap) }
    }

    private val molarMap: MutableMap<HTMaterialKey, MolarMassConvertible> = hashMapOf()

    fun modifyMaterialMolar() {
        cache.forEach { it.modifyMaterialMolar(molarMap) }
    }

    private val typeMap: MutableMap<HTMaterialKey, HTMaterialType> = hashMapOf()

    fun modifyMaterialType() {
        cache.forEach { it.modifyMaterialType(typeMap) }
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
                type
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

    fun <T> createContent(registryKey: RegistryKey<T>) {
        for (materialKey: HTMaterialKey in HTMaterial.REGISTRY.keys) {
            for (content: HTMaterialContent<T> in contentMap.getOrCreate(materialKey).getContents(registryKey)) {
                content.create(materialKey)?.run {
                    Registry.register(content.registry, content.getIdentifier(materialKey), this)
                    content.onCreate(materialKey, this)
                }
            }
        }
    }

    fun registerMaterialFluids() {
        HTMaterial.REGISTRY.forEach { (key: HTMaterialKey, material: HTMaterial) ->
            material.getProperty(HTPropertyKey.FLUID)?.init(key)
        }
    }

    //    Post Initialization    //

    fun commonSetup() {

        //Bind Game Objects to HTPart
        bindItemToPart()
        bindFluidToPart()

        cache.forEach(HTMaterialsAddon::commonSetup)

        registerRecipes()
        LOGGER.info("Recipes Registered!")

        HTFluidManager.registerAllFluids()
        LOGGER.info("All Fluids Registered to HTFluidManager!")
    }

    private val itemTable: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>> =
        HTDefaultedTable.create { _, _ -> mutableSetOf() }

    private fun bindItemToPart() {
        cache.forEach { it.bindItemToPart(itemTable) }
        itemTable.forEach { materialKey: HTMaterialKey, shapeKey: HTShapeKey, items: MutableCollection<ItemConvertible> ->
            items.forEach { item: ItemConvertible -> HTPartManager.register(materialKey, shapeKey, item) }
        }
    }

    private val fluidMap: HTDefaultedMap<HTMaterialKey, MutableCollection<Fluid>> =
        HTDefaultedMap.create { mutableSetOf() }

    private fun bindFluidToPart() {
        cache.forEach { it.bindFluidToPart(fluidMap) }
        fluidMap.forEach { materialKey: HTMaterialKey, fluids: MutableCollection<Fluid> ->
            fluids.forEach { fluid: Fluid -> HTFluidManager.register(materialKey, fluid) }
        }
    }

    private fun registerRecipes() {
        HTRecipeRegisterCallback.EVENT.register { handler ->
            HTMaterial.getMaterialKeys().forEach { key: HTMaterialKey ->
                HTPartManager.getDefaultItem(key, HTShapes.INGOT)?.let { ingotRecipe(key, it, handler) }
                HTPartManager.getDefaultItem(key, HTShapes.NUGGET)?.let { nuggetRecipe(key, it, handler) }
            }
        }
    }

    private fun ingotRecipe(material: HTMaterialKey, item: Item, handler: HTRecipeRegisterCallback.Handler) {
        //9x Nugget -> 1x Ingot
        if (!HTPartManager.hasDefaultItem(material, HTShapes.NUGGET)) return
        val nuggetTag: Tag<Item> = HTShapes.NUGGET.getCommonTag(material)
        handler.addShapedCrafting(
            HTShapes.INGOT.getIdentifier(material).prefix("shaped/"),
            ShapedRecipeJsonFactory.create(item)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', nuggetTag)
                .criterion("has_nugget", RecipesProvider.conditionsFromTag(nuggetTag))
        )
    }

    private fun nuggetRecipe(material: HTMaterialKey, item: Item, handler: HTRecipeRegisterCallback.Handler) {
        //1x Ingot -> 9x Nugget
        if (!HTPartManager.hasDefaultItem(material, HTShapes.INGOT)) return
        val ingotTag: Tag<Item> = HTShapes.INGOT.getCommonTag(material)
        handler.addShapelessCrafting(
            HTShapes.NUGGET.getIdentifier(material).prefix("shapeless/"),
            ShapelessRecipeJsonFactory.create(item, 9)
                .input(ingotTag)
                .criterion("has_ingot", RecipesProvider.conditionsFromTag(ingotTag))
        )
    }

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
        commonSetup()
        cache.forEach(HTMaterialsAddon::clientSetup)
        LOGGER.info("BlockStates and Models Registered!")
    }

}