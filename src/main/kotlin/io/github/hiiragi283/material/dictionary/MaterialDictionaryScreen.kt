package io.github.hiiragi283.material.dictionary

import com.mojang.blaze3d.systems.RenderSystem
import io.github.hiiragi283.api.part.HTPartManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper

@Environment(EnvType.CLIENT)
class MaterialDictionaryScreen(
    handler: MaterialDictionaryScreenHandler,
    playerInventory: PlayerInventory,
    title: Text,
) : HandledScreen<MaterialDictionaryScreenHandler>(handler, playerInventory, title) {
    private var scrollAmount: Float = 0.0f
    private var mouseClicked: Boolean = false
    private var scrollOffset: Int = 0
    private var canCraft: Boolean = false

    init {
        handler.setContentChangedListener(this::onInventoryChange)
        --this.titleY
    }

    override fun render(
        matrices: MatrixStack,
        mouseX: Int,
        mouseY: Int,
        delta: Float,
    ) {
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    @Suppress("DEPRECATION")
    override fun drawBackground(
        matrices: MatrixStack,
        delta: Float,
        mouseX: Int,
        mouseY: Int,
    ) {
        renderBackground(matrices)
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client!!.textureManager.bindTexture(TEXTURE)
        val i: Int = this.x
        val j: Int = this.y
        drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight)
        val k: Int = (41.0f * this.scrollAmount).toInt()
        drawTexture(matrices, i + 119, j + 15 + k, 176 + (if (shouldScroll()) 0 else 12), 0, 12, 15)
        val l: Int = i + 52
        val m: Int = j + 14
        val n: Int = this.scrollOffset + 12
        renderRecipeBackground(matrices, mouseX, mouseY, l, m, n)
        renderRecipeIcons(l, m, n)
    }

    override fun drawMouseoverTooltip(matrices: MatrixStack, x: Int, y: Int) {
        super.drawMouseoverTooltip(matrices, x, y)
        if (canCraft) {
            val i: Int = this.x + 52
            val j: Int = this.y + 14
            val k: Int = this.scrollOffset + 12
            var l: Int = this.scrollOffset
            val list: List<HTPartManager.Entry> = handler.getAvailableEntries()
            while (l < k && l < handler.getAvailableEntryCount()) {
                val m: Int = l - this.scrollOffset
                val n: Int = i + m % 4 * 16
                val o: Int = j + m / 4 * 18 + 2
                if (x < n || x >= n + 16 || y < o || y >= o + 18) {
                    ++l
                    continue
                }
                this.renderTooltip(matrices, list[l].item.defaultStack, x, y)
                ++l
            }
        }
    }

    private fun renderRecipeBackground(
        matrices: MatrixStack,
        i: Int,
        j: Int,
        k: Int,
        l: Int,
        m: Int,
    ) {
        var n: Int = this.scrollOffset
        while (n < m && n < handler.getAvailableEntryCount()) {
            val o: Int = n - this.scrollOffset
            val p: Int = k + o % 4 * 16
            val q: Int = o / 4
            val r: Int = l + q * 18 + 2
            var s: Int = this.backgroundHeight
            if (n == handler.getSelectedItem()) {
                s += 18
            } else if (i >= p && j >= r && i < p + 16 && j < r + 18) {
                s += 36
            }
            this.drawTexture(matrices, p, r - 1, 0, s, 16, 18)
            ++n
        }
    }

    private fun renderRecipeIcons(x: Int, y: Int, scrollOffset: Int) {
        val list: List<HTPartManager.Entry> = handler.getAvailableEntries()
        var i: Int = this.scrollOffset
        while (i < scrollOffset && i < handler.getAvailableEntryCount()) {
            val j: Int = i - this.scrollOffset
            val k: Int = x + j % 4 * 16
            val l: Int = j / 4
            val m: Int = y + l * 18 + 2
            client!!.itemRenderer.renderInGuiWithOverrides(list[i].item.defaultStack, k, m)
            ++i
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        mouseClicked = false
        if (canCraft) {
            var i: Int = this.x + 52
            var j: Int = this.y + 14
            val k: Int = this.scrollOffset + 12
            for (l: Int in this.scrollOffset until k) {
                val m: Int = l - this.scrollOffset
                val d: Double = mouseX - (i + m % 4 * 16).toDouble()
                val e: Double = mouseY - (j + m / 4 * 18).toDouble()
                if (!(d >= 0.0) || !(e >= 0.0) || !(d < 16.0) || !(e < 18.0) || !handler.onButtonClick(
                        client?.player!!,
                        l,
                    )
                ) {
                    continue
                }
                MinecraftClient.getInstance().soundManager.play(
                    PositionedSoundInstance.master(
                        SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                        1.0f,
                    ),
                )
                client!!.interactionManager!!.clickButton(handler.syncId, l)
                return true
            }
            i = this.x + 119
            j = this.y + 9
            if ((mouseX >= i.toDouble() && mouseX < (i + 12).toDouble() && mouseY >= j.toDouble()) && mouseY < (j + 54).toDouble()) {
                mouseClicked = true
            }
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseDragged(
        mouseX: Double,
        mouseY: Double,
        button: Int,
        deltaX: Double,
        deltaY: Double,
    ): Boolean {
        if (mouseClicked && shouldScroll()) {
            val i: Int = this.y + 14
            val j: Int = i + 54
            scrollAmount = (mouseY.toFloat() - i.toFloat() - 7.5f) / ((j - i).toFloat() - 15.0f)
            scrollAmount = MathHelper.clamp(scrollAmount, 0.0f, 1.0f)
            scrollOffset = ((scrollAmount * getMaxScroll().toFloat()).toDouble() + 0.5).toInt() * 4
            return true
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
        if (shouldScroll()) {
            val i: Int = getMaxScroll()
            scrollAmount = (scrollAmount.toDouble() - amount / i.toDouble()).toFloat()
            scrollAmount = MathHelper.clamp(scrollAmount, 0.0f, 1.0f)
            scrollOffset = ((this.scrollAmount * i.toFloat()).toDouble() + 0.5).toInt() * 4
        }
        return true
    }

    private fun shouldScroll(): Boolean = canCraft && handler.getAvailableEntryCount() > 12

    private fun getMaxScroll(): Int = (handler.getAvailableEntryCount() + 4 - 1) / 4 - 3

    private fun onInventoryChange() {
        canCraft = handler.canCraft()
        if (!canCraft) {
            scrollAmount = 0.0f
            scrollOffset = 0
        }
    }
}

private val TEXTURE = Identifier("textures/gui/container/stonecutter.png")
