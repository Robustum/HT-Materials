package io.github.hiiragi283.material.api.event

import io.github.hiiragi283.material.HTMaterialsCommon
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

fun interface HTSortTestEvent {

    fun call()

    companion object {

        @JvmField
        val EVENT: Event<HTSortTestEvent> = EventFactory.createArrayBacked(HTSortTestEvent::class.java) { callbacks ->
            HTSortTestEvent { callbacks.forEach { it.call() } }
        }

        @JvmField
        val LOGGER: Logger = LogManager.getLogger(HTSortTestEvent::class.java.simpleName)

        @JvmStatic
        fun registerPhase() {
            EVENT.addPhaseOrdering(HTMaterialsCommon.id("before"), Event.DEFAULT_PHASE)
            EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, HTMaterialsCommon.id("after"))

            EVENT.register { LOGGER.info("after!") }
            EVENT.register { LOGGER.info("before!") }
            EVENT.register { LOGGER.info("default!") }

        }

    }

}