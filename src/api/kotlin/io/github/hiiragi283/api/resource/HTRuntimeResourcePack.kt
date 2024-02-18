package io.github.hiiragi283.api.resource

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extention.id
import io.github.hiiragi283.api.extention.modify
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.item.Item
import net.minecraft.resource.ResourceType
import net.minecraft.resource.metadata.PackResourceMetadata
import net.minecraft.resource.metadata.PackResourceMetadataReader
import net.minecraft.resource.metadata.ResourceMetadataReader
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

object HTRuntimeResourcePack : HTRuntimePackBase(ResourceType.CLIENT_RESOURCES) {
    override val domains: Set<String>
        get() = _domains
    private val _domains: MutableSet<String> = mutableSetOf("minecraft", "c", HTMaterialsAPI.MOD_ID)

    @JvmStatic
    fun addDomain(vararg domain: String) {
        domain.forEach(_domains::add)
    }

    override val data: Map<Identifier, JsonElement>
        get() = _data
    private val _data: ConcurrentMap<Identifier, JsonElement> = ConcurrentHashMap()

    private fun getBlockStateId(block: Block): Identifier = block.id.modify { "blockstates/$it.json" }

    @JvmStatic
    fun addBlockState(block: Block, blockStateSupplier: BlockStateSupplier) {
        addBlockState(block, blockStateSupplier.get().asJsonObject)
    }

    @JvmStatic
    fun addBlockState(block: Block, json: JsonObject) {
        getBlockStateId(block).run {
            addDomain(this.namespace)
            _data[this] = json
        }
    }

    @JvmStatic
    fun addModel(item: Item, texture: Texture = Texture.layer0(item), model: Model = Models.GENERATED) {
        model.upload(ModelIds.getItemModelId(item).modify { "models/$it.json" }, texture) { id, supplier ->
            addDomain(id.namespace)
            _data[id] = supplier.get()
        }
    }

    @JvmStatic
    fun addModel(block: Block, json: JsonObject) {
        ModelIds.getBlockModelId(block).run {
            addDomain(this.namespace)
            _data[this.modify { "models/$it.json" }] = json
        }
    }

    @JvmStatic
    fun addModel(item: Item, json: JsonObject) {
        ModelIds.getItemModelId(item).run {
            addDomain(this.namespace)
            _data[this.modify { "models/$it.json" }] = json
        }
    }

    //    ResourcePack    //

    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    override fun <T> parseMetadata(metaReader: ResourceMetadataReader<T>): T? =
        PackResourceMetadata(LiteralText(name), 6).takeIf { metaReader is PackResourceMetadataReader } as? T

    override fun getName(): String = "${HTMaterialsAPI.MOD_NAME}'s Runtime Resource Pack"
}
