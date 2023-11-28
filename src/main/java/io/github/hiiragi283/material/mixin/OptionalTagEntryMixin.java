package io.github.hiiragi283.material.mixin;

import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@Mixin(Tag.OptionalTagEntry.class)
public abstract class OptionalTagEntryMixin implements Tag.Entry {

    @Shadow
    @Final
    private Identifier id;

    @Override
    public void forEachTagId(Consumer<Identifier> consumer) {
        consumer.accept(this.id);
    }

}