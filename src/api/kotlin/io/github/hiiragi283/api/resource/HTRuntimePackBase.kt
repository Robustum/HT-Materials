package io.github.hiiragi283.api.resource

import com.google.gson.JsonElement
import net.minecraft.resource.ResourcePack
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.function.Predicate

abstract class HTRuntimePackBase(private val type: ResourceType) : ResourcePack {
    abstract val domains: Set<String>
    abstract val data: Map<Identifier, JsonElement>

    override fun close() {}

    @Throws(IOException::class)
    override fun openRoot(fileName: String): InputStream = throw IOException("")

    @Throws(IOException::class)
    override fun open(type: ResourceType, id: Identifier): InputStream? = data[id]
        ?.let(JsonElement::toString)
        ?.let(String::toByteArray)
        ?.let(::ByteArrayInputStream)
        ?.takeIf { type == this.type }

    override fun findResources(
        type: ResourceType,
        namespace: String,
        prefix: String,
        maxDepth: Int,
        pathFilter: Predicate<String>,
    ): Collection<Identifier> = when {
        type != this.type -> setOf()
        namespace !in domains -> setOf()
        else ->
            data.keys
                .filter { id: Identifier -> id.path.startsWith(prefix) }
                .filter { id: Identifier -> pathFilter.test(id.path) }
    }

    override fun contains(type: ResourceType, id: Identifier): Boolean = type == this.type && id in data

    override fun getNamespaces(type: ResourceType): Set<String> = if (type == this.type) domains else setOf()
}
