package hiiragi283.material.init

import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.gui.MaterialTooltipComponent
import hiiragi283.material.api.item.MaterialTooltipData
import hiiragi283.material.api.material.getMaterial
import hiiragi283.material.api.part.getParts
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.EntityType
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.world.explosion.Explosion

object HiiragiEventHandlers {

    fun register() {
        //Explodes water-sensitive material in water
        ServerTickEvents.START_WORLD_TICK.register(ServerTickEvents.StartWorldTick { world: ServerWorld ->
            world.getEntitiesByType(EntityType.ITEM) { it.isSubmergedInWater }.forEach(::explodesItem)
        })
        //Complete!
        RagiMaterials.LOGGER.info("All Common Events have been registered!")
    }

    private fun explodesItem(itemEntity: ItemEntity) {
        if (itemEntity.stack.getMaterial().any { it.isMetal() }) {
            itemEntity.world.createExplosion(
                itemEntity,
                itemEntity.x,
                itemEntity.y,
                itemEntity.z,
                1.0f,
                Explosion.DestructionType.BREAK
            )
        }
    }

    @Environment(EnvType.CLIENT)
    fun registerClient() {
        //Material Item Tooltip
        ItemTooltipCallback.EVENT.register(
            ItemTooltipCallback { stack: ItemStack, _: TooltipContext, lines: MutableList<Text> ->
                stack.getParts().forEach { it.appendTooltip(lines) }
            }
        )
        //Item TooltipData
        TooltipComponentCallback.EVENT.register(TooltipComponentCallback(::getTooltipComponent))
        //Complete!
        RagiMaterials.LOGGER.info("All Client Events have been registered!")
    }

    private fun getTooltipComponent(data: TooltipData): TooltipComponent? = when (data) {
        is MaterialTooltipData -> MaterialTooltipComponent(data.part)
        else -> null
    }

}