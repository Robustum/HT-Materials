package hiiragi283.material.api.registry

import hiiragi283.material.RagiMaterials
import hiiragi283.material.util.hiiragiId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.item.ItemConvertible
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pers.solid.brrp.v1.api.RuntimeResourcePack

sealed class HiiragiRegistry<T>(private val title: String) {

    private val logger: Logger = LogManager.getLogger("${RagiMaterials.MOD_NAME}/$title")

    //    Lock    //

    private var isLocked: Boolean = false

    fun lock() {
        isLocked = true
        logger.info("This Registry has been locked!")
    }

    //    Registration    //

    val entriesMap: Map<String, Entry<T>>
        get() = entriesInternal
    private val entriesInternal: MutableMap<String, Entry<T>> = mutableMapOf()

    val map: Map<String, T>
        get() = mapInternal
    private val mapInternal: MutableMap<String, T> = mutableMapOf()

    open fun getValue(key: String): T? = mapInternal[key]

    fun getValues(): Collection<T> = mapInternal.values

    @Synchronized
    fun <U : Entry<T>> register(name: String, entry: U): U {
        if (isLocked) {
            throw IllegalStateException("[$title] This registry is already locked!!")
        }
        if (entriesInternal.putIfAbsent(name, entry) != null) {
            throw IllegalStateException("[$title] The key: $name is already registered!")
        }
        mapInternal[name] = entry.getValue()
        entry.onRegister(name)
        return entry
    }

    fun register(registry: Registry<T>) {
        entriesInternal.forEach { (name: String, entry: Entry<T>) ->
            Registry.register(registry, hiiragiId(name), entry.getValue())
        }
        logger.info("All entries have been registered!")
        lock()
    }

    fun addResources(resourcePack: RuntimeResourcePack) {
        entriesInternal.values.forEach { it.addResources(resourcePack) }
        logger.info("All resources have been registered! ")
    }

    @Environment(EnvType.CLIENT)
    fun registerClient() {
        entriesInternal.values.forEach { it.onRegisterClient() }
        logger.info("All client initialization have been done!")
    }

    //    Simple    //

    class Simple<T>(title: String) : HiiragiRegistry<T>(title)

    //    Defaulted    //

    class Defaulted<T>(title: String, private val defaultValue: Entry<T>) : HiiragiRegistry<T>(title) {

        init {
            register("default", defaultValue)
        }

        override fun getValue(key: String): T = super.getValue(key) ?: defaultValue.getValue()

    }

    //    Entry    //

    interface Entry<T> : ItemConvertible {

        @Suppress("UNCHECKED_CAST")
        fun getValue(): T = this as T

        fun onRegister(name: String) {
        }

        fun addResources(resourcePack: RuntimeResourcePack) {
        }

        @Environment(EnvType.CLIENT)
        fun onRegisterClient() {
        }

        fun toNbt(): NbtCompound

        fun toPacket(buf: PacketByteBuf)

    }

}