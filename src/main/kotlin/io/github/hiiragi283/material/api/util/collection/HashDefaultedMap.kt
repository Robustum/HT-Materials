package io.github.hiiragi283.material.api.util.collection

class HashDefaultedMap<K, V>(mapping: (K) -> V) : AbstractDefaultedMap<K, V>(mapping) {
    override val backingMap: MutableMap<K, V> = hashMapOf()
}
