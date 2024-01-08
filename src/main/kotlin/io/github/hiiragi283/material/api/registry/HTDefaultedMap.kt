package io.github.hiiragi283.material.api.registry

import io.github.hiiragi283.material.impl.registry.HTDefaultedMapImpl
import java.util.function.BiConsumer

interface HTDefaultedMap<K, V> {

    fun getOrCreate(key: K): V

    fun forEach(biConsumer: BiConsumer<K, V>)

    fun forEach(action: (K, V) -> Unit)

    companion object {
        @JvmStatic
        fun <K, V> create(mapping: (K) -> V): HTDefaultedMap<K, V> = HTDefaultedMapImpl(mapping)
    }

}