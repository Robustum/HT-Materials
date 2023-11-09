package io.github.hiiragi283.material.common

import net.minecraft.util.Identifier
import java.util.function.Function

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun Identifier.append(path: String) = Identifier(this.namespace, this.path + path)

fun Identifier.append(function: Function<String, String>) = Identifier(this.namespace, function.apply(this.path))