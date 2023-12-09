@file:JvmName("TableUtil")

package io.github.hiiragi283.material.common.util

fun <R, C, V> tableOf(vararg triple: Triple<R, C, V>): HTTable<R, C, V> = HTTable.Impl(triple.toList())

fun <R, C, V> tableOf(table: HTTable<out R, out C, out V>): HTTable<R, C, V> = HTTable.Impl(table.entries)

fun <R, C, V> mutableTableOf(vararg triple: Triple<R, C, V>): HTMutableTable<R, C, V> =
    HTMutableTable.Impl(triple.toList())