package io.github.hiiragi283.material.impl.registry

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.util.computeIfAbsent
import io.github.hiiragi283.material.util.forEach

internal class HTDefaultedTableImpl<R: Any, C: Any, V: Any>(private val mapping: (R, C) -> V) : HTDefaultedTable<R, C, V> {

    private val backingTable: Table<R, C, V> = HashBasedTable.create()

    override fun getOrCreate(rowKey: R, columnKey: C): V = backingTable.computeIfAbsent(rowKey, columnKey, mapping)

    override fun forEach(action: (R, C, V) -> Unit) =
        backingTable.forEach { cell -> action(cell.rowKey!!, cell.columnKey!!, cell.value!!) }

    override fun iterator(): Iterator<V> = backingTable.values().iterator()

}