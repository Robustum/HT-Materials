package io.github.hiiragi283.api.tag

import io.github.hiiragi283.api.extension.createEvent
import net.fabricmc.fabric.api.event.Event
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

fun interface TagsBuildingEvent {
    fun register(handler: Handler)

    companion object {
        private fun createEvent(): Event<TagsBuildingEvent> = createEvent { callbacks ->
            TagsBuildingEvent { handler -> callbacks.forEach { it.register(handler) } }
        }

        @JvmStatic
        val BLOCK: Event<TagsBuildingEvent> = createEvent()

        @JvmStatic
        val ITEM: Event<TagsBuildingEvent> = createEvent()

        @JvmStatic
        val FLUID: Event<TagsBuildingEvent> = createEvent()

        @JvmStatic
        val ENTITY_TYPE: Event<TagsBuildingEvent> = createEvent()
    }

    class Handler(val map: MutableMap<Identifier, Tag.Builder>) {
        fun builder(id: Identifier): Tag.Builder = map.computeIfAbsent(id) { Tag.Builder.create() }
    }
}
