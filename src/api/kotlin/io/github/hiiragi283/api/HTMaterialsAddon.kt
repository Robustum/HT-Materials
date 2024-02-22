package io.github.hiiragi283.api

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.collection.DefaultedMap
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
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

    //    Initialize    //

    fun registerShape(shapeHelper: ShapeHelper) {}

    fun registerMaterial(materialHelper: MaterialHelper) {}

    fun registerMaterialKey(registry: ImmutableSet.Builder<HTMaterialKey>) {}

    fun modifyMaterialComposition(registry: MutableMap<HTMaterialKey, HTMaterialComposition>) {}

    fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap.Builder>) {}

    fun modifyMaterialFlag(registry: DefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {}

    fun modifyMaterialProperty(registry: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {}

    fun modifyMaterialType(registry: MutableMap<HTMaterialKey, HTMaterialType>) {}

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

        fun getShapeIdPath(key: HTShapeKey): String =
            idPathMap.getOrDefault(key, "%s_${key.name}")

        fun setShapeIdPath(key: HTShapeKey, idPath: String) {
            idPathMap[key] = idPath
        }

        // Tag Path
        private val tagPathMap: MutableMap<HTShapeKey, String> = hashMapOf()

        fun getShapeTagPath(key: HTShapeKey): String =
            tagPathMap.getOrDefault(key, "${getShapeIdPath(key)}s")

        fun setShapeTagPath(key: HTShapeKey, tagPath: String) {
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

        // Material Composition
        private val compositionMap: MutableMap<HTMaterialKey, HTMaterialComposition> = hashMapOf()

        fun getMaterialComposition(key: HTMaterialKey): HTMaterialComposition =
            compositionMap.getOrDefault(key, HTMaterialComposition.EMPTY)

        fun setMaterialComposition(key: HTMaterialKey, composition: HTMaterialComposition) {
            compositionMap[key] = composition
        }

        // Material flag
        private val flagMap: MutableMap<HTMaterialKey, HTMaterialFlagSet.Builder> = hashMapOf()

        fun getOrCreateFlagSet(key: HTMaterialKey): HTMaterialFlagSet.Builder =
            flagMap.computeIfAbsent(key) { HTMaterialFlagSet.Builder() }

        // Material Property
        private val propertyMap: MutableMap<HTMaterialKey, HTMaterialPropertyMap.Builder> = hashMapOf()

        fun getOrCreatePropertyMap(key: HTMaterialKey): HTMaterialPropertyMap.Builder =
            propertyMap.computeIfAbsent(key) { HTMaterialPropertyMap.Builder() }

        // Material Type
        private val typeMap: MutableMap<HTMaterialKey, HTMaterialType> = hashMapOf()

        fun getMaterialType(key: HTMaterialKey): HTMaterialType =
            typeMap.getOrDefault(key, HTMaterialType.Undefined)

        fun setMaterialType(key: HTMaterialKey, type: HTMaterialType) {
            typeMap[key] = type
        }
    }
}