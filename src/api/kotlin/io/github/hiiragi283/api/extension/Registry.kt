package io.github.hiiragi283.api.extension

import com.mojang.serialization.DynamicOps
import com.mojang.serialization.Keyable
import net.minecraft.util.Identifier
import java.util.stream.Stream

fun <T> T.id(function: (T) -> Identifier): Identifier = function(this)

fun simpleKeyable(collection: Collection<String>): Keyable = object : Keyable {
    override fun <T> keys(ops: DynamicOps<T>): Stream<T> = collection.stream().map(ops::createString)
}

fun <E> simpleKeyable(collection: Collection<E>, mapper: (E) -> String): Keyable = object : Keyable {
    override fun <T> keys(ops: DynamicOps<T>): Stream<T> = collection.stream().map(mapper).map(ops::createString)
}
