package io.github.hiiragi283.api.shape

import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.Keyable
import java.util.stream.Stream

interface HTShapeRegistry : Map<String, HTShape>, Codec<HTShape>, Keyable {
    override fun <T : Any> encode(input: HTShape, ops: DynamicOps<T>, prefix: T): DataResult<T> =
        ops.mergeToPrimitive(prefix, ops.createString(input.name))

    override fun <T : Any> decode(ops: DynamicOps<T>, input: T): DataResult<Pair<HTShape, T>> =
        Codec.STRING.decode(ops, input).flatMap { pair: Pair<String, T> ->
            get(pair.first)
                ?.let { DataResult.success(Pair.of(it, pair.second)) }
                ?: DataResult.error("Unknown shape name; ${pair.first}")
        }

    override fun <T> keys(ops: DynamicOps<T>): Stream<T> = keys.stream().map(ops::createString)

    class Builder(private val list: MutableList<String>) {
        fun add(shape: HTShape) {
            add(shape.name)
        }

        fun add(name: String) {
            list.add(name)
        }

        operator fun plusAssign(shape: HTShape) {
            add(shape)
        }

        operator fun plusAssign(name: String) {
            add(name)
        }
    }
}
