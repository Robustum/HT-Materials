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
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.util.isModLoaded
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemConvertible
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
        for (materialKey: HTMaterialKey in HTMaterial.REGISTRY.keys) {
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

        cache.forEach(HTMaterialsAddon::commonSetup)

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

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
        commonSetup()
        cache.forEach(HTMaterialsAddon::clientSetup)
        LOGGER.info("BlockStates and Models Registered!")
    }
}
