package io.github.hiiragi283.api.transfer

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

@Suppress("UnstableApiUsage")
internal object HTCombinedProvidersImpl {
    @JvmStatic
    fun createEvent(invokeFallback: Boolean): Event<HTMaterialStorage.CombinedItemApiProvider> {
        return io.github.hiiragi283.api.extension.createEvent<HTMaterialStorage.CombinedItemApiProvider> { listeners ->
            HTMaterialStorage.CombinedItemApiProvider { context ->
                val storages: MutableList<Storage<HTMaterialVariant>> = mutableListOf()

                listeners.forEach { listener ->
                    listener.find(context)?.let(storages::add)
                }

                if (storages.isNotEmpty() && invokeFallback) {
                    HTMaterialStorage.GENERAL_COMBINED_PROVIDER.invoker().find(context)?.let(storages::add)
                }

                CombinedStorage(storages).takeIf { storages.isNotEmpty() }
            }
        }
    }

    private class Provider : ItemApiLookup.ItemApiProvider<Storage<HTMaterialVariant>, ContainerItemContext> {
        val event: Event<HTMaterialStorage.CombinedItemApiProvider> = createEvent(true)

        override fun find(itemStack: ItemStack, context: ContainerItemContext): Storage<HTMaterialVariant>? {
            check(context.itemVariant.matches(itemStack)) {
                "Query stack $itemStack and ContainerItemContext variant ${context.itemVariant} don't match."
            }
            return event.invoker().find(context)
        }
    }

    @JvmStatic
    fun getOrCreateItemEvent(item: Item): Event<HTMaterialStorage.CombinedItemApiProvider> {
        HTMaterialStorage.ITEM.registerForItems(Provider(), item)
        val existingProvider: ItemApiLookup.ItemApiProvider<Storage<HTMaterialVariant>, ContainerItemContext>? =
            HTMaterialStorage.ITEM.getProvider(item)
        return checkNotNull((existingProvider as? Provider)?.event) {
            "An incompatible provider was already registered for item $item. Provider: $existingProvider."
        }
    }
}
