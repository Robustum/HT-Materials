package io.github.hiiragi283.api.tag

import io.github.hiiragi283.api.extension.createEvent
import net.fabricmc.fabric.api.event.Event
import net.minecraft.tag.TagManager

fun interface TagsUpdatedEvent {
    fun onUpdated(tagManager: TagManager, isClient: Boolean)

    companion object {
        @JvmField
        val EVENT: Event<TagsUpdatedEvent> = createEvent { callbacks ->
            TagsUpdatedEvent { manager, isClient -> callbacks.forEach { it.onUpdated(manager, isClient) } }
        }
    }
}
