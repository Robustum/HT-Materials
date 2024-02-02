package io.github.hiiragi283.api.util.collection

class LinkedHashDefaultedMap<K, V>(mapping: (K) -> V) : AbstractDefaultedMap<K, V>(mapping) {
    override val backingMap: MutableMap<K, V> = linkedMapOf()
}
