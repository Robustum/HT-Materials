package io.github.hiiragi283.material.api.resource

import com.google.gson.JsonObject
import io.github.hiiragi283.material.HTMaterials
import net.minecraft.data.client.model.BlockStateModelGenerator
import net.minecraft.data.client.model.BlockStateSupplier
import net.minecraft.data.client.model.Model
import net.minecraft.resource.ResourcePack
import net.minecraft.resource.ResourceType
import net.minecraft.resource.metadata.PackResourceMetadata
import net.minecraft.resource.metadata.PackResourceMetadataReader
import net.minecraft.resource.metadata.ResourceMetadataReader
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.Predicate

object HTRuntimeResourcePack : ResourcePack {
    private val DOMAINS: Set<String> = setOf("minecraft", "c", HTMaterials.MOD_ID)
    private val DATA: ConcurrentMap<Identifier, JsonObject> = ConcurrentHashMap()

    @JvmStatic
    fun addBlockState(id: Identifier, blockStateSupplier: BlockStateSupplier) {
        DATA[id] = blockStateSupplier.get().asJsonObject
    }

    @JvmStatic
    fun addModel(id: Identifier) {
        DATA[id]
    }

    //    ResourcePack    //

    override fun close() {}

    @Throws(IOException::class)
    override fun openRoot(fileName: String): InputStream = throw IOException("")

    @Throws(IOException::class)
    override fun open(type: ResourceType, id: Identifier): InputStream? = DATA[id]
        ?.let(JsonObject::toString)
        ?.let(String::toByteArray)
        ?.let(::ByteArrayInputStream)
        ?.takeIf { type == ResourceType.CLIENT_RESOURCES }

    override fun findResources(
        type: ResourceType,
        namespace: String,
        prefix: String,
        maxDepth: Int,
        pathFilter: Predicate<String>
    ): Collection<Identifier> = when {
        type != ResourceType.CLIENT_RESOURCES -> setOf()
        namespace !in DOMAINS -> setOf()
        else -> DATA.keys.filter { id: Identifier -> id.path.startsWith(prefix) }
            .filter { id: Identifier -> pathFilter.test(id.path) }
    }

    override fun contains(type: ResourceType, id: Identifier): Boolean =
        type == ResourceType.CLIENT_RESOURCES && id in DATA

    override fun getNamespaces(type: ResourceType): Set<String> =
        if (type == ResourceType.CLIENT_RESOURCES) DOMAINS else setOf()

    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    override fun <T> parseMetadata(metaReader: ResourceMetadataReader<T>): T? =
        if (metaReader is PackResourceMetadataReader) PackResourceMetadata(LiteralText(""), 6) as? T else null

    override fun getName(): String = "${HTMaterials.MOD_NAME}'s Runtime Data Pack"

}