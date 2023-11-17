package io.github.hiiragi283.material.api

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.util.registry.SimpleRegistry
import java.util.function.Consumer

@Suppress("unused")
object HTMaterialsAPI {

    //    Material    //

    @JvmField
    val MATERIAL_REGISTRY: SimpleRegistry<HTMaterial> = HTMaterial.REGISTRY

    @JvmStatic
    fun getMaterial(name: String): HTMaterial? = HTMaterial.getMaterial(name)

    //    Shape    //

    @JvmField
    val SHAPE_REGISTRY: SimpleRegistry<HTShape> = HTShape.REGISTRY

    @JvmStatic
    fun getShape(name: String): HTShape? = HTShape.getShape(name)

    //    Util    //

    @JvmStatic
    fun <T> funcWrapper(consumer: Consumer<T>): T.() -> Unit = { consumer.accept(this) }

}