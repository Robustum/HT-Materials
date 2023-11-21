package io.github.hiiragi283.material.client

import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.prefix
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.Models
import net.minecraft.data.client.TextureKey
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder
import java.util.function.BiConsumer

@Environment(EnvType.CLIENT)
object HTMaterialModelManager {

    private val registry: MutableMap<HTShape, BiConsumer<RuntimeResourcePack, HTPart>> = mutableMapOf()

    init {
        //Block
        registerConsumer(HTShape.BLOCK) { resourcePack: RuntimeResourcePack, part: HTPart ->
            val block: Block = Blocks.AIR
            val blockModelId: Identifier = getBlockModelId(part)
            val itemModelId: Identifier = getItemModelId(part)
            //BlockState
            resourcePack.addBlockState(
                part.getIdentifier(),
                BlockStateModelGenerator.createSingletonBlockState(block, blockModelId)
            )
            //Block Model
            resourcePack.addModel(
                blockModelId,
                ModelJsonBuilder.create(HTMaterialsCommon.id("block/all_tinted"))
                    .addTexture(TextureKey.ALL, Identifier("block/iron_block"))
            )
            //Item Model
            HTMaterialsCommon.RESOURCE_PACK.addModel(itemModelId, ModelJsonBuilder.create(blockModelId))
        }
        //Bucket
        registerConsumer(HTShape.BUCKET) { resourcePack: RuntimeResourcePack, part: HTPart ->
            resourcePack.addModel(
                getItemModelId(part),
                ModelJsonBuilder.create(Models.GENERATED)
                    .addTexture(TextureKey.LAYER0, Identifier("minecraft:item/bucket"))
                //.addTexture("layer1", Identifier("minecraft:item/bucket"))
            )
        }
        //Dust
        registerSimpleConsumer(HTShape.DUST, Identifier("item/sugar"))
        //Fluid
        registerConsumer(HTShape.FLUID) { resourcePack: RuntimeResourcePack, part: HTPart ->
            val block: HTMaterialFluid.Block = HTMaterialFluid.getBlock(part.material) ?: return@registerConsumer
            val modelId: Identifier = part.getIdentifier().prefix("block/fluid/")
            //BlockState
            resourcePack.addBlockState(
                part.getIdentifier(),
                BlockStateModelGenerator.createSingletonBlockState(block, modelId)
            )
            //Model (for particle)
            resourcePack.addModel(
                modelId,
                ModelJsonBuilder().addTexture(TextureKey.PARTICLE, Identifier("minecraft:block/white_concrete"))
            )
        }
        //Gear
        registerSimpleConsumer(HTShape.GEAR)
        //Gem
        registerSimpleConsumer(HTShape.GEM, Identifier("item/quartz"))
        //Ingot
        registerConsumer(HTShape.INGOT) { resourcePack: RuntimeResourcePack, part: HTPart ->
            resourcePack.addModel(
                getItemModelId(part),
                ModelJsonBuilder.create(Models.GENERATED)
                    .addTexture(TextureKey.LAYER0, HTMaterialsCommon.id("item/ingot"))
                    .addTexture("layer1", HTMaterialsCommon.id("item/ingot_overlay"))
            )
        }
        //Nugget
        registerSimpleConsumer(HTShape.NUGGET)
        //Plate
        registerSimpleConsumer(HTShape.PLATE)
        //Raw Ore
        //Raw Block
        //Rod
        registerSimpleConsumer(HTShape.ROD)
    }

    @JvmStatic
    fun getBlockModelId(part: HTPart): Identifier = part.getIdentifier().prefix("block/")

    @JvmStatic
    fun getItemModelId(part: HTPart): Identifier = part.getIdentifier().prefix("item/")

    @JvmStatic
    fun registerSimpleConsumer(shape: HTShape, textureId: Identifier = HTMaterialsCommon.id("item/${shape.name}")) {
        registerConsumer(shape) { resourcePack, part ->
            resourcePack.addModel(
                getItemModelId(part),
                ModelJsonBuilder.create(Models.GENERATED)
                    .addTexture(TextureKey.LAYER0, textureId)
            )
        }
    }

    @JvmStatic
    fun registerConsumer(shape: HTShape, biConsumer: BiConsumer<RuntimeResourcePack, HTPart>) {
        registry[shape] = biConsumer
    }

    internal fun register() {
        registry.forEach { (shape: HTShape, biConsumer: BiConsumer<RuntimeResourcePack, HTPart>) ->
            HTMaterial.REGISTRY.forEach { material: HTMaterial ->
                biConsumer.accept(HTMaterialsCommon.RESOURCE_PACK, HTPart(material, shape))
            }
        }
        registry.clear()
    }

}