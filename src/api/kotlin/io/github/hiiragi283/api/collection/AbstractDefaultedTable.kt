package io.github.hiiragi283.api.collection

import com.google.common.collect.Table
import io.github.hiiragi283.api.extention.computeIfAbsent
import io.github.hiiragi283.api.extention.forEach

abstract class AbstractDefaultedTable<R : Any, C : Any, V : Any>(protected val mapping: (R, C) -> V) :
    DefaultedTable<R, C, V> {
    abstract val backingTable: Table<R, C, V>

    override fun getOrCreate(rowKey: R, columnKey: C): V = backingTable.computeIfAbsent(rowKey, columnKey, mapping)

    override fun forEach(action: (R, C, V) -> Unit) = backingTable.forEach { cell -> action(cell.rowKey!!, cell.columnKey!!, cell.value!!) }

    override fun toTable(): Table<R, C, V> = backingTable

    override fun iterator(): Iterator<V> = backingTable.values().iterator()
}
