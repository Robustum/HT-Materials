@file:JvmName("TableUtil")

package io.github.hiiragi283.material.common.util

import com.google.common.collect.Table

//    Table    //

inline fun <R, C, V> Table<R, C, V>.forEach(action: (Table.Cell<R, C, V>) -> Unit) {
    for (cell in this.cellSet()) action(cell)
}

inline fun <R, C, V> Table<R, C, V>.computeIfAbsent(row: R & Any, column: C & Any, mapping: (R, C) -> V & Any): V {
    var result: V? = this.get(row, column)
    if (result == null) {
        this.put(row, column, mapping(row, column))
        result = this.get(row, column)!!
    }
    return result
}