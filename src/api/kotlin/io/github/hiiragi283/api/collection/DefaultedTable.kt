package io.github.hiiragi283.api.collection

import com.google.common.collect.Table

interface DefaultedTable<R : Any, C : Any, V : Any> : Iterable<V> {
    fun getOrCreate(rowKey: R, columnKey: C): V

    fun forEach(action: (R, C, V) -> Unit)

    fun toTable(): Table<R, C, V>
}
