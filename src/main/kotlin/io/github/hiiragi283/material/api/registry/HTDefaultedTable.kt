package io.github.hiiragi283.material.api.registry

import io.github.hiiragi283.material.impl.registry.HTDefaultedTableImpl

interface HTDefaultedTable<R : Any, C : Any, V : Any> : Iterable<V> {
    fun getOrCreate(rowKey: R, columnKey: C): V

    fun forEach(action: (R, C, V) -> Unit)

    companion object {
        @JvmStatic
        fun <R : Any, C : Any, V : Any> create(mapping: (R, C) -> V): HTDefaultedTable<R, C, V> = HTDefaultedTableImpl(mapping)
    }
}
