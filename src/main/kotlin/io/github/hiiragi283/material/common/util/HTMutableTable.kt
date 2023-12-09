package io.github.hiiragi283.material.common.util

interface HTMutableTable<R, C, V> : HTTable<R, C, V> {

    fun put(rowKey: R, columnKey: C, value: V)

    fun put(triple: Triple<R, C, V>) {
        put(triple.first, triple.second, triple.third)
    }

    fun putAll(table: HTTable<out R, out C, out V>)

    fun remove(rowKey: R, columnKey: C): V?

    fun clear()

    class Impl<R, C, V>(triples: Collection<Triple<R, C, V>>) : HTMutableTable<R, C, V> {

        private val valueMap: MutableMap<Pair<R, C>, V> = hashMapOf()

        override val rowMap: Map<R, Map<C, V>>
            get() = rowMapInner
        private val rowMapInner: MutableMap<R, MutableMap<C, V>> = hashMapOf()

        override val columnMap: Map<C, Map<R, V>>
            get() = columnMapInner
        private val columnMapInner: MutableMap<C, MutableMap<R, V>> = hashMapOf()

        init {
            triples.forEach(this::put)
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

        //    HTMutableTable    //

        override fun put(rowKey: R, columnKey: C, value: V) {
            valueMap[rowKey to columnKey] = value
            rowMapInner.computeIfAbsent(rowKey) { hashMapOf() }[columnKey] = value
            columnMapInner.computeIfAbsent(columnKey) { hashMapOf() }[rowKey] = value
        }

        override fun putAll(table: HTTable<out R, out C, out V>) {
            table.entries.forEach(::put)
        }

        override fun remove(rowKey: R, columnKey: C): V? {
            return null
        }

        override fun clear() {
            rowMapInner.clear()
            columnMapInner.clear()
            valueMap.clear()
        }

    }

}

inline fun <R, C, V> HTMutableTable<R, C, V>.computeIfAbsent(row: R, column: C, mapping: (R, C) -> V): V {
    var result: V? = this.get(row, column)
    if (result == null) {
        this.put(row, column, mapping(row, column))
        result = this.get(row, column)!!
    }
    return result
}