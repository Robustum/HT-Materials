package io.github.hiiragi283.api.util.collection

import java.util.function.BiConsumer

abstract class AbstractDefaultedMap<K, V>(protected val mapping: (K) -> V) : DefaultedMap<K, V> {
    abstract val backingMap: MutableMap<K, V>

    override fun getOrCreate(key: K): V = backingMap.computeIfAbsent(key, mapping)

    override fun forEach(biConsumer: BiConsumer<K, V>) = backingMap.forEach(biConsumer)

    override fun forEach(action: (K, V) -> Unit) = backingMap.forEach(action)

    override fun toMap(): Map<K, V> = backingMap
}
