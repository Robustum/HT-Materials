package hiiragi283.material.init

import hiiragi283.material.block.entity.MachineExtenderBlockEntity
import hiiragi283.material.util.hiiragiId
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry

object HiiragiBlockEntities {

    @JvmField
    val MACHINE_EXTENDER: BlockEntityType<MachineExtenderBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        hiiragiId("machine_extender"),
        FabricBlockEntityTypeBuilder.create(
            ::MachineExtenderBlockEntity,
            HiiragiBlocks.MACHINE_EXTENDER
        ).build()
    )

}