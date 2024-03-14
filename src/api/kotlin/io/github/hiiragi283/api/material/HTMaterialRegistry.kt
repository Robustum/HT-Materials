package io.github.hiiragi283.api.material

import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.Keyable
import java.util.stream.Stream

interface HTMaterialRegistry : Map<HTMaterialKey, HTMaterial>, Codec<HTMaterial>, Keyable {
    override fun <T : Any> encode(input: HTMaterial, ops: DynamicOps<T>, prefix: T): DataResult<T> =
        ops.mergeToPrimitive(prefix, ops.createString(input.key.name))

    override fun <T : Any> decode(ops: DynamicOps<T>, input: T): DataResult<Pair<HTMaterial, T>> =
        Codec.STRING.decode(ops, input).flatMap { pair: Pair<String, T> ->
            pair.first
                .let(::HTMaterialKey)
                .let(::get)
                ?.let { DataResult.success(Pair.of(it, pair.second)) }
                ?: DataResult.error("Unknown material key; ${pair.first}")
        }

    override fun <T> keys(ops: DynamicOps<T>): Stream<T> = keys.stream().map(HTMaterialKey::name).map(ops::createString)

    class Builder(private val map: MutableMap<HTMaterialKey, HTMaterial.Builder>) {
        fun getOrCreate(key: HTMaterialKey): HTMaterial.Builder = map.computeIfAbsent(key) { HTMaterial.Builder() }
    }
}
