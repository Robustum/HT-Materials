package io.github.hiiragi283.api.util.collection

import java.util.function.BiConsumer

interface DefaultedMap<K, V> {
    fun getOrCreate(key: K): V

    fun forEach(biConsumer: BiConsumer<K, V>)

    fun forEach(action: (K, V) -> Unit)

    fun toMap(): Map<K, V>
}
