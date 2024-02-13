package io.github.hiiragi283.api.collection

import java.util.function.BiConsumer

interface DefaultedMap<K, V> {
    fun getOrCreate(key: K): V

    fun forEach(biConsumer: BiConsumer<K, V>)

    fun forEach(action: (K, V) -> Unit)

    fun toMap(): Map<K, V>
}

inline fun <K, V> buildDefaultedMap(noinline mapping: (K) -> V, builderAction: DefaultedMap<K, V>.() -> Unit): DefaultedMap<K, V> =
    HashDefaultedMap.create(mapping).apply(builderAction)

inline fun <K, V> buildDefaultedMap(noinline mapping: () -> V, builderAction: DefaultedMap<K, V>.() -> Unit): DefaultedMap<K, V> =
    HashDefaultedMap.create<K, V>(mapping).apply(builderAction)
