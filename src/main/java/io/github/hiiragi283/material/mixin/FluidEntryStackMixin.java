package io.github.hiiragi283.material.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.widgets.Tooltip;
import me.shedaniel.rei.impl.FluidEntryStack;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@Mixin(FluidEntryStack.class)
public abstract class FluidEntryStackMixin {

    @Shadow
    private Fluid fluid;

    @Inject(method = "getTooltip", at = @At(value = "TAIL"), remap = false)
    private void ht_materials$getTooltip(Point point, CallbackInfoReturnable<Tooltip> cir, @Local List<Text> toolTip) {
        toolTip.addAll(FluidVariantRendering.getTooltip(FluidVariant.of(fluid)));
    }

    @Inject(method = "asFormattedText", at = @At("RETURN"), cancellable = true)
    private void ht_materials$asFormattedText(CallbackInfoReturnable<Text> cir) {
        cir.setReturnValue(FluidVariantRendering.getName(FluidVariant.of(fluid)));
    }

}