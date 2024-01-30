package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterials

class HTShape private constructor(val key: HTShapeKey) {
    companion object {
        //    Registry    //

        private val REGISTRY: MutableMap<HTShapeKey, HTShape> = linkedMapOf()

        @JvmStatic
        fun getShapeKeys(): Collection<HTShapeKey> = REGISTRY.keys

        @JvmStatic
        fun getShapes(): Collection<HTShape> = REGISTRY.values

        @JvmStatic
        fun getShape(key: HTShapeKey): HTShape = REGISTRY[key] ?: throw IllegalStateException("Shape: $key is not registered!")

        @JvmStatic
        fun getShapeOrNull(key: HTShapeKey): HTShape? = REGISTRY[key]

        @JvmStatic
        internal fun create(key: HTShapeKey): HTShape = HTShape(key).also {
            REGISTRY.putIfAbsent(key, it)
            HTMaterials.log("Shape: $key registered!")
        }
    }
}
