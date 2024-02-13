package io.github.hiiragi283.api.collection

class HashDefaultedMap<K, V> private constructor(mapping: (K) -> V) : AbstractDefaultedMap<K, V>(mapping) {
    companion object {
        @JvmStatic
        fun <K, V> create(mapping: (K) -> V) = HashDefaultedMap(mapping)

        @JvmStatic
        fun <K, V> create(mapping: () -> V) = HashDefaultedMap<K, V> { _ -> mapping() }
    }

    override val backingMap: MutableMap<K, V> = linkedMapOf()
}
