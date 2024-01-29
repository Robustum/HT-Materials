package io.github.hiiragi283.material.api.registry

interface HTObjectKey<T> {
    val name: String
    val objClass: Class<T>
}
