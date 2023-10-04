package hiiragi283.material.api.reigstry

import hiiragi283.material.api.MaterialEntryPoint
import hiiragi283.material.api.ShapeEntryPoint
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.util.hiiragiId
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry

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
    val PART: SimpleRegistry<HiiragiPart> = createSimpleRegistry("part")

    fun registerPart() {
        HiiragiPart.getAllParts().forEach {
            Registry.register(PART, it.tagKey.id, it)
        }
    }

    private inline fun <reified T> createSimpleRegistry(name: String): SimpleRegistry<T> =
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

}