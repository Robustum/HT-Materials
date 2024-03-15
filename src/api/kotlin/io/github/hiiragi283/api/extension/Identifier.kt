package io.github.hiiragi283.api.extension

import net.minecraft.util.Identifier

fun Identifier.prefix(prefix: String) = Identifier(this.namespace, prefix + this.path)

fun Identifier.suffix(suffix: String) = Identifier(this.namespace, this.path + suffix)

inline fun Identifier.modify(function: (String) -> String) = Identifier(this.namespace, function(this.path))

fun Identifier.removePrefix(prefix: String) = Identifier(this.namespace, this.path.removePrefix(prefix))

fun Identifier.removeSuffix(suffix: String) = Identifier(this.namespace, this.path.removeSuffix(suffix))

fun Identifier.arrange(prefix: String? = null, suffix: String? = null): Identifier = Identifier(
    namespace,
    this.path.also {
        prefix?.let(it::removePrefix)
        suffix?.let(it::removeSuffix)
    },
)
