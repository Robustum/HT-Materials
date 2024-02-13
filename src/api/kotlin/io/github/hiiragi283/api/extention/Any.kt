package io.github.hiiragi283.api.extention

inline fun <T, reified U> T.castTo(): U? = this as? U
