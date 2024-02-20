package io.github.hiiragi283.api.registry

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

class HTRegistryObject<T>(val id: Identifier) : Supplier<T> {
    private var value: T? = null

    override fun get(): T = checkNotNull(value) { "Not found registry object; $id!" }

    fun updateValue(registry: Registry<T>) {
        value = if (registry.containsId(id)) registry.get(id) else null
    }
}
