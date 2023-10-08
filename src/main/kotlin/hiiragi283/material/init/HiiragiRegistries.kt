package hiiragi283.material.init

import hiiragi283.material.api.MaterialEntryPoint
import hiiragi283.material.api.ShapeEntryPoint
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.registry.HiiragiRegistry
import hiiragi283.material.api.shape.HiiragiShape
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item

object HiiragiRegistries {

    //    Game Object    //

    @JvmField
    val BLOCK: HiiragiRegistry<Block> = HiiragiRegistry.Simple("Block")

    @JvmField
    val FLUID: HiiragiRegistry<Fluid> = HiiragiRegistry.Simple("Fluid")

    @JvmField
    val ITEM: HiiragiRegistry<Item> = HiiragiRegistry.Simple("Item")

    //    Material System    //

    @JvmField
    val MATERIAL = HiiragiRegistry.Defaulted("Material", HiiragiMaterial.EMPTY)

    fun registerMaterial() {
        FabricLoader.getInstance().getEntrypoints(MaterialEntryPoint.NAME, MaterialEntryPoint::class.java)
            .forEach(MaterialEntryPoint::onMaterialRegister)
        MATERIAL.lock()
    }

    @JvmField
    val SHAPE = HiiragiRegistry.Defaulted("Shape", HiiragiShape.EMPTY)

    fun registerShape() {
        FabricLoader.getInstance().getEntrypoints(ShapeEntryPoint.NAME, ShapeEntryPoint::class.java)
            .forEach(ShapeEntryPoint::onShapeRegister)
        SHAPE.lock()
    }

    @JvmField
    val PART: HiiragiRegistry.Simple<HiiragiPart> = HiiragiRegistry.Simple("Part")

    fun registerPart() {
        HiiragiPart.getAllParts().forEach { part: HiiragiPart ->
            PART.register(part.tagPath, part)
        }
    }

}