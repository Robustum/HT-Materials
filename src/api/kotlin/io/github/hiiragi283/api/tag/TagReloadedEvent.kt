package io.github.hiiragi283.api.tag

import io.github.hiiragi283.api.extension.createEvent
import net.fabricmc.fabric.api.event.Event

fun interface TagReloadedEvent {
    fun onReloaded()

    companion object {
        @JvmField
        val EVENT: Event<TagReloadedEvent> = createEvent { callbacks ->
            TagReloadedEvent { callbacks.forEach(TagReloadedEvent::onReloaded) }
        }
    }
}
