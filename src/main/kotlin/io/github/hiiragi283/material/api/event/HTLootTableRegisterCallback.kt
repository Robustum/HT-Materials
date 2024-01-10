package io.github.hiiragi283.material.api.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootTable
import net.minecraft.util.Identifier

fun interface HTLootTableRegisterCallback {

    fun onLootTableRegister(handler: Handler)

    companion object {
        @JvmField
        val EVENT: Event<HTLootTableRegisterCallback> =
            EventFactory.createArrayBacked(HTLootTableRegisterCallback::class.java) { callbacks ->
                HTLootTableRegisterCallback { handler -> callbacks.forEach { it.onLootTableRegister(handler) } }
            }
    }

    class Handler(private val map: MutableMap<Identifier, com.google.gson.JsonElement>) {

        fun addTable(tableId: Identifier, lootTable: LootTable.Builder) {
            map[tableId] = LootManager.toJson(lootTable.build())
        }

        fun removeTable(tableId: Identifier) {
            map.remove(tableId)
        }

    }

}