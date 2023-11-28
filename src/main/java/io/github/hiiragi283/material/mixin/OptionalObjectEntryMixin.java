package io.github.hiiragi283.material.mixin;

import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@Mixin(Tag.OptionalObjectEntry.class)
public abstract class OptionalObjectEntryMixin implements Tag.Entry {

    @Shadow
    @Final
    private Identifier id;

    @Override
    public void forEachTagId(Consumer<Identifier> consumer) {
        consumer.accept(this.id);
    }

}