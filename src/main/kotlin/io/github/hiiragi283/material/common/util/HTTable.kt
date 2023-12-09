package io.github.hiiragi283.material.common.util

@JvmDefaultWithCompatibility
interface HTTable<R, C, V> {

    fun contains(rowKey: R, columnKey: C): Boolean

    fun containsRow(rowKey: R): Boolean = rowMap.containsKey(rowKey)

    fun containsColumn(columnKey: C): Boolean = columnMap.containsKey(columnKey)

    fun containsValue(value: V): Boolean

    fun get(rowKey: R, columnKey: C): V?

    fun isEmpty(): Boolean

    fun size(): Int = rowMap.values.sumOf(Map<C, V>::size)

    fun row(rowKey: R): Map<C, V>

    fun column(columnKey: C): Map<R, V>

    fun rowKeys(): Set<R> = rowMap.keys

    fun columnKeys(): Set<C> = columnMap.keys

    val entries: Set<Triple<R, C, V>>

    val values: Set<V>

    val rowMap: Map<R, Map<C, V>>

    val columnMap: Map<C, Map<R, V>>

    class Impl<R, C, V>(triples: Collection<Triple<R, C, V>>) : HTTable<R, C, V> {

        private val valueMap: MutableMap<Pair<R, C>, V> = hashMapOf()

        override val rowMap: Map<R, Map<C, V>>
            get() = rowMapInner
        private val rowMapInner: MutableMap<R, MutableMap<C, V>> = hashMapOf()

        override val columnMap: Map<C, Map<R, V>>
            get() = columnMapInner
        private val columnMapInner: MutableMap<C, MutableMap<R, V>> = hashMapOf()

        init {
            triples.forEach { (row, column, value) ->
                valueMap.putIfAbsent(row to column, value)
            }
            triples.forEach { (row, column, value) ->
                rowMapInner.computeIfAbsent(row) { hashMapOf(column to value) }.putIfAbsent(column, value)
            }
            triples.forEach { (row, column, value) ->
                columnMapInner.computeIfAbsent(column) { hashMapOf(row to value) }.putIfAbsent(row, value)
            }
        }

        //    HTTable    //

        override fun contains(rowKey: R, columnKey: C): Boolean = rowKey to columnKey in valueMap

        override fun containsValue(value: V): Boolean = valueMap.containsValue(value)

        override fun get(rowKey: R, columnKey: C): V? = valueMap[rowKey to columnKey]

        override fun isEmpty(): Boolean = valueMap.isEmpty() && rowMap.isEmpty() && columnMap.isEmpty()

        override fun row(rowKey: R): Map<C, V> = rowMapInner.computeIfAbsent(rowKey) { hashMapOf() }

        override fun column(columnKey: C): Map<R, V> = columnMapInner.computeIfAbsent(columnKey) { hashMapOf() }

        override val entries: Set<Triple<R, C, V>> =
            valueMap.map { (pair, value) -> Triple(pair.first, pair.second, value) }.toSet()

        override val values: Set<V> = valueMap.values.toSet()

    }

}

inline fun <R, C, V> HTTable<R, C, V>.forEach(action: (R, C, V) -> Unit) {
    for ((row, column, value) in entries) action(row, column, value)
}