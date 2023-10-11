package hiiragi283.material.api.recipe

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import hiiragi283.material.util.HiiragiConstants
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import java.util.function.Predicate

sealed class ItemIngredient(val count: Long) : Predicate<ItemStack> {

    abstract fun getMatchingStacks(): Collection<ItemStack>

    abstract fun toJson(): JsonObject

    fun write(buf: PacketByteBuf) {
        buf.writeCollection(getMatchingStacks(), PacketByteBuf::writeItemStack)
    }

    fun getItemStack(item: ItemConvertible) = ItemStack(item, count.toInt())

    companion object {

        @JvmStatic
        fun fromJson(jsonObject: JsonObject): ItemIngredient {
            val count: Long = jsonObject.getAsJsonPrimitive(HiiragiConstants.COUNT).asLong
            val hasItems: Boolean = jsonObject.has(HiiragiConstants.ITEM)
            val hasTag: Boolean = jsonObject.has(HiiragiConstants.TAG)
            return when {
                hasItems && hasTag -> throw JsonParseException("An ingredient entry is either a tag or an item, not both")

                hasItems -> {
                    val items: Array<Item> = JsonHelper.getArray(jsonObject, HiiragiConstants.ITEM)
                        .map { it.asString }
                        .map(::Identifier)
                        .map(Registry.ITEM::get)
                        .toTypedArray()
                    Items(*items, count = count)
                }

                hasTag -> {
                    val tag: TagKey<Item> = TagKey.of(
                        Registry.ITEM_KEY,
                        Identifier(JsonHelper.getString(jsonObject, HiiragiConstants.TAG))
                    )
                    Tags(tag, count)
                }

                else -> throw JsonParseException("An ingredient entry needs either a tag or an item")
            }
        }

        @JvmStatic
        fun read(buf: PacketByteBuf) {

        }

    }

    //    ItemConvertible    //

    class Items(vararg items: ItemConvertible, count: Long = 1) : ItemIngredient(count) {

        private val items: List<ItemConvertible> = items.toList()

        override fun getMatchingStacks(): Collection<ItemStack> = items.map(::getItemStack)

        override fun test(stack: ItemStack): Boolean =
            stack.count >= count && items.map(ItemConvertible::asItem).any(stack::isOf)

        override fun toJson(): JsonObject {
            val root = JsonObject()
            //items
            val jsonArray = JsonArray()
            items.map(ItemConvertible::asItem)
                .map(Registry.ITEM::getId)
                .map(Identifier::toString)
                .forEach(jsonArray::add)
            root.add(HiiragiConstants.ITEM, jsonArray)
            //count
            root.addProperty(HiiragiConstants.COUNT, count)
            return root
        }

    }

    //    Tag    //

    class Tags(val tag: TagKey<Item>, count: Long = 1) : ItemIngredient(count) {

        override fun getMatchingStacks(): Collection<ItemStack> =
            Registry.ITEM.iterateEntries(tag)
                .map(RegistryEntry<Item>::value)
                .map(::getItemStack)

        override fun test(stack: ItemStack): Boolean = stack.count >= count && stack.isIn(tag)

        override fun toJson(): JsonObject {
            val root = JsonObject()
            //tag
            root.addProperty(HiiragiConstants.TAG, tag.id.toString())
            //count
            root.addProperty(HiiragiConstants.COUNT, count)
            return root
        }

    }

    //    Custom    //

    class Custom(
        val matchingStacks: () -> Collection<ItemStack>,
        val predicate: (ItemStack) -> Boolean,
        count: Long = 1
    ) : ItemIngredient(count) {

        override fun getMatchingStacks(): Collection<ItemStack> = matchingStacks()

        override fun test(stack: ItemStack): Boolean = predicate(stack)

        override fun toJson(): JsonObject {
            throw UnsupportedOperationException("Json serialization is not allowed for ItemIngredient.Custom!!")
        }

    }

}