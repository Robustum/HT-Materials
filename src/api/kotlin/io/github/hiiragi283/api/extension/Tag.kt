package io.github.hiiragi283.api.extension

import net.fabricmc.fabric.mixin.tag.extension.AccessorFluidTags
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.*
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.*

@Suppress("UNCHECKED_CAST")
inline fun <reified T> getTagGroup(): TagGroup<T> = when (T::class.java) {
    Block::class.java -> BlockTags.getTagGroup()
    EntityType::class.java -> EntityTypeTags.getTagGroup()
    Fluid::class.java -> AccessorFluidTags.getRequiredTags().group
    Item::class.java -> ItemTags.getTagGroup()
    else -> TagGroup.createEmpty()
} as TagGroup<T>

fun <T> buildTagMap(tags: Map<Identifier, Tag.Builder>, registryGetter: (Identifier) -> T?): Map<Identifier, Tag<T>> {
    val map: MutableMap<Identifier, Tag<T>> = hashMapOf()
    while (tags.isNotEmpty()) {
        var bl = false
        val iterator: MutableIterator<Map.Entry<Identifier, Tag.Builder>> = tags.entries.toMutableSet().iterator()
        while (iterator.hasNext()) {
            val entry: Map.Entry<Identifier, Tag.Builder> = iterator.next()
            val optional: Optional<Tag<T>> = entry.value.build(map::get, registryGetter)
            if (!optional.isPresent) continue
            map[entry.key] = optional.get()
            iterator.remove()
            bl = true
        }
        if (bl) continue
        break
    }
    return map
}

//    Builder    //

fun Tag.Builder.add(block: Block, source: String): Tag.Builder = apply {
    add(block.id(Registry.BLOCK::getId), source)
}

fun Tag.Builder.add(item: Item, source: String): Tag.Builder = apply {
    add(item.id(Registry.ITEM::getId), source)
}

fun Tag.Builder.add(fluid: Fluid, source: String): Tag.Builder = apply {
    add(fluid.id(Registry.FLUID::getId), source)
}

fun Tag.Builder.merge(other: Tag.Builder): Tag.Builder = apply {
    other.streamEntries().forEach(this::add)
}
