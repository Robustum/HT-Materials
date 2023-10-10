@file:Suppress("UnstableApiUsage")

package hiiragi283.material.api.material

import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.part.getParts
import hiiragi283.material.init.HiiragiRegistries
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

fun ItemStack.getMaterial(): Collection<HiiragiMaterial> {
    val result: MutableList<HiiragiMaterial> = this.getParts().map(HiiragiPart::material).toMutableList()
    //液体コンテナの液体名から素材を取得
    val storage: Storage<FluidVariant> = FluidStorage.ITEM.find(this, ContainerItemContext.withInitial(this)) ?: return result.toSet()
    storage.iterable(Transaction.openOuter())
        .asSequence()
        .map(StorageView<FluidVariant>::getResource) // StorageView -> FluidVariant
        .map(FluidVariant::getObject) // FluidVariant -> Fluid
        .map(Registry.FLUID::getId) // Fluid -> Identifier
        .map(Identifier::getPath) // Identifier -> String
        .map(HiiragiRegistries.MATERIAL::getValue) // String -> HiiragiMaterial
        .filterNot(HiiragiMaterial::isEmpty) // EMPTYを除外
        .toList()
        .forEach(result::add) // resultに追加
    return result.toSet()
}