package io.github.hiiragi283.fabric.mixin;

import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.material.HTMaterialKey;
import me.shedaniel.rei.impl.FluidEntryStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
@Mixin(FluidEntryStack.class)
public abstract class FluidEntryStackMixin {

    @Shadow
    private Fluid fluid;

    @Inject(method = "asFormattedText", at = @At("RETURN"), cancellable = true)
    private void ht_materials$asFormattedText(CallbackInfoReturnable<Text> cir) {
        Optional.ofNullable(HTMaterialsAPI.getINSTANCE().fluidManager().getMaterialKey(fluid))
                .map(HTMaterialKey::getTranslatedText)
                .ifPresent(cir::setReturnValue);
    }

}