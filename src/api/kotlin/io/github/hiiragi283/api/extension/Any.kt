package io.github.hiiragi283.api.extension

inline fun <T, reified U> T.castTo(): U? = this as? U
