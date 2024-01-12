package io.github.hiiragi283.material.api.shape

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class HTShape private constructor(val key: HTShapeKey) {

    companion object {

        private val LOGGER: Logger = LogManager.getLogger(HTShape::class.java)

        //    Registry    //

        private val registry: MutableMap<HTShapeKey, HTShape> = linkedMapOf()

        @JvmField
        val REGISTRY: Map<HTShapeKey, HTShape> = registry

        @JvmStatic
        fun getShapeKeys(): Collection<HTShapeKey> = registry.keys

        @JvmStatic
        fun getShapes(): Collection<HTShape> = registry.values

        @JvmStatic
        fun getShape(key: HTShapeKey): HTShape =
            registry[key] ?: throw IllegalStateException("Shape: $key is not registered!")

        @JvmStatic
        fun getShapeOrNull(key: HTShapeKey): HTShape? = registry[key]

        @JvmStatic
        internal fun create(key: HTShapeKey): HTShape = HTShape(key).also {
            registry.putIfAbsent(key, it)
            LOGGER.info("Shape: $key registered!")
        }

    }

}