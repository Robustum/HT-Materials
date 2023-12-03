package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.block.HTMaterialBlock;
import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.material.property.HTPropertyKey;
import io.github.hiiragi283.material.api.part.HTPartManager;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.api.shape.HTShapes;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import io.github.hiiragi283.material.common.util.HTUtil;
import io.github.hiiragi283.material.common.util.TableUtil;
import io.github.hiiragi283.material.util.HTTagLoader;
import kotlin.Unit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Reference: <a href="https://github.com/GregTechCEu/GregTech-Modern/blob/1.20.1/common/src/main/java/com/gregtechceu/gtceu/core/mixins/TagLoaderMixin.java">...</a>
 *
 * @param <T>
 */

@Mixin(TagGroupLoader.class)
public class TagLoaderMixin<T> implements HTTagLoader<T> {

    @Inject(method = "loadTags", at = @At(value = "RETURN"))
    private void ht_materials$loadTags(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, Tag.Builder>> cir) {
        Map<Identifier, Tag.Builder> map = cir.getReturnValue();
        var registry = ht_materials$getRegistry();
        if (registry == null) return;
        if (Objects.equals(registry, Registry.BLOCK)) {
            //Register Mining Tool & Harvest Level Tags
            for (HTMaterial material : HTMaterial.REGISTRY) {
                var solidProperty = material.getProperty(HTPropertyKey.SOLID);
                if (solidProperty == null) continue;
                HTShapes.REGISTRY.forEach(shape -> {
                    Item item = HTPartManager.getDefaultItem(material, shape);
                    if (item == null) return;
                    Block block = HTUtil.asBlock(item);
                    if (block instanceof HTMaterialBlock materialBlock) {
                        Optional.ofNullable(solidProperty.getHarvestTool())
                                .map(tag -> ht_materials$getBuilder(map, tag))
                                .ifPresent(builder -> ht_materials$registerTag(builder, Registry.BLOCK, materialBlock));
                        Optional.ofNullable(solidProperty.getHarvestLevelTag())
                                .map(tag -> ht_materials$getBuilder(map, tag))
                                .ifPresent(builder -> ht_materials$registerTag(builder, Registry.BLOCK, materialBlock));
                    }
                });
            }
            HTMaterialsCommon.LOGGER.info("Registered Mining Tool & Harvest Level Tags!");
        } else if (Objects.equals(registry, Registry.ITEM)) {
            HTMaterialsCommon.LOGGER.info("Registered items from Tags to HTPartManager!");
            //Register Tags from HTPartManager
            TableUtil.forEach(HTPartManager.getPartToItemTable(), cell -> {
                HTMaterial material = cell.getRowKey();
                HTShape shape = cell.getColumnKey();
                cell.getValue().forEach(item -> ht_materials$registerTag(ht_materials$getBuilder(map, shape.getCommonTag(material)), Registry.ITEM, item));
                return Unit.INSTANCE;
            });
            HTMaterialsCommon.LOGGER.info("Registered Tags for HTPartManager's Entries!");
            //Sync ForgeTag and CommonTag entries
            HTMaterial.REGISTRY.forEach(material -> HTShapes.REGISTRY.forEach(shape -> {
                Tag.Builder forgeTagBuilder = ht_materials$getBuilder(map, shape.getForgeTag(material));
                Tag.Builder commonTagBuilder = ht_materials$getBuilder(map, shape.getCommonTag(material));
                ht_materials$syncTags(commonTagBuilder, forgeTagBuilder);
                ht_materials$syncTags(forgeTagBuilder, commonTagBuilder);
            }));
            HTMaterialsCommon.LOGGER.info("Synced Forge Tags and Common Tags!");
        }
    }

    @NotNull
    @Unique
    private Tag.Builder ht_materials$getBuilder(Map<Identifier, Tag.Builder> map, TagKey<?> tagKey) {
        return map.computeIfAbsent(tagKey.id(), key -> Tag.Builder.create());
    }

    @Unique
    private <R> void ht_materials$registerTag(Tag.Builder builder, Registry<R> registry, R value) {
        builder.add(registry.getId(value), HTMaterialsCommon.MOD_NAME);
    }

    @Unique
    private void ht_materials$syncTags(Tag.Builder parentBuilder, Tag.Builder childBuilder) {
        ((TagBuilderMixin) childBuilder).getEntries().forEach(parentBuilder::add);
    }

    //    HTTagLoader    //

    @Nullable
    @Unique
    private Registry<T> registry;

    @Nullable
    @Override
    public Registry<T> ht_materials$getRegistry() {
        return registry;
    }

    @Override
    public void ht_materials$setRegistry(Registry<T> registry) {
        this.registry = registry;
    }

}