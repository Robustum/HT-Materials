package hiiragi283.material.api.recipe

import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import java.util.function.Predicate

sealed class ItemIngredient(val count: Long) : Predicate<ItemStack> {

    abstract fun getMatchingStacks(): Collection<ItemStack>

    fun getItemStack(item: ItemConvertible) = ItemStack(item, count.toInt())

    //    ItemConvertible    //

    class Items(vararg items: ItemConvertible, count: Long = 1) : ItemIngredient(count) {

        private val items: List<ItemConvertible> = items.toList()

        override fun getMatchingStacks(): Collection<ItemStack> = items.map(::getItemStack)

        override fun test(stack: ItemStack): Boolean =
            stack.count >= count && items.map(ItemConvertible::asItem).any(stack::isOf)

    }

    //    Tag    //

    class Tags(val tag: TagKey<Item>, count: Long = 1) : ItemIngredient(count) {

        override fun getMatchingStacks(): Collection<ItemStack> =
            Registry.ITEM.iterateEntries(tag)
                .map(RegistryEntry<Item>::value)
                .map(::getItemStack)

        override fun test(stack: ItemStack): Boolean = stack.count >= count && stack.isIn(tag)

    }

    //    Custom    //

    class Custom(
        val matchingStacks: () -> Collection<ItemStack>,
        val predicate: (ItemStack) -> Boolean,
        count: Long = 1
    ) : ItemIngredient(count) {

        override fun getMatchingStacks(): Collection<ItemStack> = matchingStacks()

        override fun test(stack: ItemStack): Boolean = predicate(stack)

    }

}