@file:JvmName("HTUtil")
@file:Suppress("unused")

package io.github.hiiragi283.material.common.util

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import java.util.function.Function

//    Block    //

fun Item.asBlock(): Block = (this as? BlockItem)?.block ?: Blocks.AIR

fun Block.isAir(): Boolean = this == Blocks.AIR

//    Item   //

fun Item.isAir(): Boolean = this == Items.AIR

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun Identifier.prefix(prefix: String) = Identifier(this.namespace, prefix + this.path)

fun Identifier.suffix(suffix: String) = Identifier(this.namespace, this.path + suffix)

fun Identifier.modify(function: Function<String, String>) = Identifier(this.namespace, function.apply(this.path))

//    Loader    //

fun getEnvType(): EnvType = FabricLoader.getInstance().environmentType

fun isDevEnv(): Boolean = FabricLoader.getInstance().isDevelopmentEnvironment

fun isModLoaded(id: String): Boolean = FabricLoader.getInstance().isModLoaded(id)