package io.github.hiiragi283.material.api.util

import net.minecraft.loot.LootTable

interface HTCustomLootTableBlock {

    fun getLootTable(): LootTable.Builder

}