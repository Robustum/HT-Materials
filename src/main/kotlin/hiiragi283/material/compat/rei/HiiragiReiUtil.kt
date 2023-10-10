package hiiragi283.material.compat.rei

import hiiragi283.material.api.recipe.FluidIngredient
import hiiragi283.material.api.recipe.ItemIngredient
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.text.TranslatableText

fun <T : Display> CategoryIdentifier<T>.getTitle() = TranslatableText("gui.${this.namespace}.${this.path}.name")

fun entryIngredientOf(ingredient: ItemIngredient): EntryIngredient =
    EntryIngredient.of(ingredient.getMatchingStacks().map(EntryStacks::of))

fun entryIngredientOf(ingredient: FluidIngredient): EntryIngredient =
    EntryIngredient.of(ingredient.getMatchingFluids()
        .map { EntryStacks.of(it, ingredient.amount) })