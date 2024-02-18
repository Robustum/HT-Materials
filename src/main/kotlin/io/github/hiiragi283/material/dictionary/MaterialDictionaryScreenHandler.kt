package io.github.hiiragi283.material.dictionary

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.part.HTPartManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.CraftingResultInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.Property
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.slot.Slot
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MaterialDictionaryScreenHandler(
    syncId: Int,
    player: PlayerEntity,
    private val context: ScreenHandlerContext = ScreenHandlerContext.create(player.world, player.blockPos),
) : ScreenHandler(TYPE, syncId) {
    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory.player)

    companion object {
        @JvmField
        val TYPE: ScreenHandlerType<MaterialDictionaryScreenHandler> = ScreenHandlerRegistry.registerSimple(
            HTMaterialsAPI.id("material_dictionary"),
            ::MaterialDictionaryScreenHandler,
        )
    }

    private val playerInventory: PlayerInventory = player.inventory
    private val selectedItem: Property = Property.create()
    private val world: World = player.world
    private var availableEntries: List<HTPartManager.Entry> = listOf()
    private var inputStack: ItemStack = ItemStack.EMPTY
    private var lastTakeTime: Long = 0
    private var contentChangedListener: Runnable = Runnable { }

    val input: Inventory = object : SimpleInventory(1) {
        override fun markDirty() {
            super.markDirty()
            this@MaterialDictionaryScreenHandler.onContentChanged(this)
            this@MaterialDictionaryScreenHandler.contentChangedListener.run()
        }
    }
    private val output: CraftingResultInventory = CraftingResultInventory()

    val inputSlot: Slot = addSlot(Slot(input, 0, 20, 33))
    val outputSlot: Slot = addSlot(object : Slot(output, 1, 143, 33) {
        override fun canInsert(stack: ItemStack): Boolean = false

        override fun onTakeItem(player: PlayerEntity, stack: ItemStack): ItemStack {
            stack.onCraft(player.world, player, stack.count)
            this@MaterialDictionaryScreenHandler.output.unlockLastRecipe(player)
            val itemStack: ItemStack = this@MaterialDictionaryScreenHandler.inputSlot.takeStack(1)
            if (!itemStack.isEmpty) {
                this@MaterialDictionaryScreenHandler.populateResult()
            }
            context.run { world: World, blockPos: BlockPos? ->
                val time: Long = world.time
                if (this@MaterialDictionaryScreenHandler.lastTakeTime != time) {
                    world.playSound(
                        null,
                        blockPos,
                        SoundEvents.UI_STONECUTTER_TAKE_RESULT,
                        SoundCategory.BLOCKS,
                        1.0f,
                        1.0f,
                    )
                    this@MaterialDictionaryScreenHandler.lastTakeTime = time
                }
            }
            return super.onTakeItem(player, stack)
        }
    })

    init {
        for (y: Int in (0 until 3)) {
            for (x: Int in (0 until 9)) {
                addSlot(Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18))
            }
        }
        for (x: Int in (0 until 9)) {
            addSlot(Slot(playerInventory, x, 8 + x * 18, 142))
        }
        this.addProperty(this.selectedItem)
    }

    @Environment(EnvType.CLIENT)
    fun getSelectedItem(): Int = selectedItem.get()

    @Environment(EnvType.CLIENT)
    fun getAvailableEntries(): List<HTPartManager.Entry> = availableEntries

    @Environment(EnvType.CLIENT)
    fun getAvailableEntryCount(): Int = availableEntries.size

    @Environment(EnvType.CLIENT)
    fun canCraft(): Boolean = inputSlot.hasStack() && availableEntries.isNotEmpty()

    override fun canUse(player: PlayerEntity): Boolean = true

    override fun onButtonClick(player: PlayerEntity, id: Int): Boolean {
        if (isValidId(id)) {
            selectedItem.set(id)
            populateResult()
        }
        return true
    }

    private fun isValidId(id: Int): Boolean = id in availableEntries.indices

    override fun onContentChanged(inventory: Inventory) {
        val stack = inputSlot.stack
        if (!ItemStack.areItemsEqual(stack, inputStack)) {
            inputStack = stack.copy()
            updateInput(stack)
        }
    }

    private fun updateInput(stack: ItemStack) {
        selectedItem.set(-1)
        outputSlot.stack = ItemStack.EMPTY
        if (!stack.isEmpty) {
            availableEntries = HTMaterialsAPI.INSTANCE.partManager().getEntries(stack.item).toList()
        }
    }

    private fun populateResult() {
        if (availableEntries.isNotEmpty() && isValidId(selectedItem.get())) {
            val entry = availableEntries[selectedItem.get()]
            outputSlot.stack = entry.item.defaultStack
        } else {
            outputSlot.stack = ItemStack.EMPTY
        }
    }

    override fun getType(): ScreenHandlerType<*> {
        return super.getType()
    }

    @Environment(EnvType.CLIENT)
    fun setContentChangedListener(runnable: Runnable) {
        contentChangedListener = runnable
    }

    override fun canInsertIntoSlot(stack: ItemStack, slot: Slot): Boolean = slot.inventory != output && super.canInsertIntoSlot(stack, slot)

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        return super.transferSlot(player, index)
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        output.removeStack(1)
        context.run { _, _ -> dropInventory(player, this.world, input) }
    }
}
