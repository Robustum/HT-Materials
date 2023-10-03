package hiiragi283.material.util

import net.minecraft.block.BlockState
import net.minecraft.data.client.Models
import net.minecraft.data.client.TextureKey
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import pers.solid.brrp.v1.model.ModelJsonBuilder

//    Block    //

fun BlockState.itemStack(count: Int = 1) = ItemStack(this.block, count)

//    Collection    //

fun <T> Collection<T>.hasSameElements(other: Collection<T>): Boolean =
    this.containsAll(other) && other.containsAll(this)

fun <K, V> Map<K, V>.reverse(): Map<V, K> = this.toList().associate(Pair<K, V>::reverse)

fun <A, B> Pair<A, B>.reverse(): Pair<B, A> = this.second to this.first

//    Identifier    //

fun commonId(path: String) = Identifier("c", path)

fun Identifier.append(path: String) = Identifier(this.namespace, this.path + path)

fun Identifier.appendBefore(path: String) = Identifier(this.namespace, path + this.path)

//    Model    //

fun simpleItemModel(layer0: Identifier, layer1: Identifier? = null): ModelJsonBuilder =
    ModelJsonBuilder.create(Models.GENERATED)
        .addTexture(TextureKey.LAYER0, layer0)
        .addTexture("layer1", layer1)

fun simpleItemModel(layer0: String, layer1: String? = null) =
    simpleItemModel(Identifier(layer0), layer1?.let(::Identifier))

//    Implementation    //

inline fun <reified T> getBlockEntity(world: BlockView, pos: BlockPos): T? = world.getBlockEntity(pos) as? T