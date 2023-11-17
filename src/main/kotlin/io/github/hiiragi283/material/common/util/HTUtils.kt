@file:JvmName("HTUtil")
@file:Suppress("unused")

package io.github.hiiragi283.material.common.util

import net.minecraft.util.Identifier
import java.util.function.Function

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun Identifier.prefix(prefix: String) = Identifier(this.namespace, prefix + this.path)

fun Identifier.suffix(suffix: String) = Identifier(this.namespace, this.path + suffix)

fun Identifier.modify(function: Function<String, String>) = Identifier(this.namespace, function.apply(this.path))