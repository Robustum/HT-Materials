package io.github.hiiragi283.material.api.event

import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory

/**
 * Reference: https://github.com/Fabricators-of-Create/Porting-Lib/blob/1.18.2/src/main/java/io/github/fabricators_of_create/porting_lib/event/common/ModsLoadedCallback.java
 */

fun interface HTModsLoadedCallback {

    fun onAllModsLoaded(envType: EnvType)

    companion object {

        @JvmField
        val EVENT: Event<HTModsLoadedCallback> =
            EventFactory.createArrayBacked(HTModsLoadedCallback::class.java) { callbacks: Array<HTModsLoadedCallback> ->
                HTModsLoadedCallback { envType: EnvType -> callbacks.forEach { it.onAllModsLoaded(envType) } }
            }

    }

}