package hiiragi283.material.init

import hiiragi283.material.block.entity.MachineExtenderBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType

object HiiragiBlockEntities {

    @JvmField
    val MACHINE_EXTENDER: BlockEntityType<MachineExtenderBlockEntity> = FabricBlockEntityTypeBuilder.create(
        ::MachineExtenderBlockEntity,
        HiiragiBlocks.MACHINE_EXTENDER
        ).build()

}