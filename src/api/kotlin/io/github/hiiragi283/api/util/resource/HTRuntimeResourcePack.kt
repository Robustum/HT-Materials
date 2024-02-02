package io.github.hiiragi283.api.util.resource

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.util.modify
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.item.Item
import net.minecraft.resource.ResourcePack
import net.minecraft.resource.ResourceType
import net.minecraft.resource.metadata.PackResourceMetadata
import net.minecraft.resource.metadata.PackResourceMetadataReader
import net.minecraft.resource.metadata.ResourceMetadataReader
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.Predicate

object HTRuntimeResourcePack : ResourcePack {
    private val DOMAINS: Set<String> = setOf("minecraft", "c", HTMaterialsAPI.MOD_ID)
    private val DATA: ConcurrentMap<Identifier, JsonElement> = ConcurrentHashMap()

    private fun getBlockStateId(block: Block): Identifier = Registry.BLOCK.getId(block).modify { "blockstates/$it.json" }

    @JvmStatic
    fun addBlockState(block: Block, blockStateSupplier: BlockStateSupplier) {
        DATA[getBlockStateId(block)] = blockStateSupplier.get()
    }

    @JvmStatic
    fun addBlockState(block: Block, json: JsonObject) {
        DATA[getBlockStateId(block)] = json
    }

    @JvmStatic
    fun addModel(item: Item, texture: Texture = Texture.layer0(item), model: Model = Models.GENERATED) {
        model.upload(ModelIds.getItemModelId(item).modify { "models/$it.json" }, texture) { id, supplier ->
            DATA[id] = supplier.get()
        }
    }

    @JvmStatic
    fun addModel(block: Block, json: JsonObject) {
        DATA[ModelIds.getBlockModelId(block).modify { "models/$it.json" }] = json
    }

    @JvmStatic
    fun addModel(item: Item, json: JsonObject) {
        DATA[ModelIds.getItemModelId(item).modify { "models/$it.json" }] = json
    }

    //    ResourcePack    //

    override fun close() {}

    @Throws(IOException::class)
    override fun openRoot(fileName: String): InputStream = throw IOException("")

    @Throws(IOException::class)
    override fun open(type: ResourceType, id: Identifier): InputStream? = DATA[id]
        ?.let(JsonElement::toString)
        ?.let(String::toByteArray)
        ?.let(::ByteArrayInputStream)
        ?.takeIf { type == ResourceType.CLIENT_RESOURCES }

    override fun findResources(
        type: ResourceType,
        namespace: String,
        prefix: String,
        maxDepth: Int,
        pathFilter: Predicate<String>,
    ): Collection<Identifier> = when {
        type != ResourceType.CLIENT_RESOURCES -> setOf()
        namespace !in DOMAINS -> setOf()
        else -> DATA.keys.filter { id: Identifier -> id.path.startsWith(prefix) }
            .filter { id: Identifier -> pathFilter.test(id.path) }
    }

    override fun contains(type: ResourceType, id: Identifier): Boolean = type == ResourceType.CLIENT_RESOURCES && id in DATA

    override fun getNamespaces(type: ResourceType): Set<String> = if (type == ResourceType.CLIENT_RESOURCES) DOMAINS else setOf()

    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    override fun <T> parseMetadata(metaReader: ResourceMetadataReader<T>): T? =
        if (metaReader is PackResourceMetadataReader) PackResourceMetadata(LiteralText(""), 6) as? T else null

    override fun getName(): String = "${HTMaterialsAPI.MOD_NAME}'s Runtime Data Pack"
}
