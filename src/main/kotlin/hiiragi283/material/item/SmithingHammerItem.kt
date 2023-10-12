package hiiragi283.material.item

import hiiragi283.material.api.item.HiiragiItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack

object SmithingHammerItem : HiiragiItem(FabricItemSettings().maxCount(1).maxDamage(63)) {

    override fun getRecipeRemainder(stack: ItemStack): ItemStack = stack.copy().also { it.damage += 1 }

    override fun hasRecipeRemainder(): Boolean = true

}