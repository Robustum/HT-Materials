package io.github.hiiragi283.material.api.event

import io.github.hiiragi283.material.HTMaterials
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
            EVENT.addPhaseOrdering(HTMaterials.id("before"), Event.DEFAULT_PHASE)
            EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, HTMaterials.id("after"))

            EVENT.register { HTMaterials.log("after!") }
            EVENT.register { HTMaterials.log("before!") }
            EVENT.register { HTMaterials.log("default!") }
        }
    }
}
