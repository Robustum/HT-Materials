package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.common.append
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import java.util.function.Function
import java.util.function.Predicate

class HTShape private constructor(
    val name: String,
    private val tagPredicate: Predicate<String>,
    private val nameFunction: Function<String, String>
) {

    companion object {

        val REGISTRY: Map<String, HTShape>
            get() = map
        private val map: MutableMap<String, HTShape> = mutableMapOf()

        @JvmStatic
        fun createSimple(name: String): HTShape = Builder(name)
            .setTagPredicate { namespace -> namespace.endsWith("_${name}s") }
            .setNameFunction { namespace -> namespace.split("_")[0] }
            .build()

        @JvmField
        val INGOT: HTShape = createSimple("ingot")

    }

    init {
        map.putIfAbsent(name, this)
    }

    fun getMaterial(tagKey: TagKey<Item>): HTMaterial? = if (tagPredicate.test(tagKey.id.path)) {
        tagKey.id.append(nameFunction::apply).let(HTMaterial.REGISTRY::get)
    } else null

    //    Builder    //

    class Builder(val name: String) {

        var tagPredicate: Predicate<String> = Predicate { false }
        var nameFunction: Function<String, String> = Function { "empty" }

        fun setTagPredicate(predicate: Predicate<String>) = also {
            tagPredicate = predicate
        }

        fun setNameFunction(function: Function<String, String>) = also {
            nameFunction = function
        }

        fun build() = HTShape(name, tagPredicate, nameFunction)

    }

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTShape -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

}