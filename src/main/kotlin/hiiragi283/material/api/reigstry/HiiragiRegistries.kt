package hiiragi283.material.api.reigstry

import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.shape.HiiragiShape
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry

object HiiragiRegistries {

    //    Game Object    //

    @JvmField
    val BLOCK: HiiragiRegistry<Block> = HiiragiRegistry("Block")

    @JvmField
    val BLOCK_ENTITY: HiiragiRegistry<BlockEntityType<*>> = HiiragiRegistry("BLock Entity")

    @JvmField
    val FLUID: HiiragiRegistry<Fluid> = HiiragiRegistry("Fluid")

    @JvmField
    val ITEM: HiiragiRegistry<Item> = HiiragiRegistry("Item")

    //    Material System    //

    @JvmField
    val MATERIAL: HiiragiRegistry<HiiragiMaterial> = HiiragiRegistry("Material")

    @JvmField
    val SHAPE: HiiragiRegistry<HiiragiShape> = HiiragiRegistry("Shape")

    fun registerShape() {
        FabricLoader.getInstance()
    }

    @JvmField
    val PART: SimpleRegistry<HiiragiPart> = FabricRegistryBuilder.createSimple(
        HiiragiPart::class.java,
        RagiMaterials.id("Part")
    ).buildAndRegister()

    fun registerPart() {
        HiiragiPart.getAllParts().forEach {
            Registry.register(PART, it.tagKey.id, it)
        }
    }

}