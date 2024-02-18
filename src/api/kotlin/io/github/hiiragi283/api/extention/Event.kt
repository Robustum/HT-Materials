package io.github.hiiragi283.api.extention

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory

inline fun <reified T> createEvent(noinline factory: (Array<T>) -> T): Event<T> = EventFactory.createArrayBacked(T::class.java, factory)
