package io.github.hiiragi283.api.extension

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader

fun (() -> Unit).runWhenOn(envType: EnvType) {
    if (FabricLoader.getInstance().environmentType == envType) this()
}

inline fun <T> runForEnv(clientRun: () -> T, serverRun: () -> T) = when (currentEnvType) {
    EnvType.CLIENT -> clientRun()
    EnvType.SERVER -> serverRun()
}
