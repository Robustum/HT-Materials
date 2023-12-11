@file:JvmName("TableUtil")

package io.github.hiiragi283.material.common.util

import com.google.common.collect.Table

inline fun <R : Any, C : Any, V : Any> Table<R, C, V>.computeIfAbsent(
    rowKey: R,
    columnKey: C,
    mapping: (R, C) -> V
): V {
    if (!this.contains(rowKey, columnKey)) {
        this.put(rowKey, columnKey, mapping(rowKey, columnKey))
    }
    return this.get(rowKey, columnKey)!!
}