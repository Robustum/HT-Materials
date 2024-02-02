package io.github.hiiragi283.material.impl.shape

import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey

internal data class HTShapeImpl(
    override val key: HTShapeKey,
    override val idPath: String,
    override val tagPath: String,
) : HTShape
