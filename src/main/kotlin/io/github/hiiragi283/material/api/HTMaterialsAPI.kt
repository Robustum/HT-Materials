package io.github.hiiragi283.material.api

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlags
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import java.util.*
import java.util.function.Consumer
import java.util.function.Predicate

@Suppress("unused")
object HTMaterialsAPI {

    //    Fluid    //

    @JvmStatic
    fun getMaterialFluid(material: HTMaterial): HTMaterialFluid? = HTMaterialFluid.getStill(material)

    //    Material - Common    //

    @JvmStatic
    fun createMaterial(name: String, init: Consumer<HTMaterial>): HTMaterial =
        HTMaterial.create(name) { init.accept(this) }

    @JvmStatic
    fun getMaterial(name: String): Optional<HTMaterial> = Optional.ofNullable(HTMaterial.getMaterial(name))

    @JvmStatic
    fun <T : HTMaterialProperty<T>> getProperty(material: HTMaterial, key: HTPropertyKey<T>): Optional<T> =
        Optional.ofNullable(material.getProperty(key))

    @JvmStatic
    fun modifyInfo(material: HTMaterial, init: Consumer<HTMaterial.Info>) {
        material.modifyInfo { init.accept(this) }
    }

    //    Material - Flag    //

    @JvmStatic
    fun createFlag(name: String, init: Consumer<HTMaterialFlag.Builder>): HTMaterialFlag =
        HTMaterialFlag.create(name) { init.accept(this) }

    @JvmStatic
    fun getMaterialFlag(name: String): Optional<HTMaterialFlag> = Optional.ofNullable(HTMaterialFlag.REGISTRY[name])

    @JvmStatic
    fun modifyFlags(material: HTMaterial, init: Consumer<HTMaterialFlags>) {
        material.modifyFlags { init.accept(this) }
    }

    //    Material - Property    //

    @JvmStatic
    fun <T : HTMaterialProperty<T>> getPropertyKey(name: String): Optional<HTMaterialProperty<T>> =
        Optional.ofNullable(HTPropertyKey.get<T>(name))

    @JvmStatic
    fun modifyProperties(material: HTMaterial, init: Consumer<HTMaterialProperties>) {
        material.modifyProperties { init.accept(this) }
    }

    //    Shape    //

    @JvmStatic
    fun createShape(name: String, init: Consumer<HTShape.Builder>): HTShape = HTShape.create(name) { init.accept(this) }

    @JvmStatic
    fun getShape(name: String): Optional<HTShape> = Optional.ofNullable(HTShape.getShape(name))

    @JvmStatic
    fun modifyBlockPredicate(shape: HTShape, predicate: Predicate<HTMaterial>) {
        shape.modifyBlockPredicate(predicate)
    }

    @JvmStatic
    fun modifyItemPredicate(shape: HTShape, predicate: Predicate<HTMaterial>) {
        shape.modifyItemPredicate(predicate)
    }

    //    Part    //

    @JvmStatic
    fun getPart(itemConvertible: ItemConvertible): Optional<HTPart> =
        Optional.ofNullable(HTPartManager.getPart(itemConvertible))

    @JvmStatic
    fun getDefaultItem(material: HTMaterial, shape: HTShape): Optional<Item> =
        Optional.ofNullable(HTPartManager.getDefaultItem(material, shape))

    @JvmStatic
    fun getItems(material: HTMaterial, shape: HTShape): Collection<Item> = HTPartManager.getItems(material, shape)

    @JvmStatic
    fun registerItemToPart(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
        HTPartManager.register(material, shape, itemConvertible)
    }

}