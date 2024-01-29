package io.github.hiiragi283.material.api.resource

import com.google.gson.JsonElement
import net.minecraft.advancement.Advancement
import net.minecraft.block.Block
import net.minecraft.data.server.recipe.*
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootTable
import net.minecraft.util.Identifier
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.Consumer

object HTRuntimeDataManager {
    //    Advancement    //

    private val ADVANCEMENTS: ConcurrentMap<Identifier, JsonElement> = ConcurrentHashMap()

    @JvmStatic
    fun advancementConsumer(consumer: Consumer<Map<Identifier, JsonElement>>) {
        consumer.accept(ADVANCEMENTS)
    }

    @JvmStatic
    fun addAdvancement(id: Identifier, task: Advancement.Task) {
        ADVANCEMENTS[id] = task.toJson()
    }

    //    Loot    //

    private val LOOT_TABLES: ConcurrentMap<Identifier, JsonElement> = ConcurrentHashMap()

    @JvmStatic
    fun lootTableConsumer(consumer: Consumer<Map<Identifier, JsonElement>>) {
        consumer.accept(LOOT_TABLES)
    }

    @JvmStatic
    fun addBlockLootTable(block: Block, builder: LootTable.Builder) {
        LOOT_TABLES[block.lootTableId] = LootManager.toJson(builder.build())
    }

    //    Recipe    //

    private val RECIPES: ConcurrentMap<Identifier, JsonElement> = ConcurrentHashMap()

    @JvmStatic
    fun recipeConsumer(consumer: Consumer<Map<Identifier, JsonElement>>) {
        consumer.accept(RECIPES)
    }

    private val EXPORTER: Consumer<RecipeJsonProvider> = Consumer { provider ->
        RECIPES.putIfAbsent(
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

    @JvmStatic
    fun addRecipe(id: Identifier, json: JsonElement) {
        RECIPES[id] = json
    }
}
