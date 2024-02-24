package io.github.hiiragi283.api

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import net.fabricmc.api.EnvType
import net.minecraft.util.Identifier

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {
    val modId: String

    fun id(path: String) = Identifier(modId, path)

    val priority: Int

    //    Pre Initialize    //

    fun registerShape(shapeHelper: ShapeHelper) {}

    fun registerMaterial(materialHelper: MaterialHelper) {}

    //    Post Initialization    //

    fun bindItemToPart(builder: HTPartManager.Builder) {}

    fun bindFluidToPart(builder: HTFluidManager.Builder) {}

    fun postInitialize(envType: EnvType) {}

    //    ShapeHelper    //

    class ShapeHelper {
        // Shape key
        val shapeKeys: Set<HTShapeKey>
            get() = _shapeKeys
        private val _shapeKeys: MutableSet<HTShapeKey> = mutableSetOf()

        fun addShapeKey(shapeKey: HTShapeKey) {
            check(_shapeKeys.add(shapeKey)) { "" }
        }

        // Id Path
        private val idPathMap: MutableMap<HTShapeKey, String> = hashMapOf()

        fun getIdPath(key: HTShapeKey): String = idPathMap.getOrDefault(key, "%s_${key.name}")

        fun setIdPath(key: HTShapeKey, idPath: String) {
            idPathMap[key] = idPath
        }

        // Tag Path
        private val tagPathMap: MutableMap<HTShapeKey, String> = hashMapOf()

        fun getTagPath(key: HTShapeKey): String = tagPathMap.getOrDefault(key, "${getIdPath(key)}s")

        fun setTagPath(key: HTShapeKey, tagPath: String) {
            tagPathMap[key] = tagPath
        }
    }

    //    MaterialHelper    //

    class MaterialHelper {
        // Material key
        val materialKeys: Set<HTMaterialKey>
            get() = _materialKeys
        private val _materialKeys: MutableSet<HTMaterialKey> = mutableSetOf()

        fun addMaterialKey(materialKey: HTMaterialKey) {
            check(_materialKeys.add(materialKey)) { "" }
        }

        // Alternative Name
        private val alternativeNameMap: Multimap<HTMaterialKey, String> = HashMultimap.create()

        fun getAlternativeNames(key: HTMaterialKey): Collection<String> = alternativeNameMap[key]

        fun addAlternativeName(key: HTMaterialKey, vararg alternativeNames: String) {
            alternativeNameMap.putAll(key, alternativeNames.toList())
        }

        // Material Composition
        private val compositionMap: MutableMap<HTMaterialKey, HTMaterialComposition> = hashMapOf()

        fun getComposition(key: HTMaterialKey): HTMaterialComposition = compositionMap.getOrDefault(key, HTMaterialComposition.EMPTY)

        fun setComposition(key: HTMaterialKey, composition: HTMaterialComposition) {
            compositionMap[key] = composition
        }

        // Material flag
        private val flagMap: MutableMap<HTMaterialKey, HTMaterialFlagSet.Builder> = hashMapOf()

        fun getOrCreateFlagSet(key: HTMaterialKey): HTMaterialFlagSet.Builder = flagMap.computeIfAbsent(key) { HTMaterialFlagSet.Builder() }

        // Material Property
        private val propertyMap: MutableMap<HTMaterialKey, HTMaterialPropertyMap.Builder> = hashMapOf()

        fun getOrCreatePropertyMap(key: HTMaterialKey): HTMaterialPropertyMap.Builder =
            propertyMap.computeIfAbsent(key) { HTMaterialPropertyMap.Builder() }

        // Material Type
        private val typeMap: MutableMap<HTMaterialKey, HTMaterialType> = hashMapOf()

        fun getType(key: HTMaterialKey): HTMaterialType = typeMap.getOrDefault(key, HTMaterialType.Undefined)

        fun setType(key: HTMaterialKey, type: HTMaterialType) {
            typeMap[key] = type
        }

        // Utility
        fun addSimpleMaterial(key: HTMaterialKey, element: Pair<HTElement, Int>, type: HTMaterialType? = null) {
            addMaterialKey(key)
            setComposition(key, HTMaterialComposition.molecular(element))
            type?.let { setType(key, it) }
        }

        fun addMetalMaterial(key: HTMaterialKey, element: HTElement) {
            addSimpleMaterial(key, element to 1, HTMaterialType.Metal)
        }

        fun addMetalMaterial(key: HTMaterialKey, composition: HTMaterialComposition) {
            addMaterialKey(key)
            setComposition(key, composition)
            setType(key, HTMaterialType.Metal)
        }

        fun addStoneMaterial(key: HTMaterialKey, composition: HTMaterialComposition) {
            addMaterialKey(key)
            setComposition(key, composition)
            setType(key, HTMaterialType.Stone)
        }
    }
}