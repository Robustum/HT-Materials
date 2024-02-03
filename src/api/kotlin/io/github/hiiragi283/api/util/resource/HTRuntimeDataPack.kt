package io.github.hiiragi283.api.util.resource

import com.google.gson.JsonElement
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.util.modify
import io.github.hiiragi283.api.util.suffix
import net.minecraft.advancement.Advancement
import net.minecraft.block.Block
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootTable
import net.minecraft.resource.ResourceType
import net.minecraft.resource.metadata.PackResourceMetadata
import net.minecraft.resource.metadata.PackResourceMetadataReader
import net.minecraft.resource.metadata.ResourceMetadataReader
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.Consumer

object HTRuntimeDataPack : HTRuntimePackBase(ResourceType.SERVER_DATA) {
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

    //    Advancements    //

    @JvmStatic
    fun addAdvancement(id: Identifier, task: Advancement.Task) {
        _data[id.modify { "advancements/$it.json" }] = task.toJson()
    }

    //    Loot    //

    @JvmStatic
    fun addBlockLootTable(block: Block, builder: LootTable.Builder) {
        _data[block.lootTableId] = LootManager.toJson(builder.build())
    }

    //    Recipe    //

    private val EXPORTER: Consumer<RecipeJsonProvider> = Consumer { provider ->
        addRecipe(provider.recipeId.suffix(".json"), provider.toJson())
    }

    @JvmStatic
    fun addRecipe(consumer: Consumer<Consumer<RecipeJsonProvider>>) {
        consumer.accept(EXPORTER)
    }

    @JvmStatic
    fun addRecipe(id: Identifier, jsonElement: JsonElement) {
        _data[id.modify { "recipes/$it.json" }] = jsonElement
    }

    //    ResourcePack    //

    @Suppress("UNCHECKED_CAST")
    override fun <T> parseMetadata(metaReader: ResourceMetadataReader<T>): T? =
        PackResourceMetadata(LiteralText(""), 6).takeIf { metaReader is PackResourceMetadataReader } as? T

    override fun getName(): String = "${HTMaterialsAPI.MOD_NAME}'s Runtime Data Pack"
}
