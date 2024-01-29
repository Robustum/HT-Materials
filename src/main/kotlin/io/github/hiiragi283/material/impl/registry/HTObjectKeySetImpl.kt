package io.github.hiiragi283.material.impl.registry

import io.github.hiiragi283.material.api.registry.HTObjectKey
import io.github.hiiragi283.material.api.registry.HTObjectKeySet

internal class HTObjectKeySetImpl<T : HTObjectKey<*>> : HTObjectKeySet<T> {
    private val backingSet: MutableSet<T> = linkedSetOf()

    override fun add(key: T): Boolean = backingSet.add(key)

    override fun iterator(): Iterator<T> = backingSet.iterator()
}
