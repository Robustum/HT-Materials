package hiiragi283.material.init

import hiiragi283.material.block.entity.InventoryExtenderBlockEntity
import hiiragi283.material.block.entity.TankExtenderBlockEntity
import hiiragi283.material.util.hiiragiId
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry

object HiiragiBlockEntities {

    @JvmField
    val INVENTORY_EXTENDER: BlockEntityType<InventoryExtenderBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        hiiragiId("inventory_extender"),
        FabricBlockEntityTypeBuilder.create(
            ::InventoryExtenderBlockEntity,
            HiiragiBlocks.INVENTORY_EXTENDER
        ).build()
    )

    @JvmField
    val TANK_EXTENDER: BlockEntityType<TankExtenderBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        hiiragiId("tank_extender"),
        FabricBlockEntityTypeBuilder.create(
            ::TankExtenderBlockEntity,
            HiiragiBlocks.TANK_EXTENDER
        ).build()
    )

}