package io.github.hiiragi283.material.api.registry

import io.github.hiiragi283.material.impl.registry.HTObjectKeySetImpl

interface HTObjectKeySet<T : HTObjectKey<*>> : Iterable<T> {
    fun add(key: T): Boolean

    fun addAll(keys: Iterable<T>) {
        keys.forEach(::add)
    }

    fun addAll(vararg keys: T) {
        keys.forEach(::add)
    }

    companion object {
        @JvmStatic
        fun <T : HTObjectKey<*>> create(): HTObjectKeySet<T> = HTObjectKeySetImpl()
    }
}
