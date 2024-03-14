package io.github.hiiragi283.api

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.material.property.HTMaterialProperty
import io.github.hiiragi283.api.material.property.HTPropertyType
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import net.fabricmc.api.EnvType
import net.minecraft.util.Identifier

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {
    val modId: String

    fun id(path: String) = Identifier(modId, path)

    val priority: Int

    //    Pre Initialize    //

    @Deprecated("Use modifyMaterialRegistry()")
    fun registerMaterial(materialHelper: MaterialHelper) {}

    fun modifyShapeRegistry(builder: HTShapeRegistry.Builder) {}

    fun modifyMaterialRegistry(builder: HTMaterialRegistry.Builder) {}

    //    Post Initialization    //

    fun modifyFluidManager(builder: HTFluidManager.Builder) {}

    fun modifyPartManager(builder: HTPartManager.Builder) {}

    fun postInitialize(envType: EnvType) {}

    //    MaterialHelper    //

    class MaterialHelper {
        // Material key
        val materialKeys: Set<HTMaterialKey>
            get() = _materialKeys
        private val _materialKeys: MutableSet<HTMaterialKey> = mutableSetOf()

        fun addMaterialKey(materialKey: HTMaterialKey) {
            check(_materialKeys.add(materialKey)) { "Material named ${materialKey.name} is already registered!" }
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
        private val flagMap: MutableMap<HTMaterialKey, MutableSet<Identifier>> = hashMapOf()

        fun getOrCreateFlagSet(key: HTMaterialKey): MutableSet<Identifier> = flagMap.computeIfAbsent(key) { hashSetOf() }

        // Material Property
        private val propertyMap: MutableMap<HTMaterialKey, MutableMap<HTPropertyType<*>, HTMaterialProperty<*>>> =
            hashMapOf()

        fun getOrCreatePropertyMap(key: HTMaterialKey): MutableMap<HTPropertyType<*>, HTMaterialProperty<*>> =
            propertyMap.computeIfAbsent(key) { hashMapOf() }

        @JvmOverloads
        fun <T : HTMaterialProperty<T>> addProperty(key: HTMaterialKey, property: T, action: T.() -> Unit = {}) {
            getOrCreatePropertyMap(key)[property.type] = property.apply(action)
        }

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
