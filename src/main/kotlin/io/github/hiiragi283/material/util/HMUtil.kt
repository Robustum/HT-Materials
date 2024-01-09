@file:JvmName("HTUtil")

package io.github.hiiragi283.material.util

import com.google.gson.JsonObject
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.api.metadata.ModMetadata
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import java.util.function.Function

//    Block    //

fun Item.asBlock(): Block = (this as? BlockItem)?.block ?: Blocks.AIR

fun Block.isAir(): Boolean = this == Blocks.AIR

//    Fluid    //

fun Fluid.checkNotEmpty() = this.also { fluid: Fluid ->
    check(fluid == Fluids.EMPTY) { "The Entry: $this is empty!" }
}

fun Fluid.asBlock(): Block = this.defaultState.blockState.block

//    Item   //

fun ItemConvertible.checkItemNotAir(): Item = this.asItem().also { item: Item ->
    check(!item.isAir()) { "The Entry: $this has no valid Item!" }
}

fun Item.isAir(): Boolean = this == Items.AIR

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun Identifier.prefix(prefix: String) = Identifier(this.namespace, prefix + this.path)

fun Identifier.suffix(suffix: String) = Identifier(this.namespace, this.path + suffix)

fun Identifier.modify(function: Function<String, String>) = Identifier(this.namespace, function.apply(this.path))

//    Json    //

fun buildJson(builderAction: JsonObject.() -> Unit): JsonObject = JsonObject().apply(builderAction)

//    Loader    //

fun getAllModId(): Collection<String> = FabricLoader.getInstance()
    .allMods
    .map(ModContainer::getMetadata)
    .map(ModMetadata::getId)

fun getEnvType(): EnvType = FabricLoader.getInstance().environmentType

fun isDevEnv(): Boolean = FabricLoader.getInstance().isDevelopmentEnvironment

fun isModLoaded(id: String): Boolean = FabricLoader.getInstance().isModLoaded(id)

//    Storage    //

@Suppress("UnstableApiUsage")
fun getTransaction(): Transaction = Transaction.getCurrentUnsafe()?.openNested() ?: Transaction.openOuter()