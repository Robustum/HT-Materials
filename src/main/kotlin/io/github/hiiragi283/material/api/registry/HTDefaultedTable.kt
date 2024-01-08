package io.github.hiiragi283.material.api.registry

import com.google.common.collect.Table
import io.github.hiiragi283.material.impl.registry.HTDefaultedTableImpl
import java.util.function.Consumer

interface HTDefaultedTable<R: Any, C: Any, V: Any> {

    fun getOrCreate(rowKey: R, columnKey: C): V

    fun forEach(consumer: Consumer<Table.Cell<R, C, V>>)

    fun forEach(action: (R, C, V) -> Unit)

    companion object {
        @JvmStatic
        fun <R: Any, C: Any, V: Any> create(mapping: (R, C) -> V): HTDefaultedTable<R, C, V> = HTDefaultedTableImpl(mapping)
    }

}