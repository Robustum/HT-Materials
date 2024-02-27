package io.github.hiiragi283.api.tag

import io.github.hiiragi283.api.extension.createEvent
import io.github.hiiragi283.api.extension.id
import net.fabricmc.fabric.api.event.Event
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

fun interface GlobalTagEvent {
    fun register(handler: Handler)

    companion object {
        private fun createEvent(): Event<GlobalTagEvent> = createEvent { callbacks ->
            GlobalTagEvent { handler -> callbacks.forEach { it.register(handler) } }
        }

        @JvmStatic
        val BLOCK: Event<GlobalTagEvent> = createEvent()

        @JvmStatic
        val ITEM: Event<GlobalTagEvent> = createEvent()

        @JvmStatic
        val FLUID: Event<GlobalTagEvent> = createEvent()

        @JvmStatic
        val ENTITY_TYPE: Event<GlobalTagEvent> = createEvent()
    }

    class Handler(val map: MutableMap<Identifier, Tag.Builder>) {
        inline fun <reified T> builder(tag: Tag<T>): Tag.Builder = tag.id()?.let(::builder) ?: Tag.Builder.create()

        fun builder(id: Identifier): Tag.Builder = map.computeIfAbsent(id) { Tag.Builder.create() }
    }
}
