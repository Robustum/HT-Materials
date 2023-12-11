package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.part.HTPartManager;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.api.shape.HTShapes;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin<T> {

    @Final
    @Shadow
    private String entryType;

    @Inject(method = "buildGroup", at = @At(value = "HEAD"))
    private void ht_materials$buildGroup(Map<Identifier, Tag.Builder> tags, CallbackInfoReturnable<TagGroup<T>> cir) {
        if (entryType.equals("item")) {
            //Register Tags from HTPartManager
            HTPartManager.getPartToItemTable().cellSet().forEach(cell -> {
                HTMaterial material = cell.getRowKey();
                HTShape shape = cell.getColumnKey();
                Collection<Item> items = cell.getValue();
                if (material != null && shape != null && items != null) {
                    items.forEach(item -> ht_materials$getorCreateBuilder(tags, shape.getCommonTag(material)).add(Registry.ITEM.getId(item), HTMaterialsCommon.MOD_NAME));
                }
            });
            HTMaterialsCommon.LOGGER.info("Registered Tags for HTPartManager's Entries!");
            //Sync ForgeTag and CommonTag entries
            HTMaterial.REGISTRY.forEach(material -> HTShapes.REGISTRY.forEach(shape -> {
                Tag.Builder forgeBuilder = ht_materials$getorCreateBuilder(tags, shape.getForgeTag(material));
                Tag.Builder commonBuilder = ht_materials$getorCreateBuilder(tags, shape.getCommonTag(material));
                ht_materials$syncEntries(commonBuilder, forgeBuilder);
                ht_materials$syncEntries(forgeBuilder, commonBuilder);
            }));
            HTMaterialsCommon.LOGGER.info("Synced Forge Tags and Common Tags!");
        }
    }

    @Unique
    private <R> Tag.Builder ht_materials$getorCreateBuilder(Map<Identifier, Tag.Builder> tags, Tag.Identified<R> tag) {
        return tags.computeIfAbsent(tag.getId(), key -> Tag.Builder.create());
    }

    @Unique
    private void ht_materials$syncEntries(Tag.Builder parentBuilder, Tag.Builder childBuilder) {
        ((TagBuilderAccessor) childBuilder).getEntries().forEach(parentBuilder::add);
    }

}