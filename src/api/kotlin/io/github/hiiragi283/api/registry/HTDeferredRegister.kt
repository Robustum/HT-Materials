package io.github.hiiragi283.api.registry

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import java.util.function.Supplier

class HTDeferredRegister<T>(val registryKey: RegistryKey<Registry<T>>, val namespace: String) {
    private val entryMap: MutableMap<HTRegistryObject<T>, Supplier<out T>> = mutableMapOf()
    val entries: Set<HTRegistryObject<T>>
        get() = entryMap.keys
    private var isInitialized: Boolean = false

    fun register(path: String, supplier: Supplier<out T>): HTRegistryObject<T> {
        check(!isInitialized) { "DeferredRegister already initialized!" }
        val id = Identifier(namespace, path)
        val registryObject: HTRegistryObject<T> = HTRegistryObject(id)
        check(entryMap.putIfAbsent(registryObject, supplier) == null) { "Duplicate registration found; $id!" }
        return registryObject
    }

    fun register(registry: Registry<T>) {
        check(!isInitialized) { "DeferredRegister already initialized!" }
        entryMap.forEach { (obj: HTRegistryObject<T>, supplier: Supplier<out T>) ->
            Registry.register(registry, obj.id, supplier.get())
            obj.updateValue(registry)
        }
        isInitialized = true
    }
}
