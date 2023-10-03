package hiiragi283.material.api.reigstry

import hiiragi283.material.RagiMaterials
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import pers.solid.brrp.v1.api.RuntimeResourcePack

class HiiragiRegistry<T>(private val name: String) {

    //    Lock    //

    private var isLocked: Boolean = false

    fun lock() {
        isLocked = true
    }

    //    Registration    //

    private val entriesInternal: MutableMap<String, Entry<T>> = mutableMapOf()

    fun getMap(): Map<String, T> = entriesInternal.toList().associate { it.first to it.second.getValue() }

    fun getMapReversed(): Map<T, String> = entriesInternal.toList().associate { it.second.getValue() to it.first }

    fun getPath(entry: Entry<T>): String? = getMapReversed()[entry.getValue()]

    fun getKey(entry: Entry<T>): Identifier? = getPath(entry)?.let(RagiMaterials::id)

    fun <U: Entry<T>> register(path: String, entry: U): U {
        if (isLocked) {
            throw IllegalStateException("[$name] This registry is already locked!!")
        }
        if (entriesInternal.putIfAbsent(path, entry) != null) {
            throw IllegalStateException("[$name] The key: $path is already registered!")
        }
        return entry
    }

    fun register(registry: Registry<T>) {
        entriesInternal.forEach { (path: String, entry: Entry<T>) ->
            entry.onRegister(path, registry)
            Registry.register(registry, RagiMaterials.id(path), entry.getValue())
        }
        lock()
    }

    fun addResources(resourcePack: RuntimeResourcePack) {
        entriesInternal.values.forEach { it.addResources(resourcePack) }
    }

    @Environment(EnvType.CLIENT)
    fun registerClient() {
        entriesInternal.values.forEach { it.onRegisterClient() }
    }

    //    Entry    //

    interface Entry<T> : ItemConvertible {

        @Suppress("UNCHECKED_CAST")
        fun getValue(): T = this as T

        fun onRegister(path: String, registry: Registry<T>) {

        }

        fun addResources(resourcePack: RuntimeResourcePack) {

        }

        @Environment(EnvType.CLIENT)
        fun onRegisterClient() {

        }

    }

}