package io.github.hiiragi283.api.extension

import io.github.hiiragi283.api.HTMaterialsAPI

inline fun runTryAndCatch(runnable: () -> Unit) {
    try {
        runnable()
    } catch (e: Exception) {
        HTMaterialsAPI.LOGGER.error(e.localizedMessage)
    }
}
