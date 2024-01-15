package io.github.hiiragi283.material.api.resource

import com.google.gson.JsonObject
import io.github.hiiragi283.material.HTMaterials
import net.minecraft.data.server.recipe.*
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
import java.util.function.Consumer
import java.util.function.Predicate

object HTRuntimeDataPack : ResourcePack {
    private val DOMAINS: Set<String> = setOf("minecraft", "c", HTMaterials.MOD_ID)
    private val DATA: ConcurrentMap<Identifier, JsonObject> = ConcurrentHashMap()

    private val EXPORTER: Consumer<RecipeJsonProvider> = Consumer { provider ->
        DATA.putIfAbsent(
            provider.recipeId,
            provider.toJson(),
        )
    }

    @JvmStatic
    fun addShapedCrafting(id: Identifier, factory: ShapedRecipeJsonFactory) {
        factory.offerTo(EXPORTER, id)
    }

    @JvmStatic
    fun addShapelessCrafting(id: Identifier, factory: ShapelessRecipeJsonFactory) {
        factory.offerTo(EXPORTER, id)
    }

    @JvmStatic
    fun addSmelting(id: Identifier, factory: CookingRecipeJsonFactory) {
        factory.offerTo(EXPORTER, id)
    }

    @JvmStatic
    fun addSingleItemRecipe(id: Identifier, factory: SingleItemRecipeJsonFactory) {
        factory.offerTo(EXPORTER, id)
    }

    @JvmStatic
    fun addSmithingRecipe(id: Identifier, factory: SmithingRecipeJsonFactory) {
        factory.offerTo(EXPORTER, id)
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
        ?.takeIf { type == ResourceType.SERVER_DATA }

    override fun findResources(
        type: ResourceType,
        namespace: String,
        prefix: String,
        maxDepth: Int,
        pathFilter: Predicate<String>
    ): Collection<Identifier> = when {
        type != ResourceType.SERVER_DATA -> setOf()
        namespace !in DOMAINS -> setOf()
        else -> DATA.keys.filter { id: Identifier -> id.path.startsWith(prefix) }
            .filter { id: Identifier -> pathFilter.test(id.path) }
    }

    override fun contains(type: ResourceType, id: Identifier): Boolean = type == ResourceType.SERVER_DATA && id in DATA

    override fun getNamespaces(type: ResourceType): Set<String> =
        if (type == ResourceType.SERVER_DATA) DOMAINS else setOf()

    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    override fun <T> parseMetadata(metaReader: ResourceMetadataReader<T>): T? =
        if (metaReader is PackResourceMetadataReader) PackResourceMetadata(LiteralText(""), 6) as? T else null

    override fun getName(): String = "${HTMaterials.MOD_NAME}'s Runtime Data Pack"

}