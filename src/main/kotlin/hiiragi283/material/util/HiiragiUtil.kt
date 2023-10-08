package hiiragi283.material.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import hiiragi283.material.RagiMaterials
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.data.client.Models
import net.minecraft.data.client.TextureKey
import net.minecraft.item.ItemStack
import net.minecraft.predicate.BlockPredicate
import net.minecraft.predicate.StatePredicate
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry
import net.minecraft.world.BlockView
import pers.solid.brrp.v1.model.ModelJsonBuilder
import java.lang.reflect.Field

//    Block    //

fun BlockState.itemStack(count: Int = 1) = ItemStack(this.block, count)

fun blockPredicateOf(block: Block) = blockPredicateOf { blocks(block) }

fun blockPredicateOf(init: BlockPredicate.Builder.() -> Unit): BlockPredicate {
    val builder: BlockPredicate.Builder = BlockPredicate.Builder.create()
    builder.init()
    return builder.build()
}

fun statePredicateOf(init: StatePredicate.Builder.() -> Unit): StatePredicate {
    val builder: StatePredicate.Builder = StatePredicate.Builder.create()
    builder.init()
    return builder.build()
}

//    Collection    //

fun <T> Collection<T>.hasSameElements(other: Collection<T>): Boolean =
    this.containsAll(other) && other.containsAll(this)

fun <K, V> Map<K, V>.reverse(): Map<V, K> = this.toList().associate(Pair<K, V>::reverse)

fun <A, B> Pair<A, B>.reverse(): Pair<B, A> = this.second to this.first

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun hiiragiId(path: String) = Identifier(RagiMaterials.MOD_ID, path)

fun Identifier.append(path: String) = Identifier(this.namespace, this.path + path)

fun Identifier.appendBefore(path: String) = Identifier(this.namespace, path + this.path)

//    Implementation    //

inline fun <reified T> getBlockEntity(world: BlockView, pos: BlockPos): T? = world.getBlockEntity(pos) as? T

//    Json    //

fun ItemStack.toJson(): JsonElement {
    val root = JsonObject()
    root.addProperty("item", Registry.ITEM.getId(this.item).toString())
    root.addProperty("count", 1)
    return root
}

//    Model    //

fun simpleItemModel(layer0: Identifier, layer1: Identifier? = null): ModelJsonBuilder =
    ModelJsonBuilder.create(Models.GENERATED)
        .addTexture(TextureKey.LAYER0, layer0)
        .addTexture("layer1", layer1)

fun simpleItemModel(layer0: String, layer1: String? = null) =
    simpleItemModel(Identifier(layer0), layer1?.let(::Identifier))

//    Reflection    //

fun Field.enableAccess() = also { it.isAccessible = true }

//    Registry    //

inline fun <reified T> createSimpleRegistry(name: String): SimpleRegistry<T> =
    FabricRegistryBuilder.createSimple(
        T::class.java,
        hiiragiId(name)
    ).attribute(RegistryAttribute.SYNCED).buildAndRegister()

inline fun <reified T> createDefaultedRegistry(name: String, defaultName: String): DefaultedRegistry<T> =
    FabricRegistryBuilder.createDefaulted(
        T::class.java,
        hiiragiId(name),
        hiiragiId(defaultName)
    ).attribute(RegistryAttribute.SYNCED).buildAndRegister()