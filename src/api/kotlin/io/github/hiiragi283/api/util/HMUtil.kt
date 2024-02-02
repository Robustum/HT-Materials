@file:JvmName("HTUtil")

package io.github.hiiragi283.api.util

import com.google.common.collect.ImmutableCollection
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.api.entrypoint.EntrypointContainer
import net.fabricmc.loader.api.metadata.ModMetadata
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluid
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import java.util.function.Function

//    Block    //

fun Item.asBlock(): Block = (this as? BlockItem)?.block ?: Blocks.AIR

fun Block.isAir(): Boolean = this == Blocks.AIR

//    Collection    //

fun <T : Any> ImmutableCollection.Builder<T>.addAll(vararg element: T) {
    element.forEach(this::add)
}

//    Fluid    //

fun Fluid.asBlock(): Block = this.defaultState.blockState.block

//    Item   //

fun Item.isAir(): Boolean = this == Items.AIR

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun Identifier.prefix(prefix: String) = Identifier(this.namespace, prefix + this.path)

fun Identifier.suffix(suffix: String) = Identifier(this.namespace, this.path + suffix)

fun Identifier.modify(function: Function<String, String>) = Identifier(this.namespace, function.apply(this.path))

//    Json    //

fun buildJson(builderAction: JsonObject.() -> Unit): JsonObject = JsonObject().apply(builderAction)

fun JsonObject.addArray(property: String, builderAction: JsonArray.() -> Unit) {
    this.add(property, JsonArray().apply(builderAction))
}

fun JsonObject.addObject(property: String, builderAction: JsonObject.() -> Unit) {
    this.add(property, buildJson(builderAction))
}

//    Loader    //

operator fun <T> EntrypointContainer<T>.component1(): T = this.entrypoint

operator fun <T> EntrypointContainer<T>.component2(): ModContainer = this.provider

fun getAllModId(): Collection<String> = FabricLoader.getInstance()
    .allMods
    .map(ModContainer::getMetadata)
    .map(ModMetadata::getId)

fun getEnvType(): EnvType = FabricLoader.getInstance().environmentType

fun isClient(): Boolean = getEnvType() == EnvType.CLIENT

fun isServer(): Boolean = getEnvType() == EnvType.SERVER

inline fun onEnv(envType: EnvType, action: () -> Unit) {
    if (getEnvType() == envType) action()
}

fun isDevEnv(): Boolean = FabricLoader.getInstance().isDevelopmentEnvironment

fun isModLoaded(id: String): Boolean = FabricLoader.getInstance().isModLoaded(id)

inline fun <reified T> invokeEntryPoints(key: String, crossinline action: T.() -> Unit) {
    FabricLoader.getInstance().invokeEntrypoints(key, T::class.java) { action(it) }
}

//    Storage    //

@Suppress("UnstableApiUsage")
fun getTransaction(): Transaction = Transaction.getCurrentUnsafe()?.openNested() ?: Transaction.openOuter()
