package io.github.hiiragi283.material.dictionary

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extention.openSimpleScreen
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.Rarity
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object MaterialDictionaryItem : Item(
    FabricItemSettings()
        .group(HTMaterialsAPI.INSTANCE.itemGroup())
        .maxCount(1)
        .rarity(Rarity.EPIC),
) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack: ItemStack = user.getStackInHand(hand)
        if (!world.isClient) {
            user.openSimpleScreen(name) { syncId: Int, _, player: PlayerEntity ->
                MaterialDictionaryScreenHandler(syncId, player)
            }
            return TypedActionResult.success(stack)
        }
        return TypedActionResult.consume(stack)
    }
}
