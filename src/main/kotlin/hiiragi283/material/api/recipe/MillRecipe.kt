package hiiragi283.material.api.recipe

import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import hiiragi283.material.RagiMaterials
import hiiragi283.material.init.HiiragiRecipeSerializers
import hiiragi283.material.init.HiiragiRecipeTypes
import hiiragi283.material.util.HiiragiConstants
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

@Suppress("UnstableApiUsage")
class MillRecipe(
    private val id: Identifier,
    private val input: ItemIngredient,
    private val output: ItemStack
) : Recipe<Inventory> {

    //    Recipe    //

    override fun matches(inventory: Inventory, world: World): Boolean {
        throw UnsupportedOperationException("")
    }

    fun matches(storage: SingleVariantStorage<ItemVariant>): Boolean =
        input.test(storage.variant.toStack(storage.amount.toInt()))

    override fun craft(inventory: Inventory): ItemStack {
        throw UnsupportedOperationException("")
    }

    fun craft(storage: SingleVariantStorage<ItemVariant>): ItemStack = getOutput()

    override fun fits(width: Int, height: Int): Boolean {
        throw UnsupportedOperationException("")
    }

    override fun getOutput(): ItemStack = output.copy()

    override fun getId(): Identifier = id

    override fun getSerializer(): RecipeSerializer<*> = HiiragiRecipeSerializers.MILL

    override fun getType(): RecipeType<*> = HiiragiRecipeTypes.MILL

    fun toJson(): JsonObject {
        val root = JsonObject()
        //type
        root.addProperty(HiiragiConstants.TYPE, Registry.RECIPE_SERIALIZER.getId(serializer).toString())
        //result
        root.add(
            HiiragiConstants.RESULT,
            ItemStack.CODEC.encodeStart(
                JsonOps.INSTANCE,
                getOutput()
            ).getOrThrow(true, RagiMaterials.LOGGER::error)
        )
        //input
        root.add(HiiragiConstants.INGREDIENT, input.toJson())
        return root
    }

    //    Serializer    //

    object Serializer : RecipeSerializer<MillRecipe> {

        override fun read(id: Identifier, json: JsonObject): MillRecipe = MillRecipe(
            id,
            ItemIngredient.fromJson(json.getAsJsonObject(HiiragiConstants.INGREDIENT)),
            ItemStack.CODEC.parse(
                JsonOps.INSTANCE,
                json.getAsJsonObject(HiiragiConstants.RESULT)
            ).getOrThrow(true, RagiMaterials.LOGGER::error)
        )

        override fun read(id: Identifier, buf: PacketByteBuf): MillRecipe {
            TODO("Not yet implemented")
        }

        override fun write(buf: PacketByteBuf, recipe: MillRecipe) {
            recipe.input.write(buf)
            buf.writeItemStack(recipe.output)
        }

    }

}