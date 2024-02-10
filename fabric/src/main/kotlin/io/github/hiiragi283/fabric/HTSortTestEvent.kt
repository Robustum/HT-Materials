package io.github.hiiragi283.fabric

import io.github.hiiragi283.api.HTMaterialsAPI
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory

fun interface HTSortTestEvent {
    fun call()

    companion object {
        @JvmField
        val EVENT: Event<HTSortTestEvent> = EventFactory.createArrayBacked(HTSortTestEvent::class.java) { callbacks ->
            HTSortTestEvent { callbacks.forEach { it.call() } }
        }

        @JvmStatic
        fun registerPhase() {
            EVENT.addPhaseOrdering(HTMaterialsAPI.id("before"), Event.DEFAULT_PHASE)
            EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, HTMaterialsAPI.id("after"))

            EVENT.register { HTMaterialsAPI.log("after!") }
            EVENT.register { HTMaterialsAPI.log("before!") }
            EVENT.register { HTMaterialsAPI.log("default!") }
        }
    }
}
