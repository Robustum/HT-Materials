package io.github.hiiragi283.api.util.collection

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table

class HashDefaultedTable<R : Any, C : Any, V : Any>(mapping: (R, C) -> V) : AbstractDefaultedTable<R, C, V>(mapping) {
    override val backingTable: Table<R, C, V> = HashBasedTable.create()
}
